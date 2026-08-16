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


	<xsl:variable name="responseTypeList" select="tokenize('MultipleResponse,WeightedMultipleResponse,Boolean,Likert,MultiBoolean,BoundedLength,BoundedNumber,FixedDigit,RegularExpression,Date', ',')" />
	<xsl:template match="Survey">
		<xsl:variable name="survey" select="." />
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

          @media screen and (max-aspect-ratio: 1) {
			div#mainContent {
          	max-width: 100%;
          }
          }
          @media only screen and (min-aspect-ratio: 1)  {
          div#mainContent {
          	max-width: 1200px;
          	}
          }
          div#mainContent {
				margin: 0px auto;
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

						td {
							padding: 5px 5px;
						}
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
                        width: 25px;
                        }

                        p.RadioLabelParagraph {
                           margin: 0px;
                          padding: 0px;
                        }
                    </xsl:text>
					<xsl:for-each select="SurveyItem">
						<xsl:variable name="itemNum" select="position() + count(preceding-sibling::SurveyImage)" />
						<xsl:variable name="questionNum" select="@QuestionNum" />
						<xsl:value-of select="concat('h3#itemText', $itemNum, ' div div { margin: 2% 5% 0% 5%; } &#x0A;')" />

						<xsl:value-of select="concat('h3#itemText', $itemNum, ' {&#x0A;')" />
						<xsl:if test="Response/@Type ne 'Instruction'">
							<xsl:value-of select="'margin: 2px 5px 3px 20px;&#x0A;'" />
						</xsl:if>
						<xsl:if test="Response/@Type eq 'Instruction'">
							<xsl:if test="count(following-sibling::SurveyItem) ge 1">
								<xsl:if test="following-sibling::SurveyItem[position() eq 1]/Response/@Type ne 'Instruction'">
									<xsl:value-of select="'margin: 2px 5px 8px 20px;&#x0A;'" />
								</xsl:if>
								<xsl:if test="following-sibling::SurveyItem[position() eq 1]/Response/@Type eq 'Instruction'">
									<xsl:value-of select="'margin: 2px 5px 8px 20px;&#x0A;'" />
								</xsl:if>
							</xsl:if>
							<xsl:if test="count(following-sibling::SurveyItem) eq 0">
								<xsl:value-of select="'margin: 2px 5px 8px 20px;&#x0A;'" />
							</xsl:if>
						</xsl:if>
						

						<xsl:call-template name="writeFormatCSS">
							<xsl:with-param name="format" select="Format" />
						</xsl:call-template>
						<xsl:value-of select="'margin: 0px;'" />
						<xsl:value-of select="'}&#x0A;'" />
						<xsl:variable name="selectionBasedResponses">
							<xsl:sequence select="tokenize('MultipleResponse,WeightedMultipleResponse,Boolean,Likert,MultiBoolean', ',')" />
						</xsl:variable>
						<xsl:for-each select="child::*[contains($selectionBasedResponses, name())]">
							<xsl:value-of select="concat('p.response', $questionNum, '{&#x0A;')" />
							<xsl:call-template name="writeFormatCSS">
								<xsl:with-param name="format" select="./Format" />
							</xsl:call-template>
							<xsl:value-of select="'padding: 0px;&#x0A;'" />
							<xsl:value-of select="'margin: 0px;&#x0A;'" />
							<xsl:value-of select="'vertical-align: top;&#x0A;'" />
							<xsl:value-of select="'text-align: left;&#x0A;'" />
							<xsl:value-of select="'}&#x0A;'" />
							<xsl:value-of select="concat('li#ItemLITag', $itemNum, ' input {&#x0A;')" />
							<xsl:value-of select="concat('width: ', xs:integer(Format/FontSize) * 2 div 3, 'px;&#x0A;')" />
							<xsl:value-of select="concat('height: ', xs:integer(Format/FontSize) * 2 div 3, 'px;&#x0A;')" />
							<xsl:value-of select="'}&#x0A;'" />

						</xsl:for-each>
						<xsl:variable name="nonSelectionBasedResponses">
							<xsl:sequence select="tokenize('BoundedLength,BoundedNumber,FixedDigit,RegularExpression,Date', ',')" />
						</xsl:variable>

						<xsl:for-each select="child::*[contains($nonSelectionBasedResponses, name())]">
							<xsl:value-of select="concat('div#response', $questionNum,' input {&#x0A;')" />
							<xsl:call-template name="writeFormatCSS">
								<xsl:with-param name="format" select="./Format" />
							</xsl:call-template>
							<xsl:value-of select="'padding: 0px;&#x0A;'" />
							<xsl:value-of select="'text-align: left;&#x0A;'" />
							<xsl:value-of select="'}&#x0A;'" />
							<xsl:value-of select="concat('div#response', $questionNum, ' p {&#x0A;')" />
							<xsl:call-template name="writeFormatCSS">
								<xsl:with-param name="format" select="./Format" />
							</xsl:call-template>
							<xsl:value-of select="'vertical-align: middle;&#x0A;'" />
							<xsl:value-of select="'text-align: left;&#x0A;'" />
							<xsl:value-of select="'}&#x0A;'" />
							<xsl:value-of select="concat('div#response', $questionNum, ' textarea {&#x0A;')" />
							<xsl:call-template name="writeFormatCSS">
								<xsl:with-param name="format" select="./Format" />
							</xsl:call-template>
							<xsl:value-of select="'vertical-align: middle;&#x0A;'" />
							<xsl:value-of select="'text-align: left;&#x0A;'" />
							<xsl:value-of select="'}&#x0A;'" />


						</xsl:for-each>
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
					<xsl:attribute name="type" select="'text/javascript'" />
					<xsl:attribute name="src" select="'/IAT/scripts/MiscUtils.js'" />
					<xsl:value-of select="' '" />
				</xsl:element>	
				<xsl:element name="script">
					<xsl:attribute name="type" select="'text/javascript'" />
					<xsl:attribute name="src" select="'/IAT/scripts/SubFunct.js'" />
					<xsl:value-of select="' '" />
				</xsl:element>	
				<xsl:element name="script">
					<xsl:attribute name="type" select="'text/javascript'" />
					<xsl:attribute name="src" select="string-join(('/IAT/resource', ClientId, IAT, ScriptId), '/')" />
					<xsl:value-of select="' '" />
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
					<form id="SurveyForm" method="POST">
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
										<xsl:attribute name="ItemNum" select="@ItemNum" />
										<xsl:if test="name() eq 'SurveyImage'">
											<xsl:attribute name="Image" select="'true'" />
										</xsl:if>
										<xsl:if test="name() eq 'SurveyItem'">
											<xsl:attribute name="Image" select="'false'" />
										</xsl:if>	
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
						</form>
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
								<xsl:attribute name="style" select="concat('font-size: ', $fontSize, 'px; font-family:', $caption/FontName, ';')" />
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
							<xsl:attribute name="style" select="concat('font-size: ', $fontSize, 'px; font-family:', $caption/FontName, ';')" />
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
					<xsl:attribute name="src" select="concat('data:', MimeType, ';base64,', ImageData)" />
					<xsl:attribute name="id" select="Id" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template match="SurveyItem">
		<xsl:variable name="questionNum" select="xs:integer(@QuestionNum)"/>
		<xsl:variable name="itemNum" select="position()" />
		<xsl:element name="li">
			<xsl:if test="some $n in child::* satisfies index-of($responseTypeList, $n/name()) ne 0">
				<xsl:attribute name="id" select="concat('ItemLITag', $itemNum)" />
			</xsl:if>	
			<xsl:if test="Response/@Type ne 'Instruction'">
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
				<xsl:if test="Response/@Type eq 'Instruction'">
					<xsl:attribute name="class" select="'InstructionsDiv'" />
				</xsl:if>
				<xsl:if test="Response/@Type ne 'Instruction'">
					<xsl:attribute name="class" select="'SurveyItemDiv'" />
				</xsl:if>
				<xsl:element name="h3">
					<xsl:attribute name="id" select="concat('itemText', $itemNum)" />
					<xsl:value-of select="Text" />
				</xsl:element>

				<xsl:element name="div">
					<xsl:variable name="childResponse" select="for $i in child::* return $i[index-of($responseTypeList, name()) ne 0]" />
					<xsl:attribute name="style" select="'margin: 1% 5% 0% 5%;'" />
					<xsl:attribute name="name" select="$childResponse/name()" />
					<xsl:apply-templates select="$childResponse">
						<xsl:with-param name="itemNum" as="xs:integer" select="$questionNum" />
					</xsl:apply-templates>

				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template match="Likert">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:variable name="reverseScored" select="./IsReverseScored" />
		<xsl:variable name="numChoices" select="xs:integer(./NumChoices)" />
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'RadioButtonTable'" />
			<xsl:for-each select="./ChoiceDescriptions/Choice">
				<xsl:call-template name="writeRadioButton">
					<xsl:with-param name="itemNum" select="$itemNum" />
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

	<xsl:template match="Boolean">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'RadioButtonTable'" />
			<xsl:call-template name="writeRadioButton">
				<xsl:with-param name="itemNum" select="$itemNum" />
				<xsl:with-param name="radioValue" select="'1'" />
				<xsl:with-param name="radioLabel" select="./TrueStatement" />
			</xsl:call-template>
			<xsl:call-template name="writeRadioButton">
				<xsl:with-param name="itemNum" select="$itemNum" />
				<xsl:with-param name="radioValue" select="'0'" />
				<xsl:with-param name="radioLabel" select="./FalseStatement" />
			</xsl:call-template>
		</xsl:element>
	</xsl:template>

	<xsl:template match="MultipleResponse">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'RadioButtonTable'" />
			<xsl:for-each select="./Choices/Choice">
				<xsl:call-template name="writeRadioButton">
					<xsl:with-param name="itemNum" select="$itemNum" />
					<xsl:with-param name="radioValue" select="position()" />
					<xsl:with-param name="radioLabel" select="." />
				</xsl:call-template>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template match="WeightedMultipleResponse">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:element name="table">
			<xsl:attribute name="width" select="'90%'" />
			<xsl:attribute name="class" select="'RadioButtonTable'" />
			<xsl:for-each select="./WeightedChoices/WeightedChoice">
				<xsl:call-template name="writeRadioButton">
					<xsl:with-param name="itemNum" select="$itemNum" />
					<xsl:with-param name="radioValue" select="./Weight" />
					<xsl:with-param name="radioLabel" select="./Choice" />
				</xsl:call-template>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template match="MultiBoolean">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:variable name="numTableRows" select="xs:integer(ceiling((count(./Labels/Label)) div 2))" />
		<xsl:variable name="labels" select="./Labels" />
		<xsl:element name="input">
			<xsl:attribute name="name" select="concat('Item', $itemNum)" />
			<xsl:attribute name="type" select="'hidden'" />
			<xsl:attribute name="id" select="concat('Item', $itemNum)" />
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
							<xsl:attribute name="name" select="concat('Item', $itemNum, '_', $col1Index)" />
							<xsl:attribute name="ID" select="concat('Item', $itemNum, '_', $col1Index)" />
						</xsl:element>
					</td>
					<td>
						<xsl:element name="p">
							<xsl:attribute name="class" select="concat('response', $itemNum)" />
							<xsl:element name="label">
								<xsl:attribute name="for" select="concat('Item', $itemNum, '_', $col1Index)" />
								<xsl:value-of select="$labels/Label[position() = $col1Index]" />
							</xsl:element>
						</xsl:element>
					</td>
					<xsl:if test="position() + $numTableRows le count($labels/Label)">
						<td style="width: 0px;">
							<xsl:element name="input">
								<xsl:attribute name="type" select="'checkbox'" />
								<xsl:attribute name="name" select="concat('Item', $itemNum, '_', $col2Index)" />
								<xsl:attribute name="ID" select="concat('Item', $itemNum, '_', $col2Index)" />
							</xsl:element>
						</td>
						<td>
							<xsl:element name="div">
								<xsl:element name="p">
									<xsl:attribute name="class" select="concat('response', $itemNum)" />
									<xsl:element name="label">
										<xsl:attribute name="for" select="concat('Item', $itemNum, '_', $col2Index)" />
										<xsl:value-of select="$labels/Label[position() = $col2Index]" />
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
		<xsl:param name="itemNum" as="xs:integer" />
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
			<xsl:attribute name="id" select="concat('response', $itemNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $itemNum)" />
				<xsl:attribute name="id" select="concat('Item', $itemNum)" />
				<xsl:attribute name="class" select="'DateInput'" />
			</xsl:element>
			<xsl:element name="p">
				<xsl:attribute name="class" select="DateInputLabel" />
				<xsl:attribute name="id" select="concat('DateInputLabel', $itemNum)" />
				<xsl:if test="StartDate[@HasValue eq 'True']">
					<xsl:variable name="startMonth" select="StartDate/Month" />
					<xsl:if test="EndDate[@HasValue eq 'True']">
						<xsl:variable name="endMonth" select="EndDate/Month" />
						Please enter a date between
						<xsl:value-of select="concat($monthNames/month[@number eq $startMonth], ' ')" />
						<xsl:value-of select="StartDate/Day" />
						,
						<xsl:value-of select="StartDate/Year" />
						and
						<xsl:value-of select="concat($monthNames/month[@number eq $endMonth], ' ')" />
						<xsl:value-of select="EndDate/Day" />
						,
						<xsl:value-of select="EndDate/Year" />
						in MM/DD/YYYY format.
					</xsl:if>
					<xsl:if test="EndDate[@HasValue ne 'True']">
						Please enter a date after
						<xsl:value-of select="concat($monthNames/month[@number eq $startMonth], ' ')" />
						<xsl:value-of select="StartDate/Day" />
						,
						<xsl:value-of select="StartDate/Year" />
						in MM/DD/YYYY format.
					</xsl:if>
				</xsl:if>
				<xsl:if test="StartDate[@HasValue ne 'True']">
					<xsl:if test="EndDate[@HasValue eq 'True']">
						<xsl:variable name="endMonth" select="EndDate/Month" />
						Please enter a date before
						<xsl:value-of select="concat($monthNames/month[@number eq $endMonth], ' ')" />
						<xsl:value-of select="EndDate/Day" />
						,
						<xsl:value-of select="EndDate/Year" />
						in MM/DD/YYYY format.
					</xsl:if>
					<xsl:if test="EndDate[@HasValue ne 'True']">
						Please enter a date in MM/DD/YYYY format.
					</xsl:if>
				</xsl:if>
			</xsl:element>
		</xsl:element>
		<br class="Clear" />
	</xsl:template>

	<xsl:template match="BoundedLength">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:variable name="maxTextLength" as="xs:integer" select="mine:textWidth(MaxLength, Format)" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $itemNum)" />
			<xsl:if test="$maxTextLength le 600">
				<xsl:element name="input">
					<xsl:attribute name="name" select="concat('Item', $itemNum)" />
					<xsl:attribute name="id" select="concat('Item', $itemNum)" />
					<xsl:attribute name="style" select="concat('width: ', xs:string(xs:integer($maxTextLength) div 9 * 8), 'px;')" />
					<xsl:if test="$itemNum eq xs:integer(//Survey/@UniqueResponseItem)">
						<xsl:attribute name="onblur" select="'CheckUniqueResponse(event);'" />
					</xsl:if>
				</xsl:element>
			</xsl:if>
			<xsl:if test="$maxTextLength gt 600">
				<xsl:element name="textarea">
					<xsl:attribute name="name" select="concat('Item', $itemNum)" />
					<xsl:attribute name="id" select="concat('Item', $itemNum)" />
					<xsl:attribute name="class" select="'BoundedLengthTextArea'" />
					<xsl:attribute name="style" select="'width: 90%;'" />
					<xsl:variable name="nRows" select="ceiling($maxTextLength div 600)" />
					<xsl:if test="$nRows le 8">
						<xsl:attribute name="rows" select="$nRows" />
					</xsl:if>
					<xsl:if test="$nRows gt 8">
						<xsl:attribute name="rows" select="'8'" />
					</xsl:if>
					<xsl:if test="$itemNum eq xs:integer(//Survey/@UniqueResponseItem)">
						<xsl:attribute name="onblur" select="'CheckUniqueResponse(event);'" />
					</xsl:if>
					<xsl:value-of select="' '" />
				</xsl:element>
			</xsl:if>
		</xsl:element>
	</xsl:template>

	<xsl:template match="BoundedNumber">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $itemNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $itemNum)" />
				<xsl:attribute name="ID" select="concat('Item', $itemNum)" />
				<xsl:attribute name="class" select="'BoundedNumberInput'" />
				<xsl:if test="$itemNum eq xs:integer(//Survey/@UniqueResponseItem)">
					<xsl:attribute name="onblur" select="'CheckUniqueResponse(event);'" />
				</xsl:if>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template match="FixedDigit">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $itemNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $itemNum)" />
				<xsl:attribute name="ID" select="concat('Item', $itemNum)" />
				<xsl:attribute name="class" select="'FixedDigitInput'" />
				<xsl:if test="$itemNum eq xs:integer(//Survey/@UniqueResponseItem)">
					<xsl:attribute name="onblur" select="'CheckUniqueResponse(event);'" />
				</xsl:if>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template match="RegularExpression">
		<xsl:param name="itemNum" as="xs:integer" />
		<xsl:element name="div">
			<xsl:attribute name="id" select="concat('response', $itemNum)" />
			<xsl:element name="input">
				<xsl:attribute name="type" select="'text'" />
				<xsl:attribute name="name" select="concat('Item', $itemNum)" />
				<xsl:attribute name="ID" select="concat('Item', $itemNum)" />
				<xsl:attribute name="class" select="'RegExInput'" />
				<xsl:if test="$itemNum eq xs:integer(//Survey/@UniqueResponseItem)">
					<xsl:attribute name="onblur" select="'CheckUniqueResponse(event);'" />
				</xsl:if>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="writeRadioButton">
		<xsl:param name="itemNum" />
		<xsl:param name="radioValue" />
		<xsl:param name="radioLabel" />
		<xsl:element name="tr">
			<xsl:element name="td">
				<xsl:attribute name="class" select="'RadioInputCell'" />
				<xsl:element name="input">
					<xsl:attribute name="class" select="'RadioInput'" />
					<xsl:attribute name="type" select="'radio'" />
					<xsl:attribute name="name" select="concat('Item', $itemNum)" />
					<xsl:attribute name="value" select="$radioValue" />
				</xsl:element>
			</xsl:element>
			<xsl:element name="td">
				<xsl:element name="div">
					<xsl:element name="p">
						<xsl:attribute name="class" select="'RadioLabelParagraph'" />
						<xsl:attribute name="class" select="concat('response', $itemNum)" />
						<xsl:value-of select="$radioLabel" />
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="writeFormatCSS">
		<xsl:param name="format" />
		<xsl:value-of select="concat('font-size: ', xs:integer($format/FontSize) * 7 div 4, 'px;&#x0A;')" />
		<xsl:value-of select="concat('color: #', $format/ColorR, $format/ColorG, $format/ColorB, ';&#x0A;')" />
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
