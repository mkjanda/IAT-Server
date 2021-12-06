function UnencSubFunct(funct)
{
    this._Funct = funct;
    return this;
}

UnencSubFunct.prototype = {
    constructor: UnencSubFunct,
    cEval: function(t, params) {
        return this._Funct.call(t, params);
    },
    fEval: function(params) {
        return this._Funct(params);
    }
};

function TextSubFunct(funct)
{
    UnencSubFunct.call(this, eval("(" + funct + ")"));
    return this;
}

TextSubFunct.prototype = new UnencSubFunct();
TextSubFunct.prototype.constructor = TextSubFunct;
