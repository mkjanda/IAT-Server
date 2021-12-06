
function fillText(txt, fnt, patURL, patAlpha, canvas) {
	"use strict";
	var context = canvas.getContext("2d");
	var img = new Image();
	img.onload = function() { 
		context.font = fnt;
		context.textAlign = "center";
		context.textBaseline = "middle";
		context.fillStyle = context.createPattern(img, "repeat");
		context.fillText(txt, canvas.width / 2, canvas.height / 2);
		context.strokeWidth = 2;
		context.strokeStyle = "rgba(0, 0, 0, 1)";
		context.strokeText(txt, canvas.width / 2, canvas.height / 2);
	};
	img.src = patURL;
}

function strokeAndFillText(txt, fnt, patURL, color, strokeWidth, patAlpha, canvas) {
	"use strict";
	var context = canvas.getContext("2d");
	var img = new Image();
	context.strokeStyle = color;
	context.font = fnt;
	context.textAlign = "center";
	context.textBaseline = "middle";
	context.strokeWidth = strokeWidth;
	img.onload = function() { 
		context.fillStyle = context.createPattern(img, "repeat");
		context.fillText(txt, canvas.width / 2, canvas.height / 2);
		context.strokeText(txt, canvas.width / 2, canvas.height / 2);
	};
	img.src = patURL;
}

function strokeText(txt, fnt, color, strokeWidth, canvas) {
	"use strict";
	var context = canvas.getContext("2d");
	context.font = fnt;
	context.textAlign = "center";
	context.textBaseAlign = "middle";
	context.strokeStyle = color;
	context.strokeWidth = strokeWidth;
	context.strokeText(txt, canvas.width / 2, canvas.height / 2);
}

function drawGradientSubHeader(txt, fontSize, fontFamily, canvas) {
	"use strict";
	var ctx = canvas.getContext("2d");
	ctx.font = fontSize.toString() + "px " + fontFamily;
	var txtWidth = ctx.measureText(txt).width;        
    var gradient = ctx.createLinearGradient(0, 0, canvas.width, canvas.height);
    gradient.addColorStop(0, "#F7CAC9");
	gradient.addColorStop((canvas.width - txtWidth) / (2 * canvas.width), "#F7CAC9");
	gradient.addColorStop((canvas.width - txtWidth * 0.333) / (2 * canvas.width), "#92A8D1");
	gradient.addColorStop((canvas.width - txtWidth * -0.333) / (2 * canvas.width), "#B7BE98");
	gradient.addColorStop((canvas.width + txtWidth) / (2 * canvas.width), "#B1869F");
    gradient.addColorStop(1, "#B1869F");
    ctx.fillStyle = gradient;
	ctx.strokeStyle = "#343637";
	ctx.lineWidth = 3;
	ctx.strokeText(txt, (canvas.width - txtWidth) / 2, fontSize);
	ctx.fillText(txt, (canvas.width - txtWidth) / 2, fontSize);     
}

