"use strict";

function UnencSubFunct(funct) {
  this._Funct = funct;
  return this;
}

UnencSubFunct.prototype = {
  constructor: UnencSubFunct,
  cEval: function cEval(t, params) {
    return this._Funct.call(t, params);
  },
  fEval: function fEval(params) {
    return this._Funct(params);
  }
};

function TextSubFunct(funct) {
  UnencSubFunct.call(this, eval("(" + funct + ")"));
  return this;
}

TextSubFunct.prototype = new UnencSubFunct();
TextSubFunct.prototype.constructor = TextSubFunct;