function inherits(subClass, superClass) {
	"use strict";
	var superProps = Object.getOwnPropertyNames(superClass.prototype);
	for (var prop in superProps) {
		if (!Object.getOwnPropertyDescriptor(subClass.prototype, superProps[prop])) {
			Object.defineProperty(subClass.prototype, superProps[prop], Object.getOwnPropertyDescriptor(superClass.prototype, superProps[prop]));
		}
	}
}

var GradType = { diagonal : 1, leftToRight : 2, topToBottom : 3 };
var Align = { left : 1, center : 2, right : 3 };
var RectangleStyle = { fill : 1, draw : 2, none : 3 };
var DisplayItemType = { paragraph : 1, image : 2, footer : 3, header: 4, spriteSheetAnimation : 5 };
var displaySequence = undefined;


function Size(width, height) {
	"use strict";
	this.szX = width;
	this.szY = height;
	return this;
}

Size.prototype = {
	constructor: Size,
	get width() {
		"use strict";
		return this.szX;
	},
	set width(val) {
		"use strict";
		this.szX = val;
	},
	get height() {
		"use strict";
		return this.szY;
	},
	set height(val) {
		"use strict";
		this.szY = val;
	}
};
	

function Point(x, y) {
	"use strict";
	this._x = x;
	this._y = y;
	return this;
}

Point.prototype = {
	constructor : Point,
	get x() {
		"use strict";
		return this._x;
	},
	get y() {
		"use strict";
		return this._y;
	}
};

function FrameComponent()
{
	"use strict";
	return this;
}

FrameComponent.prototype = {
	constructor : FrameComponent,
	display : function(ctx) { alert("Call to abstract display method."); }
};

function Rectangle(left, top, width, height, style) {
	"use strict";
	FrameComponent.call(this);
	this.left = left;
	this.right = left + width;
	this.top = top;
	this.bottom = top + height;
	if (style) {
		this.style = style;
	} else {
		this.style = RectangleStyle.none;
	}
	return this;
}

Rectangle.prototype = {
	constructor : Rectangle,
	get width() {
		"use strict";
		return this.right - this.left;
	},
	get height() {
		"use strict";
		return this.bottom - this.top;
	},
	get left() {
		"use strict";
		return this.l;
	},
	set left(val) {
		"use strict";
		this.l = val;
	},
	get top() {
		"use strict";
		return this.t;
	},
	set top(val) {
		"use strict";
		this.t = val;
	},
	get right() {
		"use strict";
		return this.r;
	},
	set right(val) {
		"use strict";
		this.r = val;
	},
	get bottom() {
		"use strict";
		return this.b;
	},
	set bottom(val) {
		"use strict";
		this.b = val;
	},
	offset : function(pt) {
		"use strict";
		this.l += pt.x;
		this.r += pt.x;
		this.t += pt.y;
		this.b += pt.y;
	},
	hitTest : function(pt) {
		"use strict";
		if ((pt.x >= this.left) && (pt.x <= this.right) && (pt.y >= this.top) && (pt.y <= this.bottom)) {
			return true;
		}
		return false;
	},
	draw : function(ctx) {
		"use strict";
		ctx.strokeRect(this.left, this.top, this.width, this.height);
	},
	fill : function(ctx) {
		"use strict";
		ctx.fillRect(this.left, this.top, this.width, this.height);
	},
	drawImage : function(ctx, img) {
		"use strict";
		if ((img.width <= this.width) && (img.height <= this.height)) {
			ctx.drawImage(img, this.left + (this.width - img.width) * 0.5, this.top + (this.height - img.height) * 0.5);
			return;
		}
		var imgAR = img.width / img.height;
		var AR = this.width / this.height;
		var imgWidth = this.width, imgHeight = this.height;
		if (imgAR > AR) {
			imgWidth = this.width;
			imgHeight = imgWidth * img.height / img.width;
		} else {
			imgHeight = this.height;
			imgWidth = imgHeight * img.width / img.height;
		}
		ctx.drawImage(img, this.left + (this.width - imgWidth) * 0.5, this.top + (this.height - imgHeight) * 0.5, imgWidth, imgHeight);
	},
	display : function(ctx) {
		"use strict";
		switch (this.style) {
			case RectangleStyle.fill:
				this.fill(ctx);
				break;
				
			case RectangleStyle.draw:
				this.draw(ctx);
				break;
		}
	}
};
inherits(Rectangle, FrameComponent);


function Text(str, fontSize, fontFamily, boundingRect, align, textIndent) {
	"use strict";
	FrameComponent.call(this);
	this.t = str;
	if (align) {
		this.align = align;
	} else {
		this.align = Align.left;
	}
	this.fontSize = fontSize;
	this.fontFamily = fontFamily;
	this.boundingRect = boundingRect;
	this.textIndent = textIndent;
	return this;
}

Text.prototype = {
	constructor : Text,
	get text() {
		"use strict";
		return this.t;
	},
	get lineHeight() {
		"use strict";
		return this.fontSize * 9 / 8;
	},
	parseString : function(width, ctx) {
		"use strict";
		var lines = [];
		var line;
		var origText = this.text;
		if ((this.align === Align.left) && (this.textIndent)) {
		    line = origText;
			if (ctx.measureText(line).width > width - this.textIndent) {
		    while (ctx.measureText(line).width > width - this.textIndent) {
				line = line.substr(0, line.length - 1);
			}
    		line = (line.trim().match(/(.+)\s+[^\s]*$/)) ? line.trim().match(/(.+)\s+[^\s]*$/)[1] : line;
			}
			lines.push(line.trim());
			origText = origText.substring(line.length).trim();
		}
		while (origText.length > 0) {
			line = origText;
			if (ctx.measureText(line).width > width) {
			    while (ctx.measureText(line).width > width) {
					line = line.substr(0, line.length - 1);
				}
    			line = (line.trim().match(/(.+)\s+[^\s]*$/)) ? line.trim().match(/(.+)\s+[^\s]*$/)[1] : line;
			}
			lines.push(line.trim());
			origText = origText.substring(line.length).trim();
		}
		return lines;
	},
	drawText : function(ctx, txtFill) {
		"use strict";
		ctx.save();
		ctx.textBaseline = "top";
		ctx.font = this.fontSize.toString() + "px " + this.fontFamily;
		ctx.fillStyle = txtFill;
		var lines = this.parseString(this.boundingRect.width, ctx), ctr;
		for (ctr = 0; ctr < lines.length; ctr++) {
			var lineWidth = ctx.measureText(lines[ctr]).width;
			switch (this.align) {
				case Align.left:
					if ((this.textIndent > 0) && (ctr === 0)) {
						ctx.fillText(lines[ctr], this.boundingRect.left + this.textIndent, this.boundingRect.top);
					} else {
						ctx.fillText(lines[ctr], this.boundingRect.left, this.boundingRect.top + this.lineHeight * ctr);
					}
					break;
					
				case Align.center:
					ctx.fillText(lines[ctr], this.boundingRect.left + (this.boundingRect.width - lineWidth) / 2, this.boundingRect.top + this.lineHeight * ctr);
					break;
					
				case Align.right:
					ctr.fillText(lines[ctr], this.boundingRect.right - lineWidth, this.boundingRect.top + this.lineHeight * ctr);
					break;
			}
		}
		ctx.restore();
	},
	drawPatternedText : function(ctx, img) {
		"use strict";
		var patt = ctx.createPattern(img, "repeat");
		this.drawText(ctx, patt);
	},
	strokeText : function(ctx, strokeWidth, strokeStyle, strokeOpacity) {
		"use strict";
		ctx.save();
		ctx.textBaseline = "top";
		ctx.font = this.fontSize.toString() + "px " + this.fontFamily;
		ctx.lineJoin = "bevel";
		ctx.strokeStyle = strokeStyle;
		ctx.globalAlpha = strokeOpacity;
		ctx.lineWidth = strokeWidth;
		var lines = this.parseString(this.boundingRect.width, ctx), ctr;
		for (ctr = 0; ctr < lines.length; ctr++) {
			var lineWidth = ctx.measureText(lines[ctr]).width;
			switch (this.align) {
				case Align.left:
					if ((this.textIndent > 0) && (ctr === 0)) {
						ctx.strokeText(lines[ctr], this.boundingRect.left + this.textIndent, this.boundingRect.top);
					} else {
						ctx.strokeText(lines[ctr], this.boundingRect.left, this.boundingRect.top + this.lineHeight * ctr);
					}
					break;
					
				case Align.center:
					ctx.strokeText(lines[ctr], this.boundingRect.left + (this.boundingRect.width - lineWidth) / 2, this.boundingRect.top + this.lineHeight * ctr);
					break;
					
				case Align.right:
					ctr.strokeText(lines[ctr], this.boundingRect.right - lineWidth, this.boundingRect.top + this.lineHeight * ctr);
					break;
			}
		}
		ctx.restore();
	}
};
inherits(Text, FrameComponent);
function ColoredText(txt, fontSize, fontFamily, boundingRect, align, color, textIndent) {
	"use strict";
	Text.call(this, txt, fontSize, fontFamily, boundingRect, align, textIndent);
	this.color = color;
	return this;
}

ColoredText.prototype = {
	constructor : ColoredText,
	display : function(ctx) {
		"use strict";
		this.drawText(ctx, this.color);
	}
};
inherits(ColoredText, Text);

function PatternedText(txt, fontSize, fontFamily, boundingRect, align, img, textIndent, opacity) {
	"use strict";
	Text.call(this, txt, fontSize, fontFamily, boundingRect, align, textIndent);
	this.image = img;
	this.opacity = opacity;
	return this;
}

PatternedText.prototype = {
	constructor : PatternedText,
	display : function(ctx) {
		"use strict";
		ctx.save();
		ctx.globalAlpha = this.opacity;
		this.drawPatternedText(ctx, this.image);
		ctx.restore();
	}
};
inherits(PatternedText, Text);

function StrokedPatternedText(txt, fontSize, fontFamily, boundingRect, align, img, textIndent, opacity, strokeColor, strokeWidth, strokeOpacity) {
	"use strict";
	PatternedText.call(this, txt, fontSize, fontFamily, boundingRect, align, img, textIndent, opacity);
	this.strokeColor = strokeColor;
	this.strokeWidth = strokeWidth;
	this.strokeOpacity = strokeOpacity;
	return this;
}

StrokedPatternedText.prototype = {
	constructor : StrokedPatternedText,
	display : function(ctx) {
		"use strict";
		ctx.save();
		ctx.textBaseline = "top";
		ctx.globalAlpha = this.opacity;
		this.drawPatternedText(ctx, this.image);
	    ctx.restore();
		ctx.textBaseline = "top";
		ctx.strokeText(this.strokeWidth, this.strokeColor, this.strokeOpacity);
		ctx.restore();
	}
};
inherits(StrokedPatternedText, PatternedText);


function GradientText(txt, fontSize, fontFamily, boundingRect, align, colors, gradType, textIndent) {
	"use strict";
	Text.call(this, txt, fontSize, fontFamily, boundingRect, align, textIndent);
	this.colors = colors;
	var ctr = 0;
	this.colorStops = [];
	for (ctr = 0; ctr < colors.length; ctr++) {
	 	this.colorStops.push(ctr / (this.colors.length - 1));
	}
	this.gradType = gradType;
	return this;
}

GradientText.prototype = {
	constructor : GradientText,
	createGradient : function(ctx) {
		"use strict";
		ctx.save();
		ctx.font = this.fontSize.toString() + "px " + this.fontFamily;
		var lines = this.parseString(this.boundingRect.width, ctx), ctr;
		var bRect = this.boundingRect;
		if (lines.length === 1) {
			var txtWidth = ctx.measureText(this.text).width;
			switch (this.align) {
				case Align.left:
					bRect = new Rectangle(this.boundingRect.left, this.boundingRect.top, txtWidth, this.lineHeight);
					break;
				
				case Align.center:
					bRect = new Rectangle(this.boundingRect.left + (this.boundingRect.width - txtWidth) / 2, this.boundingRect.top, txtWidth, this.lineHeight);
					break;
				
				case Align.right:
					bRect = new Rectangle(this.boundingRect.left + this.boundingRect.width - txtWidth, this.boundingRect.top, txtWidth, this.lineHeight);
					break;
			}
		}
		var gradient;
		switch (this.gradType) {
			case GradType.diagonal: 
				gradient = ctx.createLinearGradient(bRect.left, bRect.top, bRect.right, bRect.bottom);
				break;
				
			case GradType.leftToRight:
				gradient = ctx.createLinearGradient(bRect.left, bRect.top, bRect.right, bRect.top);
				break;
				
			case GradType.topToBottom:
				gradient = ctx.createLinearGradient(bRect.left, bRect.top, bRect.left, bRect.bottom);
				break;
		}
		for (ctr = 0; ctr < this.colors.length; ctr++) {
			gradient.addColorStop(this.colorStops[ctr], this.colors[ctr]);
		}
		ctx.restore();
		return gradient;
	},
	display : function(ctx) {
		"use strict";
		var grad = this.createGradient(ctx);
		this.drawText(ctx, grad);
	}
};
inherits(GradientText, Text);

function IntervalGradientText(txt, fontSize, fontFamily, boundingRect, align, colors, gradType, textIndent, colorStopOffset) {
 	"use strict";
	GradientText.call(this, txt, fontSize, fontFamily, boundingRect, align, colors, gradType, textIndent);
	this.colorStopOffset = colorStopOffset;
	this.screenData = undefined;
	return this;
}

IntervalGradientText.prototype = {
	constructor: IntervalGradientText,
	offsetGradients : function(ctx) {
		"use strict";
		if (!this.screenData) {
			this.screenData = ctx.getImageData(this.boundingRect.left, this.boundingRect.top, this.boundingRect.width, this.boundingRect.height);
		}
		else {
		  ctx.putImageData(this.screenData, this.boundingRect.left, this.boundingRect.top);
		}
			
		var newStops = [];
		for (var stp in this.colorStops) {
			if (stp) {
				var newStop = this.colorStops[stp] + this.colorStopOffset;
				if (newStop > 1) {
					newStop -= 1;
				}
				newStops.push(newStop);
			}
		}
		this.colorStops = newStops;		
	},
	display : function(ctx) {
		"use strict";
		this.offsetGradients(ctx);
		var grad = this.createGradient(ctx);
		this.drawText(ctx, grad);
	}
};
inherits(IntervalGradientText, GradientText);
	
function IntervalGradientStrokedText(txt, fontSize, fontFamily, boundingRect, align, colors, gradType, textIndent, colorStopOffset, strokeWidth, strokeOpacity) {
	"use strict";
	IntervalGradientText.call(this, txt, fontSize, fontFamily, boundingRect, align, colors, gradType, textIndent, colorStopOffset);
	this.strokeWidth = strokeWidth;
	this.strokeOpacity = strokeOpacity;
	return this; 
}

IntervalGradientStrokedText.prototype = {
	constructor: IntervalGradientText,
	display : function(ctx) {
		"use strict";
	    this.offsetGradients(ctx);
		var grad = this.createGradient(ctx);
		this.drawText(ctx, grad);
		this.strokeText(ctx, this.strokeWidth, grad, this.strokeOpacity);
	}
};
inherits(IntervalGradientStrokedText, IntervalGradientText);

function StrokedText(txt, fontSize, fontFamily, boundingRect, align, textIndent, strokeWidth, strokeStyle, strokeOpacity) {
	"use strict";
	Text.call(this, txt, fontSize, fontFamily, boundingRect, align, textIndent);
	this.strokeWidth = strokeWidth;
	this.strokeStyle = strokeStyle;
	this.strokeOpacity = strokeOpacity;
	return this;
}

StrokedText.prototype = {
	constructor: StrokedText,
	display: function(ctx) {
		"use strict";
		this.strokeText(ctx, this.strokeWidth, this.strokeStyle, this.strokeOpacity);
	}
};
inherits(StrokedText, Text);


function DisplayItem(id, type) {
	"use strict";
	this._id = id;
	this._type = type;
	this._boundingRect = undefined;
	return this;
}

DisplayItem.prototype = {
	constructor : DisplayItem,
	get id() {
		"use strict";
		return this._id;
	},
	set id(val) {
		"use strict";
		this._id = val;
	},
	get boundingRect() {
		"use strict";
		return this._boundingRect;
	},
	set boundingRect(val) {
		"use strict";
		this._boundingRect = new Rectangle(val.left, val.top, val.width, val.height);
	},
	get type() {
		"use strict";
		return this._type;
	},
};

function Paragraph(id, format, text, type) {
	"use strict";
	if (!type) {
		DisplayItem.call(this, id, DisplayItemType.paragraph);
	}
	else {
		DisplayItem.call(this, id, type);
	}
	this._id = id;
	this._format = format;
	this._text = text.replace(/\s*\r?\n\s*/g, " ");
	this._boundingRect = undefined;
	return this;
}

Paragraph.prototype = {
	constructor : Paragraph,
	get format() {
		"use strict";
		return this._format;
	},
	set format(val) {
		"use strict";
		this._format = val;
	},
	get text() {
		"use strict";
		return this._text;
	},
	set text(val) {
		"use strict";
		this._text = val;
	},
	getTextSize : function(width, ctx) {
		"use strict";
		ctx.save();
		ctx.font = this.format.fontSize.toString() + "px " + this.format.fontFamily;
		ctx.textBaseline = "top";
		var lines = [];
		var line;
		var origText = this.text;
		var maxWidth = 0;
		if (this.format.textIndent) {
	    	line = origText;
			if (ctx.measureText(line).width > width - this.format.textIndent) {
			    while (ctx.measureText(line).width > width - this.format.textIndent) {
					line = line.substr(0, line.length - 1);
				}
	    		line = (line.trim().match(/(.+)\s+[^\s]*$/)) ? line.trim().match(/(.+)\s+[^\s]*$/)[1] : line;
			}
			lines.push(line.trim());
			maxWidth = ctx.measureText(line.trim()).width + this.format.textIndent;
			origText = origText.substring(line.length).trim();
		}
		while (origText.length > 0) {
			line = origText;
			if (ctx.measureText(line).width > width) {
		   		while (ctx.measureText(line).width > width) {
					line = line.substr(0, line.length - 1);
				}
	    		line = (line.trim().match(/(.+)\s+[^\s]*$/)) ? line.trim().match(/(.+)\s+[^\s]*$/)[1] : line;
			}
			lines.push(line.trim());
			if (maxWidth < ctx.measureText(line.trim()).width) {
				maxWidth = ctx.measureText(line.trim()).width;
			}
			origText = origText.substring(line.length).trim();
		}
		ctx.restore();
		if (line.length === 1) {
			return new Size(maxWidth, 9 * this.format.fontSize / 8);
		} else {
			return new Size(width, lines.length * 9 * this.format.fontSize / 8);
		}
	},
	calcBoundingRect : function(ctx, boundingRect) {
		"use strict";
		var sz = this.getTextSize(boundingRect.width, ctx);
		switch (this.format.align) {
			case Align.left:
				this.boundingRect = new Rectangle(boundingRect.left, boundingRect.top, sz.width, sz.height);
				break;
				
			case Align.center:
				this.boundingRect = new Rectangle(boundingRect.left + (boundingRect.width - sz.width) / 2, boundingRect.top, sz.width, sz.height);
				break;
				
			case Align.right:
				this.boundingRect = new Rectangle(boundingRect.right - sz.width, boundingRect.top, sz.width, sz.height);
				break;
		}
	},
	draw : function(ctx, boundingRect) {
		"use strict";
		this.calcBoundingRect(ctx, boundingRect);
     	var styles = displaySequence.getStyles(this.format.styles);
		var text, patt, cStopAry, ctr, ctr2;
		styles.forEach(function(s) {
			switch (s.type) {
				case "ImageFill":
					text = new PatternedText(this.text, this.format.fontSize, this.format.fontFamily, this.boundingRect, this.format.align, displaySequence.getImage(s.imageId), 
						this.format.textIndent, s.opacity);
					text.display(ctx);
					break;
					
				case "PatternStroke":
					patt = ctx.createPattern(displaySequence.getImage(s.imageId), "repeat");
					text = new StrokedText(this.text, this.format.fontSize, this.format.fontFamily, this.boundingRect, this.format.align, this.format.textIndent,
						s.strokeWidth, patt, s.opacity);
					text.display(ctx);
					break;
					
				case "BackStroke":
					text = new StrokedText(this.text, this.format.fontSize, this.format.fontFamily, this.boundingRect, this.format.align, this.format.textIndent, 
						s.strokeWidth, s.color, s.opacity);
					text.display(ctx);
					break;
						
				case "MovingShadow":
					cStopAry = [];
					for (ctr = 0; ctr < s.colorStops.repeats + 1; ctr++) {
					    for (ctr2 = 0; ctr2 < s.colorStops.colors.length; ctr2++) {
							cStopAry.push(s.colorStops.colors[ctr2]);
						}
					}
					text = new IntervalGradientStrokedText(this.text, this.format.fontSize, this.format.fontFamily, this.boundingRect, this.format.align,
						cStopAry, s.gradientType, this.format.textIndent, s.offsetPerTick, s.strokeWidth, s.opacity);
					displaySequence.addIntervalCall(new IntervalCallback(IntervalGradientStrokedText.prototype.display, s.tickRate, text, displaySequence.ctx));
					break;	
					
				case "GradientTextFill" :
					cStopAry = s.colorStops.colors;
					for (ctr = 0; ctr < s.colorStops.repeats; ctr++) {
						cStopAry.push(cStopAry);
					}
					text = new GradientText(this.text, this.format.fontSize, this.format.fontFamily, this.boundingRect, this.format.align, cStopAry, s.gradientType, 
						this.format.textIndent);
					text.display(ctx);
			}
		}, this);
		return new Rectangle(this.boundingRect.left, this.boundingRect.top, this.boundingRect.width, this.boundingRect.height);
	}
};
inherits(Paragraph, DisplayItem);

function PositionedImage(id, imageId, diType) {
	"use strict";
	if (diType) {
		DisplayItem.call(this, id, diType);
	} else {
		DisplayItem.call(this, id, DisplayItemType.image);
	}
	this._imageId = imageId;
	this.image = undefined;
	return this;
}

PositionedImage.prototype = {
	constructor : PositionedImage,
	setImage : function(img) {
		"use strict";
		this.image = img;
	},
	get imageId() {
		"use strict";
		return this._imageId;
	},
	draw : function(ctx, boundingRect) {
		"use strict";
		if (!this.boundingRect) {
			this.boundingRect = boundingRect;
		}
		this.boundingRect.drawImage(ctx, this.image);
		return new Rectangle(this.boundingRect.left, this.boundingRect.top, this.boundingRect.width, this.boundingRect.height);
	}
};
inherits(PositionedImage, DisplayItem);

function SpriteSheetAnimation(id, imageId, numFrames, frameWidth, tickRate) {
	"use strict";
	PositionedImage.call(this, id, imageId, DisplayItemType.spriteSheetAnimation);
	this.tickRate = tickRate;
	this.numFrames = numFrames;
	this.currFrame = 0;
	this.frameWidth = frameWidth;
	this.imageData = [];
	return this;
}

SpriteSheetAnimation.prototype = {
	constructor : SpriteSheetAnimation,
	draw : function(ctx, boundingRect) {
		"use strict";
		if (!this.boundingRect) {
			this.boundingRect = boundingRect;
		}
		ctx.putImageData(this.imageData[this.currFrame++ % this.numFrames], this.boundingRect.left, this.boundingRect.top);
		displaySequence.addIntervalCall(new IntervalCallback(SpriteSheetAnimation.prototype.draw, this.tickRate, this, displaySequence.ctx, this.boundingRect));
		return boundingRect;
	},
	setImage : function(img) {
		"use strict";
		this.image = img;
		var dummyCtx = document.createElement("canvas").getContext("2d");
		dummyCtx.canvas.width = this.frameWidth;
		dummyCtx.canvas.height = img.height;
		for (var ctr = 0; ctr < this.numFrames; ctr++) {
			dummyCtx.drawImage(this.image, ctr * this.frameWidth, 0, this.frameWidth, this.image.height, 0, 0, this.frameWidth, this.image.height);
			this.imageData.push(dummyCtx.getImageData(0, 0, this.frameWidth, this.image.height));
		}
	}
};
inherits(SpriteSheetAnimation, PositionedImage);

function Footer(id, format, text) {
	"use strict";
	Paragraph.call(this, id, format, text, DisplayItemType.footer);
	return this;
}

Footer.prototype = {
	constructor : Footer,
	calcBoundingRect : function(ctx, boundingRect) {
		"use strict";
		var sz = this.getTextSize(boundingRect.width, ctx);
		switch (this.format.align) {
			case Align.left:
				this.boundingRect = new Rectangle(boundingRect.left, boundingRect.top, sz.width, sz.height);
				break;
				
			case Align.center:
				this.boundingRect = new Rectangle(boundingRect.left + 0.5 * (boundingRect.width - sz.width), boundingRect.top, 
					sz.width, sz.height);
				break;
				
			case Align.right:
				this.boundingRect = new Rectangle(boundingRect.right - sz.width, boundingRect.top, sz.width, sz.height);
				break;
		}
	}
};
inherits(Footer, Paragraph);

function Header(id, format, text) {
	"use strict";
	Paragraph.call(this, id, format, text, DisplayItemType.header);
	return this;
}

Header.prototype = {
	constructor : Footer,
	calcBoundingRect : function(ctx, boundingRect) {
		"use strict";
		var sz = this.getTextSize(boundingRect.width, ctx);
		switch (this.format.align) {
			case Align.left:
				this.boundingRect = new Rectangle(boundingRect.left, 0, sz.width, sz.height);
				break;
				
			case Align.center:
				this.boundingRect = new Rectangle(boundingRect.left + 0.5 * (boundingRect.width - sz.width), 0, sz.width, sz.height);
				break;
				
			case Align.right:
				this.boundingRect = new Rectangle(boundingRect.right - sz.width, 0, sz.width, sz.height);
				break;
		}
	}
};
inherits(Header, Paragraph);


function DisplayImage(id, src) {
	"use strict";
	this.id = id;
	this.img = new Image();
	this.loaded = false;
	this.src = src;
	this.displays = [];	
}

DisplayImage.prototype = {
	constructor: DisplayImage,
	addDisplay: function(displayId) {
		"use strict";
		this.displays.push(displayId);
	},
	beginLoad: function() {
		"use strict";
		var that = this;
		this.img.onload = function() {
			that.loaded = true;
			that.displays.forEach(function(displayId) {displaySequence.setImageLoaded(displayId, this.id); }, that);
		};
		this.img.src = this.src;
	}
};


function Display(id, displayItems, canvasRect, ctx) {
	"use strict";
	this.ready = true;
	this.upForDisplay = false;
	this.images = [];
	this.id = id;
	this.displayItems = displayItems.displayItems;
	this.canvasRect = canvasRect;
	this.ctx = ctx;
	this.clickRectangles = [];
	this.displayImages = [];
}

Display.prototype = {
	constructor: Display,
	loadDisplay : function() {
		"use strict";
		var imgIdAry = [];
		this.displayItems.forEach(function(di) {
			if (di.styles) {
				var styles = displaySequence.getStyles(di.styles.filter(function(elem, ndx, ary) { return ary.indexOf(elem) === ndx; }));
				styles.forEach( function(s) { if (s.imageId) { imgIdAry.push(s.imageId); } } );
			} else if ((di.type === DisplayItemType.image) || (di.type === DisplayItemType.spriteSheetAnimation)) {
				var imgId = displaySequence.displayItems[di.id].imageId;
				imgIdAry.push(imgId);
				this.displayImages.push({ "id" : di.id, "imageId" : imgId });
			}
		}, this);
		imgIdAry = imgIdAry.filter(function(elem, ndx, ary) { return ary.indexOf(elem) === ndx; });
		if (imgIdAry.length > 0) {
			this.ready = false;
		}
		imgIdAry.forEach( function(imgId) {
			var img = displaySequence.images[imgId];
			img.addDisplay(this.id);
			this.images.push(img);
		}, this);
	},
	getDisplayItem : function(id) {
		"use strict";
		return this.displayItems.find(function(elem) { return elem.id === id; });
	},
	imageLoaded : function(imageId) {
		"use strict";
		this.displayImages.filter(function(pImg) { return pImg.imageId === imageId; }).forEach(function(pImg) { 
			var posImgObj = displaySequence.displayItems[pImg.id];
			posImgObj.setImage(displaySequence.getImage(imageId));
		});
		if (this.images.every(function(elem) { return elem.loaded === true; })) {
			this.ready = true;
			if (this.upForDisplay) {
				this.draw();
			}
		}
	},
	draw : function(canvasRect) {
		"use strict";
		if (canvasRect) {
			this.canvasRect = canvasRect;
		}
		var boundingRects = [];
		var rect;
		this.ctx.save();
		this.ctx.fillStyle = "rgba(255, 255, 255, 1)";
		this.ctx.fillRect(0, 0, this.canvasRect.width, this.canvasRect.height);
		this.ctx.restore();
		this.clickRectangles = [];
		var dummyCanvas = document.createElement("canvas");
		dummyCanvas.width = 1200;
		dummyCanvas.height = 2000;
		var dummyCtx = dummyCanvas.getContext("2d");
		this.displayItems.forEach(function(di) {
			var diObj = displaySequence.getDisplayItemObj(di.id);
			var left, top, width, height;
			if (isNaN(parseInt(di.boundingRect.left.toString()))) {
				if (di.boundingRect.left === "left") {
					left = 0;
				} else {
					left = 0;
				}
			} else {
				left = di.boundingRect.left;
			}
			if (isNaN(parseInt(di.boundingRect.top.toString()))) {
				if (di.boundingRect.top === "flow") {
					top = rect.bottom + diObj.format.fontSize / 8;
				} else if (di.boundingRect.top === "floor") {
					var bottom = boundingRects.map(function(elem) { return elem.bottom; }).reduce(function(prev, curr) { return (prev < curr) ? curr : prev; });
					top = bottom + diObj.format.fontSize;
				} else if (di.boundingRect.top === "previousVCenter") {
					if (!isNaN(parseInt(di.boundingRect.height.toString()))) {
						top = rect.top + (rect.height - di.boundingRect.height) / 2;
						height = rect.height - (rect.height - di.boundingRect.height) / 2;
					} else {
						top = rect.top;
					}
				} else {
					top = 0;
				}
			} else {
				top = di.boundingRect.top;
			}
			if (isNaN(parseInt(di.boundingRect.width.toString()))) {
				if (di.boundingRect.width === "width") {
					width = this.canvasRect.width - left;
				} else {
					width = this.canvasRect.width - left;
				}
			} else {
				width = di.boundingRect.width;
			}
			if (!height) {
				if (isNaN(parseInt(di.boundingRect.height.toString()))) {
					if (di.boundingRect.height === "height") {
						height = this.canvasRect.bottom - top;
					} else {
						height = this.canvasRect.bottom - top;
					}
				} else {
					height = di.boundingRect.height;
				}
			}
			rect = diObj.draw(dummyCtx, new Rectangle(left, top, width, height));
			boundingRects.push(rect);
			if (di.onClick) {
				this.clickRectangles.push({ "rect": rect, "target" : di.onClick});
			}
		}, this);
		var drawingBottom = boundingRects.map(function(elem) { return elem.bottom; }).reduce(function(prev, curr) { return (prev < curr) ? curr : prev; });
		var imgData = dummyCtx.getImageData(0, 0, 1200, drawingBottom);
		this.ctx.canvas.height = drawingBottom;
		this.ctx.putImageData(imgData, 0, 0);
		displaySequence.startInterval();
	},
	onClick : function(x, y) {
		"use strict";
		var pt = new Point(x, y);
		this.clickRectangles.forEach(function(obj) {
			if (obj.rect.hitTest(pt)) {
				displaySequence.navigate(obj.target);
			}
		}, this);
	}
};

function DisplaySequence(canvas) {
	"use strict";
	this.canvas = canvas;
	this.ctx = this.canvas.getContext("2d");
	this.formats = [];
	this.displayItems = [];
	this.styles = [];
	this.displays = [];
	this.images = [];
	this.displaySequenceXml = undefined;
	this.intervalCallbacks = [];
	this.intervalValues = [];
	this.displayNdx = 0;
	return this;
}

DisplaySequence.prototype = {
	constructor : DisplaySequence,
	get currentDisplay() {
		"use strict";
		return this.displays[this.displayNdx];
	},
	getDisplayItem : function(id) {
		"use strict";
		var display = this.displays.find(function(e) { if (!e) { return false; } return (e.displayItems.some(function(elem) { return elem.id === id; })); }, this);
		return display.getDisplayItem(id);
	},
	getDisplayItemObj : function(id) {
		"use strict";
		return this.displayItems[id];
	},
	loadImages : function(nodes) {
		"use strict";
		var imgNode = nodes.iterateNext();	
		while (imgNode) {
			var id = parseInt(imgNode.getAttribute("id"), 10);
			var imgObj = new DisplayImage(id, imgNode.textContent);
			this.images[id] = imgObj;
			imgNode = nodes.iterateNext();
		}
	},
	loadFormats : function(nodes) {
		"use strict";
		var formatNode = nodes.iterateNext();
		while (formatNode) {
			var format = JSON.parse(formatNode.textContent);
			this.formats[parseInt(formatNode.getAttribute("id"), 10)] = format;
     		formatNode = nodes.iterateNext();
		}
	},
	loadStyles : function(nodes) {
		"use strict";
		var styleNode = nodes.iterateNext();
		while (styleNode) {
			var style = JSON.parse(styleNode.textContent);
			this.styles[parseInt(styleNode.getAttribute("id"), 10)] = style;
			styleNode = nodes.iterateNext();
		}
	},
	loadParagraphs : function(node) {
		"use strict";
		var paraNode = node.iterateNext();
		while (paraNode) {
			var id = parseInt(paraNode.getAttribute("id"));
			var di = this.getDisplayItem(id);
			this.displayItems[id] = new Paragraph(id, this.getFormat(di.format), paraNode.textContent);
			paraNode = node.iterateNext();
		}
	},
	loadFooters : function(node) {
		"use strict";
		var footerNode = node.iterateNext();
		while (footerNode) {
			var id = parseInt(footerNode.getAttribute("id"));
			var di = this.getDisplayItem(id);
			this.displayItems[id] = new Footer(id, this.getFormat(di.format), footerNode.textContent);
			footerNode = node.iterateNext();
		}
	},
	loadHeaders : function(node) {
		"use strict";
		var headerNode= node.iterateNext();
		while (headerNode) {
			var id = parseInt(headerNode.getAttribute("id"));
			var di = this.getDisplayItem(id);
			this.displayItems[id] = new Header(id, this.getFormat(di.format), headerNode.textContent);
			headerNode = node.iterateNext();
		}
	},
	getFormat : function(id) {
		"use strict";
		return this.formats[id];
	},
	getImage : function(id) {
		"use strict";
		return this.images.find(function(elem) { if (!elem) { return false; } else { return elem.id === id; }}).img;
	},
	getPositionedImage : function(id) {
		"use strict";
		return this.displayItems[id];
	},
	getStyles : function(styleIds) {
		"use strict";		
		var styles = [];
		styleIds.forEach(function(id) { styles.push(this.styles[id]); }, this);
		return styles;
	},
	getDisplayItems : function(displayItemIds) {
		"use strict";
		var DIs = [];
		displayItemIds.forEach(function(id) { DIs.push(this.displayItems[id]); }, this);
		return DIs;
	},
	startImageLoad : function() {
		"use strict";
		this.images.forEach(function(elem) { elem.beginLoad(); });
	},
	addIntervalCall: function(callback) {
		"use strict";
		this.intervalCallbacks.push(callback);
	},
	startInterval : function() {
		"use strict";
		this.intervalCallbacks.forEach(function(cb) { this.intervalValues.push(window.setInterval(function() { cb.invoke(); }, cb.rate)); }, this );
	},
	clearCallbackIntervals: function() {
		"use strict";
		this.intervalValues.forEach(function(i) { window.clearInterval(i); });
		this.intervalValues = [];
		this.intervalCallbacks = [];
	},
	setImageLoaded : function(displayNum, imageId) {
		"use strict";
		this.displays[displayNum].imageLoaded(imageId);
	},
	loadPositionedImages : function(nodes) {
		"use strict";
		var node = nodes.iterateNext();
		while (node) {
			var id = parseInt(node.getAttribute("id"));
			var imageId = parseInt(node.getAttribute("imageId"));
			this.displayItems[id] = new PositionedImage(id, imageId);
			node = nodes.iterateNext();
		}
	},
	loadSpriteSheetAnimations : function(nodes) {
		"use strict";
		var node = nodes.iterateNext();
		while (node) {
			var id = parseInt(node.getAttribute("id"));
			var imageId = parseInt(node.getAttribute("imageId"));
			var numFrames = parseInt(node.getAttribute("numFrames"));
			var frameWidth = parseInt(node.getAttribute("frameWidth"));
			var tickRate = parseInt(node.getAttribute("tickRate"));
			this.displayItems[id] = new SpriteSheetAnimation(id, imageId, numFrames, frameWidth, tickRate);
			node = nodes.iterateNext();
		}
	},
	loadDisplays : function(node) {
		"use strict";
		var displayNode = node.iterateNext();
		var canvasRect = new Rectangle(0, 0, this.canvas.width, this.canvas.height);
		while (displayNode) {
			var id = parseInt(displayNode.getAttribute("id"));
			this.displays[id] = new Display(id, JSON.parse(displayNode.textContent), canvasRect, this.ctx);
			this.displays[id].loadDisplay();
			displayNode = node.iterateNext();
		}
	},
	loadFromXml : function(XML) {
		"use strict";
		this.displaySequenceXML = XML;
		this.loadImages(XML.evaluate("//Image", XML.documentElement, null, 0, null));
		this.loadFormats(XML.evaluate("//Format", XML.documentElement, null, 0, null));
		this.loadStyles(XML.evaluate("//Style", XML.documentElement, null, 0, null));
		this.loadPositionedImages(XML.evaluate("//PositionedImage", XML.documentElement, null, 0, null));
		this.loadSpriteSheetAnimations(XML.evaluate("//SpriteSheetAnimation", XML.documentElement, null, 0, null));
		this.loadDisplays(XML.evaluate("//Display", XML.documentElement, null, 0, null));
		this.loadParagraphs(XML.evaluate("//Paragraph", XML.documentElement, null, 0, null));
		this.loadFooters(XML.evaluate("//Footer", XML.documentElement, null, 0, null));
		this.loadHeaders(XML.evaluate("//Header", XML.documentElement, null, 0, null));
	},
	start : function() {
		"use strict";
		this.displayNdx = 1;
		if (this.displays[1].ready) {
			this.displays[1].draw();
		} else {
			this.displays[1].upForDisplay = true;
		}
		this.startImageLoad();
	},
	navigate : function(target) {
		"use strict";
		this.clearCallbackIntervals();
		this.ctx.save();
		this.ctx.fillStyle = "#FFFFFF";
		this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
		this.ctx.restore();
		window.scrollTo(0, 0);
		this.displayNdx = target;
		this.ctx.canvas.height = 1200;
		if (this.displays[target].ready) {
			this.displays[target].draw(new Rectangle(0, 0, this.canvas.width, this.canvas.height));
		} else {
			this.displays[target].upForDisplay = true;
		}
	}
};

function IntervalCallback(funct, rate, that, params) {
	"use strict";
	this.funct = funct;
	this.that = that;
	this.params = params;
	this.rate = rate;
}

IntervalCallback.prototype = {
	constructor : IntervalCallback,
	invoke : function() {
		"use strict";
		this.funct.call(this.that, this.params);
	}
};
		
function RollingBounceText(text, fontFamily, fontSize, fill, stroke, strokeWidth, canvas, ctx) {
	"use strict";
	this.text = text;
	this.ctx = ctx;
	this.canvasWidth = canvas.width;
	this.canvasHeight = canvas.height;
	this.fontSize = fontSize;
	this.font = fontSize.toString() + "px " + fontFamily;
	this.fill = fill;
	this.stroke = stroke;
	this.strokeWidth = strokeWidth;
	this.background = ctx.getImageData(0, 0, canvas.width, canvas.height);
	return this;
}

RollingBounceText.prototype = {
	constructor : RollingBounceText,
	measure : function() {
		"use strict";
		this.letterRects = [];
		this.ctx.save();
		this.ctx.font = this.font;
		this.textWidth = this.ctx.measureText(this.text).width;
		var ctr;
		for (ctr = 0; ctr < this.textWidth; ctr += this.textWidth / 1000) {
			this.letterRects.push(new Rectangle(ctr, 0, ctr + (this.textWidth / 1000), this.canvasHeight));
		}
		this.ctx.restore();
		this.textOrigin = new Point((this.canvasWidth - this.textWidth) / 2, this.canvasHeight);
		for (ctr = 0; ctr < this.letterRects.length; ctr++) {
			this.letterRects[ctr].offset(new Point(this.textOrigin.x, 0));
		}
	},
	drawAndCapture : function() {
		"use strict";
		this.ctx.save();
		this.ctx.font = this.font;
		this.ctx.fillStyle = this.fill;
		this.ctx.strokeStyle = this.stroke;
		this.ctx.lineWidth = this.strokeWidth;
		this.ctx.textBaseline = "bottom";
		this.ctx.fillText(this.text, this.textOrigin.x, this.textOrigin.y);
		this.ctx.strokeText(this.text, this.textOrigin.x, this.textOrigin.y);
		this.textImageData = [];
		for (var ctr = 0; ctr < this.letterRects.length; ctr++) {
			this.textImageData.push(this.ctx.getImageData(this.letterRects[ctr].left, this.letterRects[ctr].top, this.letterRects[ctr].width, this.letterRects[ctr].height));
		}
		this.ctx.restore();
	},
	bounce : function(bounceHeight, bounceDuration, bounceRate, nextLetterDelay) {
		"use strict";
		this.measure();
		this.drawAndCapture();
		var ctr;
		var that = this;
		var startTime = new Date().getTime();
		var intervalID = window.setInterval(function() {
			that.ctx.putImageData(that.background, 0, 0);
			var now = new Date().getTime();
			for (ctr = 0; ctr < that.letterRects.length; ctr++) {
				if ((7 * (now - startTime) < bounceDuration + ctr * nextLetterDelay) && (7 * (now - startTime) > ctr * nextLetterDelay)) {
					var timeOffset = 7 * (now - startTime) - (ctr * nextLetterDelay);
					if (timeOffset > bounceDuration) {
						timeOffset = bounceDuration;
					}
					var y = - bounceHeight * Math.sin((timeOffset / bounceDuration) * Math.PI);
					that.ctx.putImageData(that.textImageData[ctr], that.letterRects[ctr].left, y);
				}
				else {
					that.ctx.putImageData(that.textImageData[ctr], that.letterRects[ctr].left, that.letterRects[ctr].top);
				}
			}
			if (7 * (now - startTime) > (bounceDuration + (that.letterRects.length * nextLetterDelay))) {
				window.clearInterval(intervalID);
			}
		}, bounceRate);
	}
};		

function RollingBounceText(text, fontFamily, fontSize, fill, stroke, strokeWidth, canvas, ctx) {
	"use strict";
	this.text = text;
	this.ctx = ctx;
	this.canvasWidth = canvas.width;
	this.canvasHeight = canvas.height;
	this.fontSize = fontSize;
	this.font = fontSize.toString() + "px " + fontFamily;
	this.fill = fill;
	this.stroke = stroke;
	this.strokeWidth = strokeWidth;
	this.backgroundImg = new Image();
	this.backgroundImg.src = canvas.toDataURL();
	return this;
}

RollingBounceText.prototype = {
	constructor : RollingBounceText,
	measure : function() {
		"use strict";
		this.letterRects = [];
		this.ctx.save();
		this.ctx.font = this.font;
		this.textWidth = this.ctx.measureText(this.text).width;
		var ctr;
		for (ctr = 0; ctr < this.textWidth; ctr += 2) {
			this.letterRects.push(new Rectangle(ctr, 0, ctr + 2, this.canvasHeight));
		}
		this.ctx.restore();
		this.textOrigin = new Point((this.canvasWidth - this.textWidth) / 2, this.canvasHeight);
		for (ctr = 0; ctr < this.letterRects.length; ctr++) {
			this.letterRects[ctr].offset(new Point(this.textOrigin.x, 0));
		}
	},
	drawAndCapture : function() {
		"use strict";
		this.ctx.save();
		this.ctx.font = this.font;
		this.ctx.fillStyle = this.fill;
		this.ctx.strokeStyle = this.stroke;
		this.ctx.lineWidth = this.strokeWidth;
		this.ctx.textBaseline = "bottom";
		this.ctx.fillText(this.text, this.textOrigin.x, this.textOrigin.y);
		this.ctx.strokeText(this.text, this.textOrigin.x, this.textOrigin.y);
		this.textImageData = [];
		for (var ctr = 0; ctr < this.letterRects.length; ctr++) {
			this.textImageData.push(this.ctx.getImageData(this.letterRects[ctr].left, this.letterRects[ctr].top, this.letterRects[ctr].width, this.letterRects[ctr].height));
		}
		this.ctx.restore();
	},
	displayFrame : function(time) {
		"use strict";
                if (this.startTime === undefined)
                    this.startTime = time;
		this.ctx.drawImage(this.backgroundImg, 0, 0);
		var totalBounceTime = this.bounceDuration * this.letterRects.length;
		for (var ctr = 0; ctr < this.letterRects.length; ctr++) {
			var yOffset = 0;
			if ((ctr * this.bounceDuration >= time - this.startTime) && ((ctr + this.period) * this.bounceDuration <= time - this.startTime)) {
				var bounceStartTime = Math.floor((time - this.startTime) - (ctr * this.bounceDuration));
				yOffset = Math.floor(this.bounceHeight * Math.cos((time - this.startTime - bounceStartTime) * this.period * Math.PI));
			}
			this.ctx.putImageData(this.textImageData[ctr], this.letterRects[ctr].left, this.letterRects[ctr].top - yOffset);
		}
		if (time - this.startTime < totalBounceTime) {
			var that = this;
			window.requestAnimationFrame( function(time) { that.displayFrame(time);});
		}
	},
	bounce : function(bounceHeight, bounceDuration, period) {
		"use strict";
		this.measure();
		this.drawAndCapture();
		var that = this;
		this.startTime = undefined;
		this.bounceHeight = bounceHeight;
		this.bounceDuration = bounceDuration;
		this.period = period;
		window.requestAnimationFrame(function(time) { that.displayFrame(time); });
	}
};		

function Transform(boundingRect, canvasRect, xRot, yRot, zRot) {
	"use strict";
	this.eyeDistanceFromScreen = 100;
	this.canvasRect = canvasRect;
	this.xRot = xRot;
	this.yRot = yRot;
	this.zRot = zRot;
	this.xMatrix = undefined;
	this.yMatrix = undefined;
	this.zMatrix = undefined;
	this.boundingRect = boundingRect;
	this.transformedTopLeft = [ -0.5 * this.boundingRect.width, -0.5 * this.boundingRect.height, 0 ];
	this.transformedTopRight = [ 0.5 * this.boundingRect.width, -0.5 * this.boundingRect.height, 0];
	this.transformedBottomLeft = [ -0.5 * this.boundingRect.width, 0.5 * this.boundingRect.height, 0];
	this.transformedBottomRight = [ 0.5 * this.boundingRect.width, 0.5 * this.boundingRect.height, 0];
	return this;
}

Transform.prototype = {
	constructor: Transform,
	crossProduct : function(m1, m2) {
		"use strict";
		if (m1[0].length !== m2.length) {
			return undefined;
		}
		var resultMatrix = [];
		for (var ctr1 = 0; ctr1 < m1.length; ctr1++) {
			var resultRow = [];
			for (var ctr2 = 0; ctr2 < m2[ctr1].length; ctr2++) {
				var cellResult = 0;
				for (var ctr3 = 0; ctr3 < m2.length; ctr3++) {
					cellResult += m1[ctr1][ctr3] * m2[ctr3][ctr2];
				}
				resultRow.push(cellResult);
			}
			resultMatrix.push(resultRow);
		}
		return resultMatrix;
	},
	initTransformMatricies : function() {
		"use strict";
		if (this.xRot !== 0) {
			this.xMatrix = [[Math.cos(this.xRot), Math.sin(this.xRot), 0],
							[-Math.sin(this.xRot), Math.cos(this.xRot), 0],
							[0, 0, 1]];
		}
		if (this.yRot !== 0) {
			this.yMatrix = [[Math.cos(this.yRot), 0, Math.sin(this.yRot)],
							[0, 1, 0],
							[-Math.sin(this.yRot), 0, Math.cos(this.yRot)]];
		}
		if (this.zRot !== 0) {
			this.zMatrix = [[1, 0, 0],
							[0, Math.cos(this.zRot), Math.sin(this.zRot)],
							[0, -Math.sin(this.zRot), Math.cos(this.zRot)]];
		}
		if (!this.xMatrix) {
			this.rotationMatrix = this.crossProduct(this.yMatrix, this.zMatrix);
		} else if (!this.yMatrix) {
			this.rotationMatrix = this.crossProduct(this.xMatrix, this.zMatrix);
		} else {
			this.rotationMatrix = this.crossProduct(this.xMatrix, this.yMatrix);
		}
	},
	transformBoundingRectangle : function() {
		"use strict";
		this.transformedTopLeft = this.crossProduct(this.transformedTopLeft, this.rotationMatrix)[0];
		this.transformedTopRight = this.crossProduct(this.transformedTopRight, this.rotationMatrix)[0];
		this.transformedBottomLeft = this.crossProduct(this.transformedBottomLeft, this.rotationMatrix)[0];
		this.transformedBottomRight = this.crossProduct(this.transformedBottomRight, this.rotationMatrix)[0];
	},
	getEyeCoordinates : function() {
		"use strict";
		var translationMatrix = [[ 1, 0, 0, -0.5 * this.canvasRect.width ],
								[ 0, 1, 0, -0.5 * this.canvasRect.height],
								[ 0, 0, 1, -0.5 * this.eyeDistanceFromScreen],
								[ 0, 0, 0, 1 ]];
							
	    var pt = this.transformedTopLeft;
		pt.push(1);
		var x1y1 = this.crossProduct(pt, translationMatrix);
		x1y1.slice(0, 2);
		pt = this.transformedBottomRight;
		pt.push(1);
		var x2y2 = this.crossProduct(pt, translationMatrix);
		x2y2.slice(0, 2);
		
		return new Rectangle(x1y1[0], x1y1[1], x2y2[0], x2y2[1]);
	}
};

