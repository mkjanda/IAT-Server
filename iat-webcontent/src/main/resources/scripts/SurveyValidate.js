/* SurveyValidate.js
 * data-* driven survey validation. Replaces generated ValidateItemN / SurveyScript.xslt.
 *
 * Contract (SurveyPage.xslt):
 *   form[data-timeout]
 *   owner [data-type][data-item][data-optional]
 *   extras: data-min data-max data-min-length data-max-length
 *           data-length data-pattern data-has-start data-has-end
 *           data-start-date data-end-date
 *
 * POST: ItemN, ItemN_k checkboxes, sentinels __Unanswered__ /
 *       __ForceSubmittedUnanswered__, plus session fields from addAdditionalPostData.
 */
(function (global) {
    "use strict";

    var UNANSWERED = "__Unanswered__";
    var FORCE_UNANSWERED = "__ForceSubmittedUnanswered__";
    var ForceSubmit = false;
    var submitted = false;
    var timeoutHandle = null;

    function flagTrue(value) {
        return String(value || "").toLowerCase() === "true";
    }

    function surveyForm() {
        var form = document.getElementById("SurveyForm");
        if (form) {
            return form;
        }
        form = document.querySelector("#mainContent form") || document.querySelector("form");
        if (form && !form.id) {
            form.id = "SurveyForm";
        }
        return form;
    }

    function itemName(n) {
        return "Item" + String(n);
    }

    function owners() {
        return Array.prototype.slice.call(document.querySelectorAll("[data-type][data-item]"));
    }

    function questionLi(owner) {
        var node = owner;
        while (node && node !== document.body) {
            if (node.tagName === "LI") {
                return node;
            }
            node = node.parentNode;
        }
        return null;
    }

    function clearErrors(list) {
        if (!list) {
            return;
        }
        var kids = list.childNodes;
        var i = 0;
        while (i < kids.length) {
            if (kids[i].className === "Error") {
                list.removeChild(kids[i]);
            } else {
                i += 1;
            }
        }
        var banner = document.getElementById("ErrorsExistMsgDiv") || document.getElementById("ErrorsExistDiv");
        if (banner) {
            banner.innerText = "";
        }
    }

    function showError(list, beforeLi, message) {
        if (ForceSubmit) {
            return;
        }
        var li = document.createElement("li");
        li.className = "Error";
        li.innerText = message;
        if (list && beforeLi && beforeLi.parentNode === list) {
            list.insertBefore(li, beforeLi);
        } else if (list) {
            list.appendChild(li);
        }
    }

    function setBanner() {
        var banner = document.getElementById("ErrorsExistMsgDiv") || document.getElementById("ErrorsExistDiv");
        if (banner) {
            banner.innerText = "Response errors detected. Please review the above survey for error messages and then resubmit.";
        }
    }

    function named(n) {
        return document.getElementsByName(itemName(n));
    }

    function firstNamed(n) {
        var list = named(n);
        return list.length ? list[0] : document.getElementById(itemName(n));
    }

    function trimValue(el) {
        return el && typeof el.value === "string" ? el.value.replace(/^\s+|\s+$/g, "") : "";
    }

    function setFieldValue(n, value) {
        var el = firstNamed(n);
        if (el) {
            el.value = value;
        }
    }

    function postRadioSentinel(n, sentinel) {
        appendHidden(surveyForm(), itemName(n), sentinel);
    }

    function pad2(n) {
        n = parseInt(n, 10);
        if (isNaN(n)) {
            return "00";
        }
        return n < 10 ? "0" + n : String(n);
    }

    function parseIsoDate(raw) {
        if (!raw) {
            return null;
        }
        var parts = String(raw).split("-");
        if (parts.length !== 3) {
            return null;
        }
        var y = parseInt(parts[0], 10);
        var m = parseInt(parts[1], 10);
        var d = parseInt(parts[2], 10);
        if (!y || !m || !d) {
            return null;
        }
        var dt = new Date(y, m - 1, d);
        if (dt.getFullYear() !== y || dt.getMonth() !== m - 1 || dt.getDate() !== d) {
            return null;
        }
        return dt;
    }

    function parseMdY(raw) {
        var m = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/.exec(raw);
        if (!m) {
            return null;
        }
        var month = parseInt(m[1], 10);
        var day = parseInt(m[2], 10);
        var year = parseInt(m[3], 10);
        var dt = new Date(year, month - 1, day);
        if (dt.getFullYear() !== year || dt.getMonth() !== month - 1 || dt.getDate() !== day) {
            return null;
        }
        return dt;
    }

    function appendHidden(form, name, value) {
        if (!form || value == null) {
            return;
        }
        var existing = form.querySelector('input[type="hidden"][name="' + name + '"]');
        if (existing && existing.getAttribute("data-type") !== "multiselect") {
            existing.value = value;
            return;
        }
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = name;
        input.value = value;
        form.appendChild(input);
    }

    function addAdditionalPostData(form) {
        var sessionId = null;
        try {
            sessionId = sessionStorage.getItem("IATSESSIONID");
        } catch (e) {
            sessionId = null;
        }
        if (!sessionId && typeof CookieUtil !== "undefined" && CookieUtil.get) {
            sessionId = CookieUtil.get("IATSESSIONID");
        }
        if (sessionId) {
            appendHidden(form, "IATSESSIONID", sessionId);
        }
        var segment = null;
        try {
            segment = sessionStorage.getItem("TestSegment");
        } catch (e2) {
            segment = null;
        }
        if (!segment && typeof CookieUtil !== "undefined" && CookieUtil.get) {
            segment = CookieUtil.get("TestSegment");
        }
        if (segment) {
            appendHidden(form, "TestSegment", segment);
        }
        var corrupted = "false";
        try {
            corrupted = sessionStorage.getItem("corrupted") || "false";
        } catch (e3) {
            corrupted = "false";
        }
        appendHidden(form, "corrupted", corrupted);
        appendHidden(form, "target", "adminV2");
        var referer = "-";
        try {
            referer = sessionStorage.getItem("HTTP_REFERER") || "-";
        } catch (e4) {
            referer = "-";
        }
        appendHidden(form, "HTTP_REFERER", referer);
        appendHidden(form, "NumItems", String(owners().length));
        try {
            if (sessionStorage.getItem("LastAdminPhase") === "true") {
                sessionStorage.clear();
            }
        } catch (e5) { /* ignore */ }
    }

    function checkboxesFor(n) {
        var nodes = document.querySelectorAll('input[type="checkbox"][name^="' + itemName(n) + '_"]');
        var list = Array.prototype.slice.call(nodes);
        list.sort(function (a, b) {
            var ai = parseInt(String(a.name).split("_")[1], 10) || 0;
            var bi = parseInt(String(b.name).split("_")[1], 10) || 0;
            return ai - bi;
        });
        return list;
    }

    function validateOwner(owner, list) {
        var type = String(owner.getAttribute("data-type") || "").toLowerCase();
        var n = parseInt(owner.getAttribute("data-item"), 10);
        var optional = flagTrue(owner.getAttribute("data-optional"));
        var li = questionLi(owner);
        var sentinel = ForceSubmit ? FORCE_UNANSWERED : UNANSWERED;

        function fail(msg) {
            if (ForceSubmit) {
                if (type === "truefalse" || type === "multichoice" || type === "likert") {
                    postRadioSentinel(n, FORCE_UNANSWERED);
                } else {
                    setFieldValue(n, FORCE_UNANSWERED);
                }
                return 1;
            }
            showError(list, li, msg);
            return 1;
        }

        if (type === "truefalse" || type === "multichoice" || type === "likert") {
            var radios = named(n);
            var picked = false;
            var r;
            for (r = 0; r < radios.length; r += 1) {
                if (radios[r].checked) {
                    picked = true;
                    break;
                }
            }
            if (picked) {
                return 0;
            }
            if (optional && !ForceSubmit) {
                postRadioSentinel(n, UNANSWERED);
                return 0;
            }
            return fail("Please select a response to the question below.");
        }

        if (type === "multiselect") {
            var boxes = checkboxesFor(n);
            var checked = 0;
            var bits = "";
            var b;
            for (b = 0; b < boxes.length; b += 1) {
                if (boxes[b].checked) {
                    checked += 1;
                    bits += "1";
                } else {
                    bits += "0";
                }
            }
            var minSel = parseInt(owner.getAttribute("data-min"), 10);
            var maxSel = parseInt(owner.getAttribute("data-max"), 10);
            if (isNaN(minSel)) {
                minSel = 0;
            }
            if (isNaN(maxSel)) {
                maxSel = boxes.length;
            }
            var hidden = document.getElementById(itemName(n)) || owner;
            if (checked === 0 && optional && !ForceSubmit) {
                hidden.value = UNANSWERED;
                return 0;
            }
            if (checked < minSel || checked > maxSel) {
                if (ForceSubmit) {
                    hidden.value = FORCE_UNANSWERED;
                    return 1;
                }
                if (checked < minSel) {
                    return fail(minSel === 1
                        ? "Please select at least one response to the question below."
                        : "Please select at least " + minSel + " responses to the question below.");
                }
                return fail(maxSel === 1
                    ? "Please select no more than 1 response to the question below."
                    : "Please select no more than " + maxSel + " responses to the question below.");
            }
            hidden.value = bits;
            return 0;
        }

        var raw = trimValue(owner);
        if (!raw) {
            if (optional && !ForceSubmit) {
                owner.value = UNANSWERED;
                return 0;
            }
            if (ForceSubmit) {
                owner.value = FORCE_UNANSWERED;
                return 1;
            }
            return fail("Please provide a response to the question below.");
        }

        if (type === "boundedtext") {
            var minLen = parseInt(owner.getAttribute("data-min-length"), 10);
            var maxLen = parseInt(owner.getAttribute("data-max-length"), 10);
            if (isNaN(minLen)) {
                minLen = 0;
            }
            if (isNaN(maxLen)) {
                maxLen = raw.length;
            }
            if (raw.length < minLen || raw.length > maxLen) {
                return fail("Please enter a response between " + minLen + " and " + maxLen + " characters.");
            }
            return 0;
        }

        if (type === "boundednumber") {
            if (!/^[-+]?(?:\d+\.?\d*|\.\d+)$/.test(raw)) {
                return fail("Please enter a number.");
            }
            var num = parseFloat(raw);
            var minN = parseFloat(owner.getAttribute("data-min"));
            var maxN = parseFloat(owner.getAttribute("data-max"));
            if (!isNaN(minN) && num < minN) {
                return fail("Please enter a number no less than " + minN + ".");
            }
            if (!isNaN(maxN) && num > maxN) {
                return fail("Please enter a number no greater than " + maxN + ".");
            }
            return 0;
        }

        if (type === "fixeddigit") {
            var digits = parseInt(owner.getAttribute("data-length"), 10);
            if (isNaN(digits) || digits < 1) {
                digits = raw.length;
            }
            if (!new RegExp("^\\d{" + digits + "}$").test(raw)) {
                return fail("Please enter exactly " + digits + " digits.");
            }
            return 0;
        }

        if (type === "regex") {
            var pattern = owner.getAttribute("data-pattern") || "";
            try {
                if (!new RegExp(pattern).test(raw)) {
                    return fail("Please enter a response that matches the requested format.");
                }
            } catch (ex) {
                return fail("Please enter a response that matches the requested format.");
            }
            return 0;
        }

        if (type === "date") {
            var parsed = parseMdY(raw);
            if (!parsed) {
                return fail("Please enter a date in MM/DD/YYYY format.");
            }
            if (flagTrue(owner.getAttribute("data-has-start"))) {
                var start = parseIsoDate(owner.getAttribute("data-start-date"));
                if (start && parsed < start) {
                    return fail("Please enter a date on or after " +
                        pad2(start.getMonth() + 1) + "/" + pad2(start.getDate()) + "/" + start.getFullYear() + ".");
                }
            }
            if (flagTrue(owner.getAttribute("data-has-end"))) {
                var end = parseIsoDate(owner.getAttribute("data-end-date"));
                if (end && parsed > end) {
                    return fail("Please enter a date on or before " +
                        pad2(end.getMonth() + 1) + "/" + pad2(end.getDate()) + "/" + end.getFullYear() + ".");
                }
            }
            return 0;
        }

        return 0;
    }

    function validateSurvey() {
        var form = surveyForm();
        var list = document.getElementById("QuestionList");
        clearErrors(list);
        var nErrors = 0;
        var items = owners();
        var i;
        for (i = 0; i < items.length; i += 1) {
            nErrors += validateOwner(items[i], list);
        }
        if (nErrors > 0 && !ForceSubmit) {
            setBanner();
        }
        return nErrors;
    }

    function submitForm(abort) {
        var form = surveyForm();
        if (!form) {
            return;
        }
        var button = document.getElementById("SubmitButton");
        if (button) {
            button.disabled = true;
        }
        addAdditionalPostData(form);
        if (abort) {
            appendHidden(form, "ABORT", "true");
        }
        submitted = true;
        form.submit();
    }

    function OnSubmit(evt) {
        if (evt && evt.preventDefault) {
            evt.preventDefault();
        }
        if (submitted) {
            return false;
        }
        if (typeof IsAborting !== "undefined" && IsAborting) {
            submitForm(true);
            return false;
        }
        if (ForceSubmit) {
            validateSurvey();
            submitForm(false);
            return false;
        }
        var nErrors = validateSurvey();
        if (nErrors > 0) {
            return false;
        }
        submitForm(false);
        return false;
    }

    function DoForceSubmit() {
        ForceSubmit = true;
        validateSurvey();
        submitForm(false);
    }

    function OnLoad() {
        var form = surveyForm();
        var button = document.getElementById("SubmitButton");
        if (button) {
            button.onclick = OnSubmit;
        }
        if (form && !form.getAttribute("onsubmit")) {
            form.onsubmit = OnSubmit;
        }

        try {
            if (!sessionStorage.getItem("HTTP_REFERER")) {
                if (typeof CookieUtil !== "undefined" && CookieUtil.checkCookie && CookieUtil.checkCookie("HTTP_REFERER")) {
                    sessionStorage.setItem("HTTP_REFERER", CookieUtil.get("HTTP_REFERER"));
                } else {
                    sessionStorage.setItem("HTTP_REFERER", "-");
                }
            }
            if (sessionStorage.getItem("IATSESSIONID") == null && typeof CookieUtil !== "undefined" && CookieUtil.get) {
                sessionStorage.setItem("IATSESSIONID", CookieUtil.get("IATSESSIONID"));
            }
            sessionStorage.setItem("TestURL", window.location.href);
            sessionStorage.setItem("corrupted", sessionStorage.getItem("corrupted") || "false");
            if (!sessionStorage.getItem("TestSegment") && typeof CookieUtil !== "undefined" && CookieUtil.get) {
                sessionStorage.setItem("TestSegment", CookieUtil.get("TestSegment"));
            }
            if (typeof CookieUtil !== "undefined" && CookieUtil.deleteCookie) {
                CookieUtil.deleteCookie("IATSESSIONID");
                CookieUtil.deleteCookie("AdminPhase");
                CookieUtil.deleteCookie("LastAdminPhase");
                CookieUtil.deleteCookie("HTTP_REFERER");
                CookieUtil.deleteCookie("Alternate");
                CookieUtil.deleteCookie("TestSegment");
            }
        } catch (e) { /* private mode / missing CookieUtil */ }

        if (typeof EventUtil !== "undefined" && EventUtil.addHandler && typeof OnPopState === "function") {
            EventUtil.addHandler(window, "popstate", OnPopState);
        }

        var timeoutMs = form ? parseInt(form.getAttribute("data-timeout"), 10) : 0;
        if (timeoutMs > 0) {
            timeoutHandle = setTimeout(DoForceSubmit, timeoutMs);
        }

        var main = document.getElementById("mainContent");
        if (main && main.focus) {
            try {
                main.focus();
            } catch (e2) { /* ignore */ }
        }
    }

    global.OnLoad = OnLoad;
    global.OnSubmit = OnSubmit;
    global.DoForceSubmit = DoForceSubmit;
    global.validateSurvey = validateSurvey;
})(window);
