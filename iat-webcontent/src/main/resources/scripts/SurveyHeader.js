function appendFormData(name, value) {
    const form = document.querySelector("form");
    const elem = document.createElement("input");
    elem.type = "hidden";
    elem.name = name;
    elem.value = value;
    form.appendChild(elem);
}   

window.onpopstate = function(evt) {
    const url = sessionStorage.getItem("HTTP_REFERER");
    if (url !== null && url !== "-") {
        window.location.assign(url || '/');
    }
}

window.onload = async function() {
    const sessionCookie = await cookieStore.get("IATSESSIONID");
    sessionStorage.setItem("IATSESSIONID", sessionCookie.value);
    const adminPhaseCookie = await cookieStore.get("AdminPhase");
    const adminPhase = parseInt(adminPhaseCookie.value, 10);
    if (adminPhase === 0)
        sessionStorage.setItem("HTTP_REFERER", document.referrer);
}

window.onbeforeunload = function() {
    const sessId = sessionStorage.getItem("IATSESSIONID");
    appendFormData("IATSESSIONID", sessId);
}
