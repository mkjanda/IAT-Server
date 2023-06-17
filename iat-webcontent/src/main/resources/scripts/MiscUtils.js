var CookieUtil = {
    get:
            function(name) {
                var i, x, y, ARRcookies = document.cookie.split(";");
                for (i = 0; i < ARRcookies.length; i++)
                {
                    x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
                    y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
                    x = x.replace(/^\s+|\s+$/g, "");
                    if (x === name)
                        return decodeURIComponent(y);
                }
                return null;
            },
    set: function (name, value) {
        var c_value = encodeURIComponent(value);
        document.cookie = name + "=" + c_value;
    },
    checkCookie: function(name) {
        var cookieValue = this.get(name);
        return ((cookieValue !== null) && (cookieValue !== ""));
    },
    deleteCookie : function(name) {
        document.cookie = name + "=; expires=" + new Date(0).toISOString();
    },
    domain : undefined,
    path : undefined
};
function EventUtilClass() {
    this.handlers = new Array();
    return this;
}

EventUtilClass.prototype = {
    constructor: EventUtilClass,
    attachHandler: function(element, type, hFunct) {
        if (element.addEventListener)
            element.addEventListener(type, hFunct, false);
        else if (element.attachEvent)
            element.attachEvent("on" + type, hFunct);
        else
            element["on" + type] = hFunct;
        for (var ctr = 0; ctr < element.childNodes.length; ctr++)
            this.attachHandler(element.childNodes[ctr], type, hFunct);
    },
    addHandler:
            function(element, type, handler, target) {
                if (target) {
                    var hFunct = function(event) {
                        handler.call(target, event);
                    };
                    this.handlers.push({"handler": handler, "hFunct": hFunct});
                    if (element == document.documentElement)
                        this.attachHandler(document.documentElement, type, hFunct);
                    else {
                        if (element.addEventListener)
                            element.addEventListener(type, hFunct, false);
                        else if (element.attachEvent)
                            element.attachEvent("on" + type, hFunct);
                        else
                            element["on" + type] = hFunct;

                    }
                } else {
                    if (element == document.documentElement)
                        this.attachHandler(document.documentElement, type, handler);
                    else {
                        if (element.addEventListener)
                            element.addEventListener(type, handler, false);
                        else if (element.attachEvent)
                            element.attachEvent("on" + type, handler);
                        else
                            element["on" + type] = handler;
                    }
                }

            },
    handler:
            function(event) {
                this.targetHandler.call(this.target, event);
            },
    getEvent:
            function(event) {
                return event ? event : window.event;
            },
    getTarget:
            function(event) {
                return event.target || event.srcElement;
            },
    preventDefault:
            function(event) {
                if (event.preventDefault)
                    event.preventDefault();
                else
                    event.returnValue = false;
            },
    detachHandler:
            function(element, type, handler) {
                if (element.removeEventListener)
                    element.removeEventListener(type, handler, false);
                else if (element.detachEvent)
                    element.detachEvent("on" + type, handler);
                else
                    element["on" + type] = null;
                for (var ctr = 0; ctr < element.childNodes.length; ctr++)
                    this.detachHandler(element.childNodes[ctr], type, handler);
            },
    removeHandler:
            function(element, type, handler, target) {
                if (target) {
                    var hFunct;
                    for (var ctr = 0; ctr < this.handlers.length; ctr++)
                        if (this.handlers[ctr].handler == handler) {
                            hFunct = this.handlers[ctr].hFunct;
                            this.handlers.splice(ctr, 1);
                        }
                    if (element == document.documentElement)
                        this.detachHandler(element, type, hFunct);
                    else {
                        if (element.removeEventListener)
                            element.removeEventListener(type, hFunct, false);
                        else if (element.detachEvent)
                            element.detachEvent("on" + type, hFunct);
                        else
                            element["on" + type] = null;
                    }
                } else {
                    if (element == document.documentElement)
                        this.detachHandler(element, type, handler);
                    else {
                        if (element.removeEventListener)
                            element.removeEventListener(type, handler, false);
                        else if (element.detachEvent)
                            element.detachEvent("on" + type, handler);
                        else
                            element["on" + type] = null;
                    }
                }
            },
    stopPropogation:
            function(event) {
                if (event.stopPropogation)
                    event.stopPropogation();
                else
                    event.cancelBubble = true;
            },
    getCharCode:
            function(event) {
                if (typeof(event.charCode) == "number")
                    return event.charCode;
                else
                    return event.keyCode;
            },
            bind : function(funct, functThis) {
                return funct.bind(functThis);
            }
};

var EventUtil = new EventUtilClass();

function createSingleParamTargetFunction(t, f) {
	return function(e) { f.call(t, e); };
}


function getQueryParam(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.location.search);
    if (results == null)
        return "";
    else
        return decodeURIComponent(results[1].replace(/\+/g, " "));
}
