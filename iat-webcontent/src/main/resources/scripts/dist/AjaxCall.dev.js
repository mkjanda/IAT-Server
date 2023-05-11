"use strict";

_ajg3 = new Array(), _ajg4 = 0;

function timeout() {
  return acf1.fEval([]);
}

var _acf1 = function _acf1(_p) {
  var _l = new Object();

  _l.v1 = _ajg2.indexOf("unanswered");
  _l._hr = true;
  _l._rv = null;
  return _l._rv;
  _ajg2[_l.v1] = "timed out";

  _ajg3[_l.v1].call(_ajg3[_l.v1].getMessage(), _ajg3[_l.v1].getOnSuccess(), _ajg3[_l.v1].getCallbackThis());
};

var acf1 = new UnencSubFunct(_acf1);

function OnAjaxStateChange(xmlhttp) {
  return acf2.fEval([xmlhttp]);
}

var _acf2 = function _acf2(_p) {
  var _l = new Object();

  _l._hr = false;
  _l._rv = null;
  _l._p = _p;
  _l.v1 = _ajg2.indexOf("unanswered");

  if (_l.v1 === -1) {
    acf2.s1_1.fEval(_l);
    if (_l._hr == true) return _l._rv;
  }

  _l.v2 = _ajg3[_l.v1];

  if (_l._p[0].readyState === 4 && (_l._p[0].status === 200 || !_l._p[0].status)) {
    acf2.s1_2.fEval(_l);
    if (_l._hr == true) return _l._rv;
  } else if (_l._p[0].readyState === 4 && (_l._p[0].status === 400 || _l._p[0].status === 403 || _l._p[0].status === 404)) {
    acf2.s1_3.fEval(_l);
    if (_l._hr == true) return _l._rv;
  } else if (_l._p[0].readyState === 4) {
    acf2.s1_4.fEval(_l);
    if (_l._hr == true) return _l._rv;
  }
};

_acf2.s1_1 = function (_l) {
  _l._hr = true;
  _l._rv = null;
  return _l._rv;
};

_acf2.s1_2 = function (_l) {
  window.clearTimeout(_l.v2.getTimeoutID());
  _ajg2[_l.v1] = "answered";

  if (_l.v2.getAccepts() == "text/xml") {
    acf2.s2_1.fEval(_l);
  } else {
    acf2.s2_2.fEval(_l);
  }
};

_acf2.s1_3 = function (_l) {
  window.clearTimeout(_l.v2.getTimeoutID());
  _ajg2[_l.v1] = "answered";
};

_acf2.s1_4 = function (_l) {
  window.clearTimeout(_l.v2.getTimeoutID());
  _ajg2[_l.v1] = "answered";

  if (_l.v2.getVersion() === 2) {
    acf2.s2_3.fEval(_l);
  } else if (_l.v2.getVersion() === 1) {
    acf2.s2_4.fEval(_l);
  }
};

_acf2.s2_1 = function (_l) {
  if (_l.v2.getCallbackThis()) _l.v2.processResponse(_l._p[0].responseXML, _l.v2.getOnSuccess(), _l.v2.getCallbackThis());else _l.v2.processResponse(_l._p[0].responseXML, _l.v2.getOnSuccess());
};

_acf2.s2_2 = function (_l) {
  if (_l.v2.getCallbackThis()) _l.v2.getOnSuccess().call(_l.v2.getCallbackThis(), _l._p[0].responseText);else _l.v2.getOnSuccess()(_l._p[0].responseText);
};

_acf2.s2_3 = function (_l) {
  _l.v2.call(_l.v2.getOnSuccess(), _l.v2.getCallbackThis(), _l.v2.getRequestMethod(), _l.v2.getMessage(), _l.v2.getContentType());
};

_acf2.s2_4 = function (_l) {
  _l.v2.call(_l.v2.getMessage(), _l.v2.getOnSuccess(), _l.v2.getCallbackThis());
};

var acf2 = new UnencSubFunct(_acf2);
acf2.s1_1 = new UnencSubFunct(_acf2.s1_1);
acf2.s1_2 = new UnencSubFunct(_acf2.s1_2);
acf2.s1_3 = new UnencSubFunct(_acf2.s1_3);
acf2.s1_4 = new UnencSubFunct(_acf2.s1_4);
acf2.s2_1 = new UnencSubFunct(_acf2.s2_1);
acf2.s2_2 = new UnencSubFunct(_acf2.s2_2);
acf2.s2_3 = new UnencSubFunct(_acf2.s2_3);
acf2.s2_4 = new UnencSubFunct(_acf2.s2_4);

function AjaxCall(destURL, rootURL, requestSrc, segmentID, accepts) {
  ac1.cEval(this, [destURL, rootURL, requestSrc, segmentID, accepts]);
  return this;
}

AjaxCall.prototype.constructor = AjaxCall;

AjaxCall.prototype.buildRequestDocument = function (request, requestData, requestType) {
  return ac1.acf1.cEval(this, [request, requestData, requestType]);
};

AjaxCall.prototype.recurseElement = function (elem) {
  return ac1.acf2.cEval(this, [elem]);
};

AjaxCall.prototype.getTextRepresentation = function (xmlDoc) {
  return ac1.acf3.cEval(this, [xmlDoc]);
};

AjaxCall.prototype.getRequestMethod = function () {
  return ac1.acf4.cEval(this, []);
};

AjaxCall.prototype.getContentType = function () {
  return ac1.acf5.cEval(this, []);
};

AjaxCall.prototype.processResponse = function (resp, onAjaxSuccess, functThis) {
  return ac1.acf6.cEval(this, [resp, onAjaxSuccess, functThis]);
};

AjaxCall.prototype.call = function (msgBody, onAjaxSuccess, functThis) {
  return ac1.acf7.cEval(this, [msgBody, onAjaxSuccess, functThis]);
};

AjaxCall.prototype.getMessage = function () {
  return ac1.acf8.cEval(this, []);
};

AjaxCall.prototype.getCallNdx = function () {
  return ac1.acf9.cEval(this, []);
};

AjaxCall.prototype.getVersion = function () {
  return ac1.acf10.cEval(this, []);
};

AjaxCall.prototype.getOnSuccess = function () {
  return ac1.acf11.cEval(this, []);
};

AjaxCall.prototype.getCallbackThis = function () {
  return ac1.acf12.cEval(this, []);
};

AjaxCall.prototype.getTimeoutID = function () {
  return ac1.acf13.cEval(this, []);
};

AjaxCall.prototype.getAccepts = function () {
  return ac1.acf14.cEval(this, []);
};

var _ac1 = function _ac1(_p) {
  var _l = new Object();

  _l._p = _p;
  this._c1_mv1 = _l._p[0];
  this._c1_mv2 = getQueryParam("ClientID");
  this._c1_mv3 = getQueryParam("IATName");
  this._c1_mv4 = _l._p[1];
  this._c1_mv5 = _l._p[2];
  this._c1_mv6 = _l._p[3];
  this._c1_mv7 = -1;
  if (!_l._p[4]) this._c1_mv8 = "text/xml";else this._c1_mv8 = _l._p[4];
  _l._hr = true;
  _l._rv = this;
  return _l._rv;
};

_ac1.acf1 = function (_p) {
  var _l = new Object();

  _l._p = _p;
  _l.v1 = document.implementation.createDocument("", "AjaxRequest", null);
  _l.v2 = _l.v1.createElement("Request");

  _l.v1.documentElement.setAttribute("RequestType", _l._p[2]);

  this._c1_mv9 = _l._p[0];

  _l.v2.appendChild(_l.v1.createTextNode(_l._p[0]));

  _l.v1.documentElement.appendChild(_l.v2);

  _l.v2 = _l.v1.createElement("RequestData");

  _l.v2.appendChild(_l.v1.createCDATASection(_l._p[1]));

  _l.v1.documentElement.appendChild(_l.v2);

  _l.v2 = _l.v1.createElement("ClientID");

  _l.v2.appendChild(_l.v1.createTextNode(parseInt(this._c1_mv2, 10)));

  _l.v1.documentElement.appendChild(_l.v2);

  _l.v2 = _l.v1.createElement("IATName");

  _l.v2.appendChild(_l.v1.createTextNode(this._c1_mv3));

  _l.v1.documentElement.appendChild(_l.v2);

  _l.v2 = _l.v1.createElement("Host");

  _l.v2.appendChild(_l.v1.createTextNode(window.location.hostname));

  _l.v1.documentElement.appendChild(_l.v2);

  _l.v2 = _l.v1.createElement("RootContext");

  _l.v2.appendChild(_l.v1.createTextNode(this._c1_mv4));

  _l.v1.documentElement.appendChild(_l.v2);

  _l.v2 = _l.v1.createElement("TestSegmentID");

  _l.v2.appendChild(_l.v1.createTextNode(this._c1_mv6));

  _l.v1.documentElement.appendChild(_l.v2);

  _l._hr = true;
  _l._rv = this.getTextRepresentation(_l.v1);
  return _l._rv;
};

_ac1.acf2 = function (_p) {
  var _l = new Object();

  _l._p = _p;
  _l.v1 = "\n\r<" + _l._p[0].localName;

  if (_l._p[0].hasAttributes()) {
    ac1.acf2.s1_1.cEval(this, _l);
  }

  _l.v1 += ">";

  if (_l._p[0].nodeType === 1) {
    ac1.acf2.s1_2.cEval(this, _l);
  }

  for (_l.v2 = 0; _l.v2 < _l._p[0].childNodes.length; _l.v2++) {
    ac1.acf2.s1_3.cEval(this, _l);
  }

  _l.v1 += "</" + _l._p[0].localName + ">";
  _l._hr = true;
  _l._rv = _l.v1;
  return _l._rv;
};

_ac1.acf2.s1_1 = function (_l) {
  for (_l.v2 = 0; _l.v2 < _l._p[0].attributes.length; _l.v2++) {
    ac1.acf2.s2_1.cEval(this, _l);
  }
};

_ac1.acf2.s1_2 = function (_l) {
  for (_l.v2 = 0; _l.v2 < _l._p[0].childNodes.length; _l.v2++) {
    if (_l._p[0].childNodes[_l.v2].nodeType === 1) _l.v1 += this.recurseElement(_l._p[0].childNodes[_l.v2]);
  }
};

_ac1.acf2.s1_3 = function (_l) {
  if (_l._p[0].childNodes[_l.v2].nodeType === 3) _l.v1 += _l._p[0].childNodes[_l.v2].nodeValue;else if (_l._p[0].childNodes[_l.v2].nodeType === 4) _l.v1 += "<![CDATA[" + _l._p[0].childNodes[_l.v2].data + "]]>";
};

_ac1.acf2.s2_1 = function (_l) {
  _l.v3 = _l._p[0].attributes.item(_l.v2).nodeName;
  _l.v1 += " " + _l.v3 + "=\"" + _l._p[0].getAttribute(_l.v3) + "\"";
};

_ac1.acf3 = function (_p) {
  var _l = new Object();

  _l._p = _p;
  _l.v1 = "<?xml version=\'1.0\' encoding=\'UTF-8\' ?>\r\n";
  _l.v1 += this.recurseElement(_l._p[0].documentElement).trim();
  _l._hr = true;
  _l._rv = _l.v1;
  return _l._rv;
};

_ac1.acf4 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c1_mv15;
  return _l._rv;
};

_ac1.acf5 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = "text/xml";
  return _l._rv;
};

_ac1.acf6 = function (_p) {
  var _l = new Object();

  _l._p = _p;
  _l.v1 = _l._p[0].documentElement.getAttribute("ResponseType");
  _l.v2 = "";
  _l.v4 = _l._p[0].documentElement.getElementsByTagName("ErrorParam");
  _l.v5 = "";

  if (_l.v1 === "Error") {
    ac1.acf6.s1_1.cEval(this, _l);
  } else if (_l.v1 === "Text") {
    ac1.acf6.s1_2.cEval(this, _l);
  } else if (_l.v1 === "XML") {
    ac1.acf6.s1_3.cEval(this, _l);
  }
};

_ac1.acf6.s1_1 = function (_l) {
  for (ctr = 0; ctr < _l.v4.length; ctr++) {
    ac1.acf6.s2_1.cEval(this, _l);
  }

  window.location.assign("/ServerError.html?" + _l.v5);
};

_ac1.acf6.s1_2 = function (_l) {
  _l.v3 = _l._p[0].documentElement.getElementsByTagName("Response")[0];
  _l.v3 = _l.v3.firstChild;

  while (_l.v3.nodeType !== 3 && _l.v3.nodeType !== 4) {
    _l.v3 = _l.v3.nextSibling;
  }

  if (_l.v3.nodeType === 3) {
    ac1.acf6.s2_2.cEval(this, _l);
  } else _l.v2 = _l.v3.data;

  if (_l._p[2]) _l._p[1].call(_l._p[2], _l.v2);else _l._p[1](_l.v2);
};

_ac1.acf6.s1_3 = function (_l) {
  _l.v3 = _l._p[0].documentElement.firstChild;

  while (_l.v3.nodeType !== 1) {
    _l.v3 = _l.v3.nextSibling;
  }

  if (_l._p[2]) _l._p[1].call(_l._p[2], _l.v3);else _l._p[1](_l.v3);
};

_ac1.acf6.s2_1 = function (_l) {
  if (ctr !== 0) _l.v5 += "&";
  _l.v3 = _l.v4[ctr].getElementsByTagName("Name")[0];

  while (_l.v3.nodeType !== 1) {
    _l.v3 = _l.v3.nextSibling;
  }

  _l.v5 += _l.v3.childNodes[0].nodeValue + "=";
  _l.v3 = _l.v4[ctr].getElementsByTagName("Value")[0];

  while (_l.v3.nodeType !== 1) {
    _l.v3 = _l.v3.nextSibling;
  }

  _l.v5 += _l.v3.childNodes[0].nodeValue;
};

_ac1.acf6.s2_2 = function (_l) {
  while (_l.v3) {
    ac1.acf6.s3_1.cEval(this, _l);
  }

  _l.v2 = _l.v2.substring("<![CDATA[".length, _l.v2.lastIndexOf("]]>"));
};

_ac1.acf6.s3_1 = function (_l) {
  _l.v2 += _l.v3.data;
  _l.v3 = _l.v3.nextSibling;
};

_ac1.acf7 = function (_p) {
  var _l = new Object();

  _l._p = _p;
  if (_l._p[2]) this._c1_mv10 = _l._p[2];else this._c1_mv10 = null;
  this._c1_mv11 = _l._p[0];
  this._c1_mv12 = _l._p[1];

  if (window.XMLHttpRequest) {
    ac1.acf7.s1_1.cEval(this, _l);
  } else {
    ac1.acf7.s1_2.cEval(this, _l);
  }

  this._c1_mv13 = _l.v1;
  this._c1_mv7 = _ajg2.length;

  _l.v1.onreadystatechange = function () {
    OnAjaxStateChange(_l.v1);
  };

  this._c1_mv14 = window.setTimeout(timeout, 30000);

  _ajg2.push("unanswered");

  _ajg3.push(this);

  this._c1_mv15 = "POST";

  _l.v1.open("POST", this._c1_mv1 + "?" + new Date().getTime().toString(), true);

  _l.v1.setRequestHeader("IATSESSIONID", sessionStorage.getItem("IATSESSIONID"));

  _l.v1.setRequestHeader("Content-Type", "text/xml");

  _l.v1.setRequestHeader("Accept", this._c1_mv8);

  _l.v1.send(_l._p[0]);
};

_ac1.acf7.s1_1 = function (_l) {
  _l.v1 = new XMLHttpRequest();
};

_ac1.acf7.s1_2 = function (_l) {
  _l.v1 = new ActiveXObject("Microsoft.XMLHTTP");
};

_ac1.acf8 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c1_mv11;
  return _l._rv;
};

_ac1.acf9 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c1_mv7;
  return _l._rv;
};

_ac1.acf10 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = 1;
  return _l._rv;
};

_ac1.acf11 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c1_mv12;
  return _l._rv;
};

_ac1.acf12 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c1_mv10;
  return _l._rv;
};

_ac1.acf13 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c1_mv14;
  return _l._rv;
};

_ac1.acf14 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c1_mv8;
  return _l._rv;
};

var ac1 = new UnencSubFunct(_ac1);
ac1.acf1 = new UnencSubFunct(_ac1.acf1);
ac1.acf2 = new UnencSubFunct(_ac1.acf2);
ac1.acf2.s1_1 = new UnencSubFunct(_ac1.acf2.s1_1);
ac1.acf2.s1_2 = new UnencSubFunct(_ac1.acf2.s1_2);
ac1.acf2.s1_3 = new UnencSubFunct(_ac1.acf2.s1_3);
ac1.acf2.s2_1 = new UnencSubFunct(_ac1.acf2.s2_1);
ac1.acf3 = new UnencSubFunct(_ac1.acf3);
ac1.acf4 = new UnencSubFunct(_ac1.acf4);
ac1.acf5 = new UnencSubFunct(_ac1.acf5);
ac1.acf6 = new UnencSubFunct(_ac1.acf6);
ac1.acf6.s1_1 = new UnencSubFunct(_ac1.acf6.s1_1);
ac1.acf6.s1_2 = new UnencSubFunct(_ac1.acf6.s1_2);
ac1.acf6.s1_3 = new UnencSubFunct(_ac1.acf6.s1_3);
ac1.acf6.s2_1 = new UnencSubFunct(_ac1.acf6.s2_1);
ac1.acf6.s2_2 = new UnencSubFunct(_ac1.acf6.s2_2);
ac1.acf6.s3_1 = new UnencSubFunct(_ac1.acf6.s3_1);
ac1.acf7 = new UnencSubFunct(_ac1.acf7);
ac1.acf7.s1_1 = new UnencSubFunct(_ac1.acf7.s1_1);
ac1.acf7.s1_2 = new UnencSubFunct(_ac1.acf7.s1_2);
ac1.acf8 = new UnencSubFunct(_ac1.acf8);
ac1.acf9 = new UnencSubFunct(_ac1.acf9);
ac1.acf10 = new UnencSubFunct(_ac1.acf10);
ac1.acf11 = new UnencSubFunct(_ac1.acf11);
ac1.acf12 = new UnencSubFunct(_ac1.acf12);
ac1.acf13 = new UnencSubFunct(_ac1.acf13);
ac1.acf14 = new UnencSubFunct(_ac1.acf14);

function AjaxCallv2(destURL, rootURL, segmentID, accepts) {
  ac2.cEval(this, [destURL, rootURL, segmentID, accepts]);
  return this;
}

AjaxCallv2.prototype = new AjaxCall();
AjaxCallv2.prototype.constructor = AjaxCallv2;

AjaxCallv2.prototype.call = function (onAjaxSuccess, functThis, requestMethod, msgBody, contentType) {
  return ac2.acf1.cEval(this, [onAjaxSuccess, functThis, requestMethod, msgBody, contentType]);
};

AjaxCallv2.prototype.getOnSuccess = function () {
  return ac2.acf2.cEval(this, []);
};

AjaxCallv2.prototype.getVersion = function () {
  return ac2.acf3.cEval(this, []);
};

AjaxCallv2.prototype.getCallbackThis = function () {
  return ac2.acf4.cEval(this, []);
};

AjaxCallv2.prototype.getRequestMethod = function () {
  return ac2.acf5.cEval(this, []);
};

AjaxCallv2.prototype.getContentType = function () {
  return ac2.acf6.cEval(this, []);
};

AjaxCallv2.prototype.getMessage = function () {
  return ac2.acf7.cEval(this, []);
};

AjaxCallv2.prototype.getTimeoutID = function () {
  return ac2.acf8.cEval(this, []);
};

var _ac2 = function _ac2(_p) {
  var _l = new Object();

  _l._p = _p;
  AjaxCall.call(this, _l._p[0], _l._p[1], "", _l._p[2], _l._p[3]);
  this._c2_mv1 = _l._p[0];
  _l._hr = true;
  _l._rv = this;
  return _l._rv;
};

_ac2.acf1 = function (_p) {
  var _l = new Object();

  _l._p = _p;
  this._c2_mv2 = _l._p[4];
  this._c2_mv3 = _l._p[2];
  if (_l._p[1]) this._c2_mv4 = _l._p[1];else this._c2_mv4 = null;
  this._c2_mv5 = _l._p[3];
  this._c2_mv6 = _l._p[0];

  if (window.XMLHttpRequest) {
    ac2.acf1.s1_1.cEval(this, _l);
  } else {
    ac2.acf1.s1_2.cEval(this, _l);
  }

  this._c2_mv7 = _l.v1;
  this._c2_mv8 = _ajg2.length;

  _l.v1.onreadystatechange = function () {
    OnAjaxStateChange(_l.v1);
  };

  this._c2_mv9 = window.setTimeout(timeout, 30000);

  _ajg2.push("unanswered");

  _ajg3.push(this);

  _l.v1.open(_l._p[2], this._c2_mv1 + "?" + new Date().getTime().toString(), true);

  _l.v1.setRequestHeader("IATSESSIONID", sessionStorage.getItem("IATSESSIONID"));

  if (_l._p[4]) {
    ac2.acf1.s1_3.cEval(this, _l);
  }

  _l.v1.setRequestHeader("Accept", this.getAccepts());

  if (_l._p[3]) {
    ac2.acf1.s1_4.cEval(this, _l);
  } else {
    ac2.acf1.s1_5.cEval(this, _l);
  }
};

_ac2.acf1.s1_1 = function (_l) {
  _l.v1 = new XMLHttpRequest();
};

_ac2.acf1.s1_2 = function (_l) {
  _l.v1 = new ActiveXObject("Microsoft.XMLHTTP");
};

_ac2.acf1.s1_3 = function (_l) {
  _l.v1.setRequestHeader("Content-Type", _l._p[4]);
};

_ac2.acf1.s1_4 = function (_l) {
  _l.v1.send(_l._p[3]);
};

_ac2.acf1.s1_5 = function (_l) {
  _l.v1.send();
};

_ac2.acf2 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c2_mv6;
  return _l._rv;
};

_ac2.acf3 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = 2;
  return _l._rv;
};

_ac2.acf4 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c2_mv4;
  return _l._rv;
};

_ac2.acf5 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c2_mv3;
  return _l._rv;
};

_ac2.acf6 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c2_mv2;
  return _l._rv;
};

_ac2.acf7 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c2_mv5;
  return _l._rv;
};

_ac2.acf8 = function (_p) {
  var _l = new Object();

  _l._hr = true;
  _l._rv = this._c2_mv9;
  return _l._rv;
};

var ac2 = new UnencSubFunct(_ac2);
ac2.acf1 = new UnencSubFunct(_ac2.acf1);
ac2.acf1.s1_1 = new UnencSubFunct(_ac2.acf1.s1_1);
ac2.acf1.s1_2 = new UnencSubFunct(_ac2.acf1.s1_2);
ac2.acf1.s1_3 = new UnencSubFunct(_ac2.acf1.s1_3);
ac2.acf1.s1_4 = new UnencSubFunct(_ac2.acf1.s1_4);
ac2.acf1.s1_5 = new UnencSubFunct(_ac2.acf1.s1_5);
ac2.acf2 = new UnencSubFunct(_ac2.acf2);
ac2.acf3 = new UnencSubFunct(_ac2.acf3);
ac2.acf4 = new UnencSubFunct(_ac2.acf4);
ac2.acf5 = new UnencSubFunct(_ac2.acf5);
ac2.acf6 = new UnencSubFunct(_ac2.acf6);
ac2.acf7 = new UnencSubFunct(_ac2.acf7);
ac2.acf8 = new UnencSubFunct(_ac2.acf8);