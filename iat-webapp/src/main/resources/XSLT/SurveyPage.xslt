<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:mine="http://www.iatsoftware.net" version="2.0" exclude-result-prefixes="xs mine">
	<xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8" indent="yes" />
	<xsl:variable name="root" select="/" />
	<xsl:variable name="pageWidth" select="'100%'" />
	<xsl:variable name="surveyWidth" select="'80%'" />
	<xsl:function name="mine:textWidth">
		<xsl:param name="numChars" />
		<xsl:param name="format" />
		<xsl:value-of select="xs:integer(ceiling(xs:integer($numChars) * xs:integer($format/FontSize) * 9 div 8))" />
	</xsl:function>
	
	
	<xsl:variable name="responseTypeList" select="tokenize('MultiChoice,TrueFalse,Likert,MultiSelect,BoundedText,BoundedNumber,FixedDigit,RegEx,Date', ',')" />
	<xsl:function name="mine:getResponse">
		<xsl:param name="item" />
		<xsl:value-of select="$item/child::*[name() = $responseTypeList]/name()" />
	</xsl:function>
	
	<xsl:template match="Survey">
		<xsl:element name="html">
			<head>
				<meta http-equiv="X-UA-Compatible" content="IE=edge" />
				<title>
					<xsl:choose>
						<xsl:when test="string-length(./Caption/Text) gt 0">
							<xsl:value-of select="./Caption/Text" />
						</xsl:when>
						<xsl:when test="@IAT">
							<xsl:value-of select="@IAT" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="'GENERATED'" />
						</xsl:otherwise>
					</xsl:choose>
				</title>
				<style type="text/css">
					<xsl:text>
@import url("/IAT/css/fonts.css");

body {
          font: 100% Verdana, Arial, Helvetica, sans-serif;
          background: #FFFFFF;
          margin: 0px;
          padding: 0px 0px 20px 0px;
          color: #000000;
          }
form {
	margin: auto 5vw;
}

input[type='radio'], input[type='checkbox'] {
	height: 1rem;
	width: 1rem;
	margin-left: 1vw;
}

td:nth-of-type(2n - 1) {
	width: 1rem;
}


          @media (pointer: coarse;) {
			div#mainContent {
          	max-width: 100%;
						margin: 10px auto;
          }
          }
          @media (pointer: fine)  {
          div#mainContent {
          	max-width: 1200px;
						margin: 50px auto;
          	}
          }

          .oneColFixCtrHdr #container {
          text-align: left;
          }

          body div#container div#mainContent ul#QuestionList li div.PictureDiv {
          text-align: center;
          }
          body div#container div#mainContent ul#QuestionList li div.PictureDiv img {
			max-width: 1000px;
          width: 80%;
		  min-width: 400px;
		  max-height: 500px;
          padding: 10px 15px 10px 30px;

          }
          
</xsl:text>
					<xsl:if test="count(./Caption) eq 1">	
						<xsl:text>
              body div.svgOuter {
              </xsl:text>
						<xsl:value-of select="concat('width: ', $pageWidth, ';&#x0A;')" />
						<xsl:text>
              overflow: clip;
              box-sizing: border-box;
            }
          </xsl:text>
					</xsl:if>
					<xsl:text>

                        .oneColFixCtrHdr #mainContent ul {
                          padding: 0px 5px;
                        list-style: none;
                        }

                        .oneColFixCtrHdr #mainContent #ErrorsExistMsgDiv h3
                        {
                        font-family: georgia, sans-serif, serif;
                        font-size: 18px;
                        color: #006644;
                        font-weight: bold;
                        }

                        .oneColFixCtrHdr #mainContent ul li.ItemOdd {
                        padding: 10px 0px;
                        width: 100%;
                        }

                        .oneColFixCtrHdr #mainContent ul li.ItemEven {
                        padding: 10px 0px;
                        width: 100%;
                        }


                        .oneColFixCtrHdr #mainContent ul p.Error, li.Error {
                        font-family: sans-serif;
					</xsl:text>
					<xsl:variable name="sifs">
						<xsl:copy-of select="//SurveyItem/Format" />
					</xsl:variable>	
					<xsl:variable name="errorFontSize">
						<xsl:value-of select="distinct-values(//Survey/SurveyItem[every $fs in (preceding-sibling::SurveyItem/Format, following-sibling::SurveyItem/Format) satisfies xs:integer($fs/FontSize) ge xs:integer(Format/FontSize)]/Format/FontSize)" /> 
					</xsl:variable>
					<xsl:copy-of select="concat('font-size: ', xs:integer($errorFontSize) * 3 div 2, 'px;&#x0A;')" />
					<xsl:text>
                        color: #ff0000;
                        font-style: italic;
                        font-weight: normal;
                        margin: 5px 0px 0px 0px;
                        }


                        .RadioInputCell {
                        vertical-align: middle;
                        padding: 0px;
                        }

                        p.RadioLabelParagraph {
                           margin: 0px;
                          padding: 0px;
                        }
                    </xsl:text>
					
					<xsl:for-each select="SurveyItem">
						<xsl:variable name="itemNum" select="position() + count(preceding-sibling::SurveyImage)" />
						<xsl:variable name="questionNum" select="@QuestionNum" />
						<xsl:variable name="question" select="." />
						<xsl:variable name="response">
							<xsl:copy-of select="$question/child::*[name() eq mine:getResponse($question)]" />
						</xsl:variable>
						<xsl:value-of select="concat('h3#itemText', $itemNum, ' div div {&#x0A;')" />
						<xsl:value-of select="'&#x09;margin: 2vh 5vw 2vh 5vw;&#x0A;'" />
						<xsl:value-of select="'}&#x0A;'" />
						<xsl:value-of select="concat('h3#itemText', $itemNum, ' {&#x0A;')" />
						<xsl:if test="mine:getResponse(.) ne 'Instruction'">
							<xsl:value-of select="'&#x09;margin: 2px 5px 3px 20px;&#x0A;'" />
						</xsl:if>
						<xsl:if test="mine:getResponse(.) eq 'Instruction'">
							<xsl:value-of select="'margin: 2px 5px 8px 20px;&#x0A;'" />
						</xsl:if>
						<xsl:call-template name="writeFormatCSS">
							<xsl:with-param name="format" select="Format" />
						</xsl:call-template>
						<xsl:value-of select="'}&#x0A;'" />
						<xsl:variable name="selectionBasedResponses" select="for $i in ('MultiChoice', 'TrueFalse', 'Likert', 'MultiSelect') return $i" />
						<xsl:if test="mine:getResponse($question) = $selectionBasedResponses">
							<xsl:value-of select="concat('p.response', $questionNum, '{&#x0A;')" />
							<xsl:call-template name="writeFormatCSS">
								<xsl:with-param name="format" select="$response//Format" />
							</xsl:call-template>
							<xsl:value-of select="'padding: 0px;&#x0A;'" />
							<xsl:value-of select="'margin: 0px;&#x0A;'" />
							<xsl:value-of select="'vertical-align: top;&#x0A;'" />
							<xsl:value-of select="'text-align: left;&#x0A;'" />
							<xsl:value-of select="'}&#x0A;'" />
						</xsl:if>
						<xsl:variable name="nonSelectionBasedResponses" select="for $i in ('BoundedText', 'BoundedNumber', 'FixedDigit', 'RegEx', 'Date') return $i" />
						<xsl:if test="mine:getResponse($question) = $nonSelectionBasedResponses">
							<xsl:if test="mine:getResponse($question) ne 'BoundedText'">
								<xsl:value-of select="concat('div#response', $questionNum,' input {&#x0A;')" />
							</xsl:if>
							<xsl:if test="mine:getResponse($question) eq 'BoundedText'">
								<xsl:value-of select="concat('div#response', $questionNum,' textarea {&#x0A;')" />
							</xsl:if>
							<xsl:call-template name="writeFormatCSS">
								<xsl:with-param name="format" select="$response//Format" />
							</xsl:call-template>
							<xsl:value-of select="'padding: 2px 1px;&#x0A;'" />
							<xsl:value-of select="'margin-left: 1vw;&#x0A;'" />
							<xsl:value-of select="'text-align: left;&#x0A;'" />
							<xsl:value-of select="'}&#x0A;'" />
						</xsl:if>
					</xsl:for-each>
					
					
					<xsl:text>



          .ErrorMessageDiv h3 {
                        font-family: "Times New Roman", Times, serif;
                        font-size: 16px;
                        color: #dd0000;
                        font-style: italic;
                        font-weight: normal;
                        margin: 2px 5px 3px 10px;
                        }

                        .SurveyItemDiv .RadioButtonTable {
                        margin: 2px 5px 3px 10px;
                        padding: 0px;
                        }

                        .SurveyItemDiv .CheckBoxTable {
                        margin-left: 20px;
                        }




                        .Clear
                        {
                        clear: both;
                        min-height: 1px;
                        height: 1px;
                        }

                        .DateInputLabel
                        {
                        text-indent: -15px;
                        margin: 5px 5px 5px 215px;
                        padding: 5px 5px 5px 5px;
                        }

                        #SubmitButtonDiv
                        {
                        width: 100%;
                        text-align: center;
                        }

                        #SubmitButton {
                        width: 100px;
                        margin-top: 20px;
                        margin-left: auto;
                        margin-right: auto;
                        margin-bottom: 20px;
                        }
                    </xsl:text>
				</style>
				<xsl:element name="script">
					<xsl:attribute name="src" select="'/IAT/scripts/MiscUtils.js'" />
					<xsl:text> </xsl:text>
				</xsl:element>
				<xsl:element name="script">
					<xsl:attribute name="src" select="'/IAT/scripts/SubFunct.js'" />
					<xsl:text> </xsl:text>
				</xsl:element>
				<xsl:element name="script">
					<xsl:attribute name="src" select="'/IAT/scripts/SurveyValidate.js'" />
					<xsl:text> </xsl:text>
				</xsl:element>
			</head>
			<body class="oneColFixCtrHdr" id="body" onload="OnLoad()">
				<div id="container">
					<xsl:if test="count(//Caption) eq 1">
						<xsl:call-template name="GenerateCaption">
							<xsl:with-param name="caption" select="./Caption" />
						</xsl:call-template>
					</xsl:if>
					<div id="mainContent">
						<xsl:element name="form">
							<xsl:attribute name="method" select="'POST'" />
							<xsl:attribute name="data-timeout" select="//Survey/@TimeoutMillis" />
							<xsl:element name="ul">
								<xsl:attribute name="id" select="'QuestionList'" />
								<xsl:variable name="pos" select="position()" />
								<xsl:variable name="Items">
									<xsl:for-each select="//Survey/child::*[(name() eq 'SurveyItem') or (name() eq 'SurveyImage')]">
										<xsl:if test="name() eq 'SurveyImage'">
											<xsl:element name="SurveyImage">
												<xsl:copy-of select="child::*" />
											</xsl:element>
										</xsl:if>
										<xsl:if test="name() eq 'SurveyItem'">
											<xsl:element name="SurveyItem">
												<xsl:variable name="ndx" select="position()" as="xs:integer" />
												<xsl:attribute name="QuestionNum" select="@QuestionNum" />
												<xsl:attribute name="optional" select="lower-case(string(@Optional))" />
												<xsl:attribute name="ItemNum" select="@ItemNum" />
												<xsl:attribute name="Image" select="'false'" />
												<xsl:copy-of select="child::*" />
											</xsl:element>
										</xsl:if>
									</xsl:for-each>
								</xsl:variable>
								<xsl:apply-templates select="$Items/child::node()" />
							</xsl:element>
							<xsl:element name="h3">
								<xsl:attribute name="id" select="'ErrorsExistMsgDiv'" />
								<xsl:value-of select="' '" />
							</xsl:element>
							<xsl:element name="div">
								<xsl:attribute name="id" select="'SubmitButtonDiv'" />
								<xsl:element name="input">
									<xsl:attribute name="id" select="'SubmitButton'" />
									<xsl:attribute name="type" select="'button'" />
									<xsl:attribute name="value" select="'Submit'" />
									<xsl:attribute name="onclick" select="'OnSubmit()'" />
								</xsl:element>
							</xsl:element>
						</xsl:element>
					</div>
				</div>
			</body>
		</xsl:element>
	</xsl:template>
	
	<xsl:function name="mine:pow">
		<xsl:param name="value" />
		<xsl:param name="power" />
		<xsl:if test="xs:integer($power) eq 0">
			<xsl:value-of select="1" />
		</xsl:if>
		<xsl:if test="xs:integer($power) gt 0">
			<xsl:value-of select="$value * mine:pow($value, xs:integer($power) - 1)" />
		</xsl:if>
		<xsl:if test="xs:integer($power) lt 0">
			<xsl:value-of select="1" />
		</xsl:if>
	</xsl:function>
	
	<xsl:function name="mine:hexToDecimal">
		<xsl:param name="hexValue" />
		<xsl:variable name="hexDigits">
			<xsl:variable name="digs">
				<xsl:value-of select="concat('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')" />
			</xsl:variable>
			<xsl:for-each select="for $n in 1 to string-length($digs) return substring($digs, $n, 1)">
				<xsl:variable name="letter" select="." />
				<xsl:element name="Digit">
					<xsl:value-of select="$letter" />
				</xsl:element>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="decimalDigitValues">
			<xsl:for-each select="for $i in 1 to string-length($hexValue) return $i">
				<xsl:variable name="n" select="xs:integer(.)" />
				<xsl:variable name="decimalDigitValue" select="count($hexDigits/Digit[every $dig in preceding-sibling::Digit satisfies $dig ne substring($hexValue, $n, 1)]) - 1" />
				<xsl:element name="digitValue">
					<xsl:value-of select="$decimalDigitValue * mine:pow(16, string-length($hexValue) - $n)" />
				</xsl:element>
			</xsl:for-each>
		</xsl:variable>
		<xsl:copy-of select="sum($decimalDigitValues/digitValue)" />
	</xsl:function>
	
	
	<xsl:template name="GenerateCaption">
		<xsl:param name="caption" />
		<xsl:element name="div">
			<xsl:attribute name="class" select="'svgOuter'" />
			<xsl:attribute name="style" select="concat('background: #', $caption/BackColorR, $caption/BackColorG, $caption/BackColorB, ';')" />
			<xsl:variable name="svgViewBoxHeight" select="xs:integer($caption/LineHeight) * 3 div 2" />
			<xsl:variable name="svgHeight" select="xs:integer($caption/FontSize) * 9 div 4" />
			<xsl:variable name="fontSize" select="xs:integer($caption/FontSize) * 3 div 2" />
			<xsl:variable name="textY" select="$svgHeight * 5 div 7" />
			<xsl:element name="svg">
				<xsl:attribute name="viewBox" select="concat('0 0 2000', ' ', $svgHeight)" />
				<xsl:attribute name="width" select="$pageWidth" />
				<xsl:attribute name="height" select="$svgHeight" />
				<g overflow="hidden">
					<defs>
						<mask id="captionMask">
							<xsl:element name="text">
								<xsl:attribute name="fill" select="'white'" />
								<xsl:attribute name="text-anchor" select="'middle'" />
								<xsl:attribute name="x" select="1000" />
								<xsl:attribute name="y" select="$textY" />
								<xsl:attribute name="style" select="concat('font-size: ', $fontSize, 'px; font-family:', $caption/FontName, '; font-weight: 700;')" />
								<xsl:value-of select="$caption/Text" />
							</xsl:element>
							<filter id="captionInnerShadow" x="-20%" y="-20%" width="140%" height="140%">
								<feGaussianBlur in="SourceGraphic" stdDeviation="2" result="blur" />
								<feOffset in="blur" dx="1.5" dy="1.5" />
							</filter>
						</mask>
						<xsl:variable name="fontColorStop" select="concat('#', $caption/FontColorR, $caption/FontColorG, $caption/FontColorB)" />
						<xsl:variable name="offFontColorStop">
							<xsl:variable name="R" select="ceiling(mine:hexToDecimal($caption/FontColorR) * 1.3)" />
							<xsl:variable name="G" select="ceiling(mine:hexToDecimal($caption/FontColorG) * 1.3)" />
							<xsl:variable name="B" select="ceiling(mine:hexToDecimal($caption/FontColorB) * 1.3)" />
							<xsl:value-of select="concat('rgba(', $R, ', ', $G, ', ', $B, ', 1)')" />
						</xsl:variable>
						<linearGradient id="captionGradient" x1=".25" x2=".55" y1="0" y2="1">
							<xsl:element name="stop">
								<xsl:attribute name="offset" select="'0%'" />
								<xsl:attribute name="stop-color" select="$offFontColorStop" />
							</xsl:element>
							<xsl:element name="stop">
								<xsl:attribute name="offset" select="'30%'" />
								<xsl:attribute name="stop-color" select="$fontColorStop" />
							</xsl:element>
							<xsl:element name="stop">
								<xsl:attribute name="offset" select="'65%'" />
								<xsl:attribute name="stop-color" select="$offFontColorStop" />
							</xsl:element>
							<xsl:element name="stop">
								<xsl:attribute name="offset" select="'75%'" />
								<xsl:attribute name="stop-color" select="$fontColorStop" />
							</xsl:element>
							<xsl:element name="stop">
								<xsl:attribute name="offset" select="'100%'" />
								<xsl:attribute name="stop-color" select="$fontColorStop" />
							</xsl:element>
						</linearGradient>
					</defs>
					<g mask="url(#captionMask)">
						<xsl:element name="rect">
							<xsl:attribute name="x" select="0" />
							<xsl:attribute name="y" select="0" />
							<xsl:attribute name="width" select="'100%'" />
							<xsl:attribute name="height" select="'100%'" />
							<xsl:attribute name="fill" select="concat('rgba(', mine:pow(mine:hexToDecimal($caption/BackColorR), 2) div 256, ', ', mine:pow(mine:hexToDecimal($caption/BackColorG), 2) div 256, ', ', mine:pow(mine:hexToDecimal($caption/BackColorB), 2) div 256, ', 1)')" />
						</xsl:element>
						<xsl:element name="text">
							<xsl:attribute name="text-anchor" select="'middle'" />
							<xsl:attribute name="x" select="1000" />
							<xsl:attribute name="y" select="$textY" />
							<xsl:attribute name="filter" select="'url(#captionInnerShadow)'" />
							<xsl:attribute name="fill" select="'url(#captionGradient)'" />
							<xsl:attribute name="style" select="concat('font-weight: 700; font-size: ', $fontSize, 'px; font-family:', $caption/FontName, ';')" />
							<xsl:value-of select="$caption/Text" />
						</xsl:element>
					</g>
				</g>
			</xsl:element>
			<xsl:element name="div">
				<xsl:variable name="width" select="'width: 100%;'" />
				<xsl:variable name="height" select="concat('height: ', xs:integer($caption/BorderWidth) * 10 div 8, 'px;')" />
				<xsl:variable name="borderColor" select="concat('#', $caption/BorderColorR, $caption/BorderColorG, $caption/BorderColorB)" />
				<xsl:variable name="minHeight" select="concat('min-height: ', $caption/BorderWidth, 'px;')" />
				<xsl:variable name="backgroundImage" select="concat('background-image: linear-gradient(to bottom, #000 0%, ', $borderColor, ' 15%, ', $borderColor, ' 70%, #000 100%);')" />
				<xsl:attribute name="style" select="concat($width, $height, $backgroundImage, $minHeight, 'box-sizing: border-box;')" />
				<xsl:value-of select="' '" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="SurveyImage">
		<xsl:element name="li">
			<xsl:attribute name="id" select="concat('ItemLITag', position())" />
			<xsl:element name="div">
				<xsl:attribute name="class" select="'PictureDiv'" />
				<xsl:element name="img">
					<xsl:attribute name="style" select="'max-width: 100%'" />
					<xsl:attribute name="type" select="MimeType" />
					<xsl:attribute name="src" select="concat('/IAT/resource/', $root//ClientId, '/', $root//IATName, '/', ResourceId, '/img')" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="SurveyItem">
		<xsl:variable name="questionNum" select="xs:integer(@QuestionNum)"/>
		<xsl:variable name="itemNum" select="position()" />
		<xsl:variable name="responseType" select="mine:getResponse(.)" />	
		<xsl:variable name="response" select="child::*[name() eq $responseType]" />
		<xsl:variable name="optional" select="@optional" />
		<xsl:element name="li">
			<xsl:if test="some $n in child::* satisfies index-of($responseTypeList, $n/name()) ne 0">
				<xsl:attribute name="id" select="concat('ItemLITag', $itemNum)" />
			</xsl:if>	
			<xsl:if test="$responseType ne 'Instruction'">
				<xsl:choose>
					<xsl:when test="(xs:integer($questionNum) mod 2) eq 0">
						<xsl:attribute name="class" select="'ItemEven'" />
					</xsl:when>
					<xsl:when test="(xs:integer($questionNum) mod 2) eq 1">
						<xsl:attribute name="class" select="'ItemOdd'" />
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:element name="div">
				<xsl:if test="$responseType eq 'Instruction'">
					<xsl:attribute name="class" select="'InstructionsDiv'" />
				</xsl:if>
				<xsl:if test="$responseType ne 'Instruction'">
					<xsl:attribute name="class" select="'SurveyItemDiv'" />
				</xsl:if>
				<xsl:element name="h3">
					<xsl:attribute name="id" select="concat('itemText', $itemNum)" />
					<xsl:value-of select="Text" />
				</xsl:element>
				<xsl:element name="div">
					<xsl:attribute name="name" select="$response/name()" />
						<xsl:apply-templates select="$response">
							<xsl:with-param name="questionNum" as="xs:integer" select="$questionNum" />
							<xsl:with-param name="optional" select="$optional" />
						</xsl:apply-templates>
					
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="Likert">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:variable name="reverseScored" select="@ReverseScored" />
		<xsl:variable name="numChoices" select="xs:integer(@NumChoices)" />
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'RadioButtonTable'" />
			<xsl:attribute name="data-item" select="$questionNum" />
			<xsl:attribute name="data-optional" select="$optional" />
			<xsl:attribute name="data-type" select="'likert'" />
			<xsl:for-each select="Choice">
				<xsl:call-template name="writeRadioButton">
					<xsl:with-param name="questionNum" select="$questionNum" />
					<xsl:with-param name="radioValue">
						<xsl:if test="$reverseScored eq 'true'">
							<xsl:value-of select="$numChoices + 1 - position()" />
						</xsl:if>
						<xsl:if test="$reverseScored eq 'false'">
							<xsl:value-of select="position()" />
						</xsl:if>
					</xsl:with-param>
					<xsl:with-param name="radioLabel" select="." />
				</xsl:call-template>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="TrueFalse">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'RadioButtonTable'" />
			<xsl:attribute name="data-type" select="'truefalse'" />
			<xsl:attribute name="data-item" select="$questionNum" />
			<xsl:attribute name="data-optional" select="$optional" />
			<xsl:call-template name="writeRadioButton">
				<xsl:with-param name="questionNum" select="$questionNum" />
				<xsl:with-param name="radioValue" select="'1'" />
				<xsl:with-param name="radioLabel" select="TrueStatement" />
			</xsl:call-template>
			<xsl:call-template name="writeRadioButton">
				<xsl:with-param name="questionNum" select="$questionNum" />
				<xsl:with-param name="radioValue" select="'0'" />
				<xsl:with-param name="radioLabel" select="FalseStatement" />
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="MultiChoice">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'RadioButtonTable'" />
			<xsl:attribute name="data-type" select="'multichoice'" />
			<xsl:attribute name="data-item" select="$questionNum" />
			<xsl:attribute name="data-optional" select="$optional" />
			<xsl:for-each select="./Choice">
				<xsl:call-template name="writeRadioButton">
					<xsl:with-param name="questionNum" select="$questionNum" />
					<xsl:with-param name="radioValue" select="position()" />
					<xsl:with-param name="radioLabel" select="." />
				</xsl:call-template>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="MultiSelect">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:variable name="numTableRows" select="xs:integer(ceiling((count(Label)) div 2))" />
		<xsl:variable name="response" select="." />
		<xsl:element name="input">
			<xsl:attribute name="name" select="concat('Item', $questionNum)" />
			<xsl:attribute name="type" select="'hidden'" />
			<xsl:attribute name="id" select="concat('Item', $questionNum)" />
			<xsl:attribute name="data-type" select="'multiselect'" />
			<xsl:attribute name="data-min" select="MinSelections" />
			<xsl:attribute name="data-max" select="MaxSelections" />
			<xsl:attribute name="data-item" select="$questionNum" />
			<xsl:attribute name="data-optional" select="$optional" />
		</xsl:element>	
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'CheckBoxTable'" />
			<xsl:for-each select="1 to $numTableRows">
				<xsl:variable name="col1Index" select="position()" />
				<xsl:variable name="col2Index" select="position() + $numTableRows" />
				<tr>
					<td style="width: 0px;">
						<xsl:element name="input">
							<xsl:attribute name="type" select="'checkbox'" />
							<xsl:attribute name="name" select="concat('Item', $questionNum, '_', $col1Index)" />
							<xsl:attribute name="ID" select="concat('Item', $questionNum, '_', $col1Index)" />
						</xsl:element>
					</td>
					<td>
						<xsl:element name="p">
							<xsl:attribute name="class" select="concat('response', $questionNum)" />
							<xsl:element name="label">
								<xsl:attribute name="for" select="concat('Item', $questionNum, '_', $col1Index)" />
								<xsl:value-of select="$response/Label[position() = $col1Index]" />
							</xsl:element>
						</xsl:element>
					</td>
					<xsl:if test="position() + $numTableRows le count($response/Label)">
						<td style="width: 0px;">
							<xsl:element name="input">
								<xsl:attribute name="type" select="'checkbox'" />
								<xsl:attribute name="name" select="concat('Item', $questionNum, '_', $col2Index)" />
								<xsl:attribute name="ID" select="concat('Item', $questionNum, '_', $col2Index)" />
							</xsl:element>
						</td>
						<td>
							<xsl:element name="div">
								<xsl:element name="p">
									<xsl:attribute name="class" select="concat('response', $questionNum)" />
									<xsl:element name="label">
										<xsl:attribute name="for" select="concat('Item', $questionNum, '_', $col2Index)" />
										<xsl:value-of select="$response/Label[position() = $col2Index]" />
									</xsl:element>
								</xsl:element>
							</xsl:element>
						</td>
						
					</xsl:if>
				</tr>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="Date">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:variable name="monthNames">
			<month number="1">January</month>
			<month number="2">February</month>
			<month number="3">March</month>
			<month number="4">April</month>
			<month number="5">May</month>
			<month number="6">June</month>
			<month number="7">July</month>
			<month number="8">August</month>
			<month number="9">September</month>
			<month number="10">October</month>
			<month number="11">November</month>
			<month number="12">December</month>
		</xsl:variable>
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $questionNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $questionNum)" />
				<xsl:attribute name="id" select="concat('Item', $questionNum)" />
				<xsl:attribute name="class" select="'DateInput'" />
				<xsl:attribute name="data-item" select="$questionNum" />
				<xsl:attribute name="data-optional" select="$optional" />
				<xsl:attribute name="data-type" select="'date'" />
				<xsl:attribute name="data-has-start" select="@HasStartDate" />
				<xsl:attribute name="data-has-end" select="@HasEndDate" />
				<xsl:attribute name="data-start-date" select="concat(StartYear, '-', StartMonth, '-', StartDay)" />
				<xsl:attribute name="data-end-date" select="concat(EndYear, '-', EndMonth, '-', EndDay)" />
			</xsl:element>
			<xsl:element name="p">
				<xsl:attribute name="class" select="'DateInputLabel'" />
				<xsl:attribute name="id" select="concat('DateInputLabel', $questionNum)" />
				<xsl:choose>
					<xsl:when test="(@HasStartDate eq 'True') and (@HasEndDate eq 'True')">
						<xsl:value-of select="concat('Please enter a date between ',
							$monthNames/month[@number eq StartMonth], ' ', StartDay, ', ', StartYear,
							' and ',
							$monthNames/month[@number eq EndMonth], ' ', EndDay, ', ', EndYear,
							' in MM/DD/YYYY format.')" />
					</xsl:when>
					<xsl:when test="(@HasStartDate eq 'True') and (@HasEndDate eq 'False')">
						<xsl:value-of select="concat('Please enter a date after ',
							$monthNames/month[@number eq StartMonth], ' ', StartDay, ', ', StartYear,
							' in MM/DD/YYYY format.')" />
					</xsl:when>
					<xsl:when test="(@HasStartDate eq 'False') and (@HasEndDate eq 'True')">
						<xsl:value-of select="concat('Please enter a date before ',
							$monthNames/month[@number eq EndMonth], ' ', EndDay, ', ', EndYear,
							' in MM/DD/YYYY format.')" />
					</xsl:when>
					<xsl:when test="(@HasStartDate eq 'False') and (@HasEndDate eq 'False')">
						<xsl:value-of select="'Please enter a date in MM/DD/YYYY format.'" />
					</xsl:when>
				</xsl:choose>
			</xsl:element>
		</xsl:element>
		<br class="Clear" />
	</xsl:template>
	
	<xsl:template match="BoundedText">
		<xsl:param name="optional" as="xs:string" />
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:variable name="maxTextLength" as="xs:integer" select="mine:textWidth(MaxLength, Format)" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $questionNum)" />
				<xsl:element name="textarea">
					<xsl:attribute name="name" select="concat('Item', $questionNum)" />
					<xsl:attribute name="id" select="concat('Item', $questionNum)" />
					<xsl:attribute name="class" select="'BoundedLengthTextArea'" />
					<xsl:attribute name="style" select="'width: 90%;'" />
					<xsl:attribute name="data-item" select="$questionNum" />
					<xsl:attribute name="data-optional" select="$optional" />
					<xsl:attribute name="data-type" select="'boundedtext'" />
					<xsl:attribute name="data-min-length" select="MinLength" />
					<xsl:attribute name="data-max-length" select="MaxLength" />
					<xsl:variable name="nRows" select="ceiling($maxTextLength div 500)" />
					<xsl:if test="$nRows le 8">
						<xsl:attribute name="rows" select="$nRows" />
					</xsl:if>
					<xsl:if test="$nRows gt 8">
						<xsl:attribute name="rows" select="'8'" />
					</xsl:if>
					<xsl:value-of select="' '" />
				</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="BoundedNumber">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $questionNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $questionNum)" />
				<xsl:attribute name="id" select="concat('Item', $questionNum)" />
				<xsl:attribute name="class" select="'BoundedNumberInput'" />
				<xsl:attribute name="data-item" select="$questionNum" />
				<xsl:attribute name="data-optional" select="$optional" />
				<xsl:attribute name="data-type" select="'boundednumber'" />
				<xsl:attribute name="data-min" select="MinValue" />
				<xsl:attribute name="data-max" select="MaxValue" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="FixedDigit">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $questionNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $questionNum)" />
				<xsl:attribute name="id" select="concat('Item', $questionNum)" />
				<xsl:attribute name="class" select="'FixedDigitInput'" />
				<xsl:attribute name="data-item" select="$questionNum" />
				<xsl:attribute name="data-optional" select="$optional" />
				<xsl:attribute name="data-type" select="'fixeddigit'" />
				<xsl:attribute name="data-length" select="NumDigs" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="RegEx">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="optional" as="xs:string" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $questionNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $questionNum)" />
				<xsl:attribute name="id" select="concat('Item', $questionNum)" />
				<xsl:attribute name="class" select="'RegExInput'" />
				<xsl:attribute name="data-item" select="$questionNum" />
				<xsl:attribute name="data-optional" select="$optional" />
				<xsl:attribute name="data-type" select="'regex'" />
				<xsl:attribute name="data-pattern" select="Expression" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="writeRadioButton">
		<xsl:param name="questionNum" as="xs:integer" />
		<xsl:param name="radioValue" />
		<xsl:param name="radioLabel" />
		<xsl:element name="tr">
			<xsl:element name="td">
				<xsl:attribute name="class" select="'RadioInputCell'" />
				<xsl:element name="input">
					<xsl:attribute name="class" select="'RadioInput'" />
					<xsl:attribute name="type" select="'radio'" />
					<xsl:attribute name="name" select="concat('Item', $questionNum)" />
					<xsl:attribute name="value" select="$radioValue" />
				</xsl:element>
			</xsl:element>
			<xsl:element name="td">
				<xsl:element name="div">
					<xsl:element name="p">
						<xsl:attribute name="class" select="concat('RadioLabelParagraph response', $questionNum)" />
						<xsl:value-of select="$radioLabel" />
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="writeFormatCSS">
		<xsl:param name="format" />
		<xsl:value-of select="concat('font-size: ', xs:integer($format/FontSize), 'px;&#x0A;')" />
		<xsl:value-of select="concat('color: ', $format/Color, ';&#x0A;')" />
		<xsl:if test="$format/Bold eq 'True'">
			<xsl:value-of select="'font-weight: bold;&#x0A;'" />
		</xsl:if>
		<xsl:if test="$format/Bold eq 'False'">
			<xsl:value-of select="'font-weight: normal;&#x0A;'" />
		</xsl:if>
		<xsl:if test="$format/Italic eq 'True'">
			<xsl:value-of select="'font-style: italic;&#x0A;'" />
		</xsl:if>
		<xsl:if test="$format/Italic eq 'False'">
			<xsl:value-of select="'font-style: normal;&#x0A;'" />
		</xsl:if>
		<xsl:value-of select="concat('font-family: &quot;', $format/Font, '&quot;, serif;&#x0A;')" />
	</xsl:template>
</xsl:stylesheet>
