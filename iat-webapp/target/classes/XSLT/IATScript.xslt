<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                exclude-result-prefixes="xs">

    <xsl:output method="xml" encoding="utf-8" indent="yes"/>

    <xsl:variable name="root" select="/" />
    
    <xsl:variable name="variableDeclarations">
        <Declarations>
            <Declaration>var inPracticeBlock = false;</Declaration>
            <Declaration>var itemBlock;</Declaration>
            <Declaration>var currentItemKeyedDir = "Left";</Declaration>
            <Declaration>var currentContinueKeyCode = 32;</Declaration>
            <Declaration>var isErrorMarked = false;</Declaration>
            <Declaration>var currentHandler = null;</Declaration>
            <Declaration>var currentStimulus = null;</Declaration>
            <Declaration>var currentItemNum = 0;</Declaration>
            <Declaration>var currentItemID;</Declaration>
            <Declaration>var EventList = new Array();</Declaration>
            <Declaration>var EventCtr = 0;</Declaration>
            <Declaration>var ErrorMark;</Declaration>
            <Declaration>var ImageLoadStatusTextElement;</Declaration>
            <Declaration>var ClickToStartElement;</Declaration>
            <Declaration>var ClickToStartText;</Declaration>
            <Declaration>var KeyedDir;</Declaration>
            <Declaration>var KeyedDirArray;</Declaration>
            <Declaration>var OriginatingBlockArray;</Declaration>
            <Declaration>var StimulusIDArray;</Declaration>
            <Declaration>var ItemNumArray;</Declaration>
            <Declaration>var KeyedDirInput;</Declaration>
            <Declaration>var Display;</Declaration>
            <Declaration>var DefaultKey;</Declaration>
            <Declaration>var FreeItemIDs;</Declaration>
            <Declaration>var Items = new Array();</Declaration>
            <Declaration>var Items1 = new Array();</Declaration>
            <Declaration>var Items2 = new Array();</Declaration>
            <Declaration>var ctr;</Declaration>
            <xsl:for-each select="//DisplayItemList/IATDisplayItem">
                <Declaration>
                    <xsl:value-of select="concat('var DI', ./ID, ';')"/>
                </Declaration>
            </xsl:for-each>
            <Declaration>var instructionBlock;</Declaration>
            <Declaration>var InstructionBlocks;</Declaration>
            <Declaration>var alternate;</Declaration>
            <Declaration>var itemBlockCtr, itemCtr = 0;</Declaration>
            <Declaration>var instructionsBlockCtr;</Declaration>
            <Declaration>var numAlternatedItemBlocks;</Declaration>
            <Declaration>var numAlternatedInstructionBlocks;</Declaration>
            <Declaration>var processIATItemFunctions = new Array();</Declaration>
            <Declaration>var iatBlocks = new Array();</Declaration>
        </Declarations>
    </xsl:variable>


    <xsl:variable name="GlobalAbbreviations">
        <xsl:variable name="Globals" select="string-join(for $elem in $variableDeclarations/Declarations/Declaration return replace($elem, '^var\s+(.+);$', '$1;'), '')" />
        <xsl:analyze-string select="$Globals" regex="([A-Za-z_][A-Za-z0-9_]*)(\s*=\s*((\[|\s+|[^,;=/&#34;\(\[\]]+|(&#34;[^&#xA;&#xD;&#34;]*?&#34;)+|\(\)||\(([^;,=&#34;]*(,)?(&#34;[^&#xA;&#xD;&#34;]*?&#34;)?)*?\)|/[^/\n]+?/|\](\s*,)?)+))?(\s*(,|;))">
            <xsl:matching-substring>
                <xsl:element name="Entry">
                    <xsl:attribute name="type" select="'global'" />
                    <xsl:element name="OrigName">
                        <xsl:value-of select="regex-group(1)" />
                    </xsl:element>
                    <xsl:element name="NewName">
                        <xsl:value-of select="concat('_', $globalVariablePrefix, position())" />
                    </xsl:element>
                    <xsl:element name="Assign">
                        <xsl:value-of select="normalize-space(regex-group(3))" />
                    </xsl:element>
                </xsl:element>
            </xsl:matching-substring>
        </xsl:analyze-string>
		<xsl:for-each select="//Globals/Global">
			<xsl:element name="Entry">
				<xsl:attribute name="type" select="'global'" />
				<xsl:element name="OrigName">
					<xsl:value-of select="OrigName" />
				</xsl:element>
				<xsl:element name="NewName">
					<xsl:value-of select="NewName" />
				</xsl:element>
				<xsl:element name="Assign" />
			</xsl:element>
		</xsl:for-each>
    </xsl:variable>


    <xsl:variable name="functionPrefix">
        <xsl:value-of select="'iF'"/>
    </xsl:variable>

    <xsl:variable name="globalVariablePrefix">
        <xsl:value-of select="'iG'"/>
    </xsl:variable>

    <xsl:variable name="classPrefix">
        <xsl:value-of select="'iC'"/>
    </xsl:variable>

    <xsl:variable name="classFunctionPrefix">
        <xsl:value-of select="'iCF'"/>
    </xsl:variable>

    <xsl:variable name="Classes">
        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATDI'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'no'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">id</xsl:element>
                    <xsl:element name="Param">img</xsl:element>
                    <xsl:element name="Param">x</xsl:element>
                    <xsl:element name="Param">y</xsl:element>
                    <xsl:element name="Param">width</xsl:element>
                    <xsl:element name="Param">height</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">this.id = id;</xsl:element>
                    <xsl:element name="Code">this.imgTag = img;</xsl:element>
                    <xsl:element name="Code">this.imgSrc = img.src;</xsl:element>
                    <xsl:element name="Code">this.x = x;</xsl:element>
                    <xsl:element name="Code">this.y = y;</xsl:element>
                    <xsl:element name="Code">this.width = width;</xsl:element>
                    <xsl:element name="Code">this.height = height;</xsl:element>
                    <xsl:element name="Code">this.imgTagID = "IATDI" + id.toString();</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getImgTagID'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.imgTagID;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'unwireClickHandler'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.imgTag.onclick = null;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'wireClickHandler'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">clickHandler</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.imgTag.onclick = clickHandler;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getId'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.id;</xsl:element>
                    </xsl:element>
                </xsl:element>


                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'setImgTagID'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">ImgTagID</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.imgTagID = ImgTagID;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getImgTag'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.imgTag;</xsl:element>
                    </xsl:element>
                </xsl:element>


                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Outline'"/>
                    <xsl:element name="Params"/>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.imgTag.className = "outlinedDI";</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Display'"/>
                    <xsl:element name="Params">
                        <xsl:element name="Param">parentNode</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.imgTag.id = this.imgTagID;</xsl:element>
                        <xsl:element name="Code">parentNode.appendChild(this.imgTag);</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Hide'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">if (this.imgTag.parentNode) {</xsl:element>
                        <xsl:element name="Code">this.imgTag.parentNode.removeChild(this.imgTag);</xsl:element>
                        <xsl:element name="Code">}</xsl:element>
                        <xsl:element name="Code">this.imgTag.className = "";</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>


        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATDisplay'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'no'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params"/>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('this.interiorWidth = ', //Layout/InteriorWidth, ';')"/>
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('this.interiorHeight = ', //Layout/InteriorHeight, ';')"/>
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('this.leftResponseKey = &quot;', //LeftResponseKey, '&quot;;')"/>
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('this.rightResponseKey = &quot;', //RightResponseKey, '&quot;;')"/>
                    </xsl:element>
                    <xsl:element name="Code">this.divTag  = document.getElementById("IATDisplayDiv");</xsl:element>
                    <xsl:element name="Code">while (this.divTag.hasChildNodes())</xsl:element>
                    <xsl:element name="Code">this.divTag.removeChild(this.divTag.firstChild);</xsl:element>
                    <xsl:element name="Code">this.displayItems = new Array();</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'AddDisplayItem'"/>
                    <xsl:element name="Params">
                        <xsl:element name="Param">di</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">this.displayItems.push(di);</xsl:element>
                        <xsl:element name="Code">di.Display(this.divTag);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getDivTag'"/>
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.divTag;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getLeftResponse'"/>
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.leftResponseKey;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getRightResponse'"/>
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.rightResponseKey;</xsl:element>
                    </xsl:element>
                </xsl:element>


                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'RemoveDisplayItem'"/>
                    <xsl:element name="Params">
                        <xsl:element name="Param">di</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">for (var ctr = 0; ctr &lt; this.displayItems.length; ctr++) {</xsl:element>
                        <xsl:element name="Code">if (this.displayItems[ctr].getId() == di.getId()) {</xsl:element>
                        <xsl:element name="Code">this.displayItems[ctr].Hide();</xsl:element>
                        <xsl:element name="Code">this.displayItems.splice(ctr, 1);</xsl:element>
                        <xsl:element name="Code">}</xsl:element>
                        <xsl:element name="Code">}</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>



                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Clear'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">for (var ctr = 0; ctr &lt; this.displayItems.length; ctr++)</xsl:element>
                        <xsl:element name="Code">this.displayItems[ctr].Hide();</xsl:element>
                        <xsl:element name="Code">this.displayItems.splice(0, this.displayItems.length);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'StartTimer'"/>
                    <xsl:element name="Params"/>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.startTime = (new Date()).getTime();</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'StopTimer'"/>
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return (new Date()).getTime() - this.startTime;</xsl:element>
                    </xsl:element>
                </xsl:element>

            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATEvent'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'no'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">handler</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">this.handler = handler;</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">if (!this.handler)</xsl:element>
                        <xsl:element name="Code">EventList[++EventCtr].Execute();</xsl:element>
                        <xsl:element name="Code">else {</xsl:element>
                        <xsl:element name="Code">EventUtil.addHandler(document, "keypress", this.handler, this);</xsl:element>
                        <xsl:element name="Code">}</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATSubmitEvent'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATEvent'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params" />
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">IATEvent.call(this, null);</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'appendFormData'"/>
                    <xsl:element name="Params">
                        <xsl:element name="Param">form</xsl:element>
                        <xsl:element name="Param">name</xsl:element>
                        <xsl:element name="Param">value</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">var elem = document.createElement("input");</xsl:element>
                        <xsl:element name="Code">elem.type = "hidden";</xsl:element>
                        <xsl:element name="Code">elem.name = name;</xsl:element>
                        <xsl:element name="Code">elem.value = value;</xsl:element>
                        <xsl:element name="Code">form.appendChild(elem);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
                    

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">var form = document.getElementById("IATForm");</xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "target", "adminV2");</xsl:element>
                        <xsl:variable name="numItems" select="sum($root//IATEvent[@EventType eq 'BeginIATBlock']/NumPresentations)" />
                        <xsl:element name="Code">
                            <xsl:value-of select="concat('this.appendFormData(form, &quot;NumItems&quot;, &quot;', $numItems, '&quot;);')" />
                        </xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "IATSESSIONID", sessionStorage.getItem("IATSESSIONID"));</xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "AdminPhase", sessionStorage.getItem("AdminPhase"));</xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "TestSegment", sessionStorage.getItem("TestSegment"));</xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "referer", sessionStorage.getItem("HTTP_REFERER"));</xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "target", "adminV2");</xsl:element>
                        <xsl:element name="Code">var search = new URLSearchParams(window.location.search);</xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "IATName", search.get("IATName"));</xsl:element>
                        <xsl:element name="Code">this.appendFormData(form, "ClientID", search.get("ClientID"));</xsl:element>
                        <xsl:element name="Code">if (sessionStorage.getItem("LastAdminPhase") === "true")</xsl:element>
                        <xsl:element name="Code">sessionStorage.clear();</xsl:element>
                        <xsl:element name="Code">submitted = true;</xsl:element>
                        <xsl:element name="Code">form.submit();</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATItem'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATEvent'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">eventNum</xsl:element>
                    <xsl:element name="Param">stimulus</xsl:element>
                    <xsl:element name="Param">itemNum</xsl:element>
                    <xsl:element name="Param">keyedDir</xsl:element>
                    <xsl:element name="Param">blockNum</xsl:element>
                    <xsl:element name="Param">startBlock</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                <xsl:text>
                    this.eventNum = eventNum;
                    this.keyedDir = keyedDir;
                    this.blockNum = blockNum;
                    this.keyedDir = keyedDir
                    this.keypress = this._keypress.bind(this);
                    this.click = this._click.bind(this);
                    this.isErrorMarked = false;
                    this.stimulus = stimulus;
                    this.itemNum = itemNum;
                    return this;
                </xsl:text>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="tokenize($constructorBodyElems, '&#x0A;')">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'_keypress'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">event</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                    <xsl:text>
                        if (this.keyedDir === "Left") {
                            if ((event.key === Display.getLeftResponse()) || (event.key === Display.getLeftResponse().toUpperCase())) 
                                this.correct();
                                 else if ((event.key === Display.getRightResponse()) || (event.key === Display.getRightResponse().toUpperCase())) 
                            this.error();
                            }

                        if (this.keyedDir === "Right") {
                            if ((event.key === Display.getLeftResponse()) || (event.key === Display.getLeftResponse().toUpperCase())) 
                                this.error();
                                 else if ((event.key === Display.getRightResponse()) || (event.key === Display.getRightResponse().toUpperCase())) 
                            this.correct();
                            }
                        </xsl:text>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:if test="string-length(normalize-space(.)) gt 0">
                                <xsl:element name="Code">
                                    <xsl:value-of select="normalize-space(.)"/>
                                </xsl:element>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'_click'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">event</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                    <xsl:text>
                        if (this.keyedDir === "Left") {
                            if (event.target === this.leftResponseDI.getImgTag())
                                this.correct();
                               else if (event.target === this.rightResponseDI.getImgTag()) 
                            this.error();
                            }
                        
                        if (this.keyedDir === "Right") {
                            if (event.target === this.rightResponseDI.getImgTag())
                                this.correct();
                               else if (event.target === this.leftResponseDI.getImgTag()) 
                            this.error();
                            }
                        </xsl:text>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:if test="string-length(normalize-space(.)) gt 0">
                                <xsl:element name="Code">
                                    <xsl:value-of select="normalize-space(.)"/>
                                </xsl:element>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>



                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'correct'" />
                    <xsl:element name="Params" />
                    <xsl:variable name="functionBodyElems">
                    <xsl:text>
                        document.body.removeEventListener("keydown", this.keypress);
                        this.rightResponseDI.getImgTag().removeEventListener("click", this.click);
                        this.leftResponseDI.getImgTag().removeEventListener("click", this.click);
                        var latency = Display.StopTimer();
                        var form = document.getElementById("IATForm");
                        var elem = document.createElement("input");
                        elem.type = "hidden";
                        elem.name = "Item" + itemCtr.toString();
                        elem.id = "Item" + itemCtr.toString();
                        elem.value = this.itemNum.toString();
                        form.appendChild(elem);
                        elem = document.createElement("input");
                        elem.type = "hidden";
                        elem.name = "Latency" + itemCtr.toString();
                        elem.id = "Latency" + itemCtr.toString();
                        elem.value = latency.toString();
                        form.appendChild(elem);
                        elem = document.createElement("input");
                        elem.type = "hidden";
                        elem.name = "Block" + itemCtr.toString();
                        elem.id = "Block" + itemCtr.toString();
                        elem.value = this.blockNum.toString();
                        form.appendChild(elem);
                        elem = document.createElement("input");
                        elem.type = "hidden";
                        elem.name = "Error" + itemCtr.toString();
                        elem.id = itemCtr.toString();
                        if (!this.errorMarked)
                            elem.value = "false";
                        else
                            elem.value = "true";    
                        form.appendChild(elem);
                        itemCtr++;
                        if (this.isErrorMarked)
                            Display.RemoveDisplayItem(ErrorMark);
                        this.isErrorMarked = false;
                        Display.RemoveDisplayItem(this.stimulus);
                        var evt = EventList[++EventCtr];
                        evt.Execute();
                    </xsl:text>                        
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:if test="string-length(normalize-space(.)) gt 0">
                                <xsl:element name="Code">
                                    <xsl:value-of select="normalize-space(.)"/>
                                </xsl:element>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'error'" />
                    <xsl:element name="Params" />
                    <xsl:variable name="functionBodyElems">
                    <xsl:text>
                        if (!this.isErrorMarked) {
                            Display.AddDisplayItem(ErrorMark);
                            this.isErrorMarked = true;
                        }
                    </xsl:text>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:if test="string-length(normalize-space(.)) gt 0">
                                <xsl:element name="Code">
                                    <xsl:value-of select="normalize-space(.)"/>
                                </xsl:element>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>                                            


                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        this.leftResponseDI = iatBlocks[this.blockNum - 1].getLeftResponseDI();
                        this.rightResponseDI = iatBlocks[this.blockNum - 1].getRightResponseDI();
                        Display.AddDisplayItem(this.stimulus);
                        Display.StartTimer();
                        window.setTimeout(100, this.ContinueExecute());
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'ContinueExecute'" />
                    <xsl:element name="Params" />
                    <xsl:variable name="functionBodyElems">
                        document.body.addEventListener("keydown", this.keypress);
                        this.leftResponseDI.getImgTag().addEventListener("click", this.click);
                        this.rightResponseDI.getImgTag().addEventListener("click", this.click);
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATBeginBlock'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATEvent'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">isPracticeBlock</xsl:element>
                    <xsl:element name="Param">leftDisplayItem</xsl:element>
                    <xsl:element name="Param">rightDisplayItem</xsl:element>
                    <xsl:element name="Param">instructionsDisplayItem</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">IATEvent.call(this, null);</xsl:element>
                    <xsl:element name="Code">this.isPracticeBlock = isPracticeBlock;</xsl:element>
                    <xsl:element name="Code">this.leftDisplayItem = leftDisplayItem;</xsl:element>
                    <xsl:element name="Code">this.rightDisplayItem = rightDisplayItem;</xsl:element>
                    <xsl:element name="Code">this.instructionsDisplayItem = instructionsDisplayItem;</xsl:element>
                    <xsl:element name="Code">iatBlocks.push(this);</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getLeftResponseDI'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.leftDisplayItem;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getRightResponseDI'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.rightDisplayItem;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">inPracticeBlock = this.isPracticeBlock;</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.leftDisplayItem);</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.rightDisplayItem);</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.instructionsDisplayItem);</xsl:element>
                        <xsl:element name="Code">IATEvent.prototype.Execute.call(this);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATEndBlock'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATEvent'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params" />
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">IATEvent.call(this, null);</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">inPracticeBlock = false;</xsl:element>
                        <xsl:element name="Code">Display.Clear();</xsl:element>
                        <xsl:element name="Code">IATEvent.prototype.Execute.call(this);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATInstructionScreen'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATEvent'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">continueChar</xsl:element>
                    <xsl:element name="Param">continueInstructionsDI</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    this.continueChar = continueChar;
                    this.continueInstructionsDI = continueInstructionsDI;
                    this.keypress = this._keypress.bind(this);
                    this.click = this._click.bind(this);
                    return this;
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="tokenize($constructorBodyElems, '&#x0A;')">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'_click'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">event</xsl:element>  
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                        this.continueInstructionsDI.getImgTag().removeEventListener("click", this.click);
                        document.body.removeEventListener("keypress", this.keypress);
                        Display.Clear();
                        window.setTimeout(EventList[++EventCtr].Execute(), 100);    
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
          
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'_keypress'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">event</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                        if (event.keyCode == this.continueChar) {
                            this.continueInstructionsDI.getImgTag().removeEventListener("click", this.click);
                            document.body.removeEventListener("keypress", this.keypress);
                            Display.Clear();
                            window.setTimeout(EventList[++EventCtr].Execute(), 100);    
                        }
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:variable name="functionBodyElems">
                        this.continueInstructionsDI.getImgTag().addEventListener("click", this.click);
                        document.body.addEventListener("keypress", this.keypress);
                        Display.AddDisplayItem(this.continueInstructionsDI);
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="tokenize($functionBodyElems, '&#x0A;')">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATTextInstructionScreen'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATInstructionScreen'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">continueChar</xsl:element>
                    <xsl:element name="Param">continueInstructionsDI</xsl:element>
                    <xsl:element name="Param">textInstructionsDI</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">IATInstructionScreen.call(this, continueChar, continueInstructionsDI);</xsl:element>
                    <xsl:element name="Code">this.textInstructionsDI = textInstructionsDI;</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">Display.AddDisplayItem(this.textInstructionsDI);</xsl:element>
                        <xsl:element name="Code">IATInstructionScreen.prototype.Execute.call(this);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATMockItemInstructionScreen'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATInstructionScreen'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">continueChar</xsl:element>
                    <xsl:element name="Param">continueInstructionsDI</xsl:element>
                    <xsl:element name="Param">leftResponseDI</xsl:element>
                    <xsl:element name="Param">rightResponseDI</xsl:element>
                    <xsl:element name="Param">stimulusDI</xsl:element>
                    <xsl:element name="Param">instructionsDI</xsl:element>
                    <xsl:element name="Param">errorMarked</xsl:element>
                    <xsl:element name="Param">outlineLeftResponse</xsl:element>
                    <xsl:element name="Param">outlineRightResponse</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">IATInstructionScreen.call(this, continueChar, continueInstructionsDI);</xsl:element>
                    <xsl:element name="Code">this.leftResponseDI = leftResponseDI;</xsl:element>
                    <xsl:element name="Code">this.rightResponseDI = rightResponseDI;</xsl:element>
                    <xsl:element name="Code">this.stimulusDI = stimulusDI;</xsl:element>
                    <xsl:element name="Code">this.instructionsDI = instructionsDI;</xsl:element>
                    <xsl:element name="Code">this.errorMarked = errorMarked;</xsl:element>
                    <xsl:element name="Code">this.outlineLeftResponse = outlineLeftResponse;</xsl:element>
                    <xsl:element name="Code">this.outlineRightResponse = outlineRightResponse;</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">if (this.outlineLeftResponse)</xsl:element>
                        <xsl:element name="Code">this.leftResponseDI.Outline();</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.leftResponseDI);</xsl:element>
                        <xsl:element name="Code">if (this.outlineRightResponse)</xsl:element>
                        <xsl:element name="Code">this.rightResponseDI.Outline();</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.rightResponseDI);</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.stimulusDI);</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.instructionsDI);</xsl:element>
                        <xsl:element name="Code">if (this.errorMarked)</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(ErrorMark);</xsl:element>
                        <xsl:element name="Code">IATInstructionScreen.prototype.Execute.call(this);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATKeyedInstructionScreen'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'yes'" />
                <xsl:value-of select="'IATInstructionScreen'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">continueChar</xsl:element>
                    <xsl:element name="Param">continueInstructionsDI</xsl:element>
                    <xsl:element name="Param">instructionsDI</xsl:element>
                    <xsl:element name="Param">leftResponseDI</xsl:element>
                    <xsl:element name="Param">rightResponseDI</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">IATInstructionScreen.call(this, continueChar, continueInstructionsDI);</xsl:element>
                    <xsl:element name="Code">this.instructionsDI = instructionsDI;</xsl:element>
                    <xsl:element name="Code">this.leftResponseDI = leftResponseDI;</xsl:element>
                    <xsl:element name="Code">this.rightResponseDI = rightResponseDI;</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'Execute'"/>
                    <xsl:element name="Params"/>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">Display.AddDisplayItem(this.instructionsDI);</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.leftResponseDI);</xsl:element>
                        <xsl:element name="Code">Display.AddDisplayItem(this.rightResponseDI);</xsl:element>
                        <xsl:element name="Code">IATInstructionScreen.prototype.Execute.call(this);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATBlock'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'no'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">blockNum</xsl:element>
                    <xsl:element name="Param">blockPosition</xsl:element>
                    <xsl:element name="Param">numPresentations</xsl:element>
                    <xsl:element name="Param">alternatedWith</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">this.blockNum = blockNum;</xsl:element>
                    <xsl:element name="Code">this.blockPosition = blockPosition;</xsl:element>
                    <xsl:element name="Code">this.numPresentations = numPresentations;</xsl:element>
                    <xsl:element name="Code">this.alternatedWith = alternatedWith;</xsl:element>
                    <xsl:element name="Code">this.BeginBlockEvent = null;</xsl:element>
                    <xsl:element name="Code">this.EndBlockEvent = null;</xsl:element>
                    <xsl:element name="Code">this.Items = new Array();</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'AddItem'"/>
                    <xsl:element name="Params">
                        <xsl:element name="Param">item</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.Items.push(item);</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'setBeginBlockEvent'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">event</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.BeginBlockEvent = event;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'setEndBlockEvent'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">event</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">this.EndBlockEvent = event;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getNumItems'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.Items.length;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getItem'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">ndx</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.Items[ndx];</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getBeginBlockEvent'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.BeginBlockEvent;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getEndBlockEvent'" />
                    <xsl:element name="Params" />
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.EndBlockEvent;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'GenerateContents'"/>
                    <xsl:element name="Params">
                        <xsl:element name="Param">randomization</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">var result = new Array();</xsl:element>
                        <xsl:element name="Code">result.push(this.BeginBlockEvent);</xsl:element>
                        <xsl:element name="Code">var ctr;</xsl:element>
                        <xsl:element name="Code">var currItemNdx, lastItemNdx = -1;</xsl:element>
                        <xsl:element name="Code">if (randomization == "None") {</xsl:element>
                        <xsl:element name="Code">for (ctr = 0; ctr &lt; Items.length; ctr++)</xsl:element>
                        <xsl:element name="Code">result.push(this.Items[ctr]);</xsl:element>
                        <xsl:element name="Code">} else if (randomization == "RandomOrder") {</xsl:element>
                        <xsl:element name="Code">var tempItems = new Array();</xsl:element>
                        <xsl:element name="Code">for (ctr = 0; ctr &lt; this.Items.length; ctr++)</xsl:element>
                        <xsl:element name="Code">tempItems.push(this.Items[ctr]);</xsl:element>
                        <xsl:element name="Code">for (ctr = 0; ctr &lt; this.Items.length; ctr++) {</xsl:element>
                        <xsl:element name="Code">var ndx = Math.floor(Math.random() * tempItems.length);</xsl:element>
                        <xsl:element name="Code">result.push(tempItems[ndx]);</xsl:element>
                        <xsl:element name="Code">tempItems.splice(ndx, 1);</xsl:element>
                        <xsl:element name="Code">}</xsl:element>
                        <xsl:element name="Code">} else if (randomization == "SetNumberOfPresentations") {</xsl:element>
                        <xsl:element name="Code">for (ctr = 0; ctr &lt; this.numPresentations; ctr++) {</xsl:element>
                        <xsl:element name="Code">currItemNdx = Math.floor(Math.random() * this.Items.length);</xsl:element>
                        <xsl:element name="Code">while (currItemNdx == lastItemNdx)</xsl:element>
                        <xsl:element name="Code">currItemNdx = Math.floor(Math.random() * this.Items.length);</xsl:element>
                        <xsl:element name="Code">result.push(this.Items[currItemNdx]);</xsl:element>
                        <xsl:element name="Code">lastItemNdx = currItemNdx;</xsl:element>
                        <xsl:element name="Code">}</xsl:element>
                        <xsl:element name="Code">}</xsl:element>
                        <xsl:element name="Code">result.push(this.EndBlockEvent);</xsl:element>
                        <xsl:element name="Code">return result;</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="'IATInstructionBlock'"/>
            <xsl:element name="Super">
                <xsl:attribute name="Has" select="'no'" />
            </xsl:element>
            <xsl:element name="Constructor">
                <xsl:element name="Params">
                    <xsl:element name="Param">alternatedWith</xsl:element>
                    <xsl:element name="Param">blockPosition</xsl:element>
                </xsl:element>
                <xsl:variable name="constructorBodyElems">
                    <xsl:element name="Code">this.alternatedWith = alternatedWith;</xsl:element>
                    <xsl:element name="Code">this.blockPosition = blockPosition;</xsl:element>
                    <xsl:element name="Code">this.screens = new Array();</xsl:element>
                    <xsl:element name="Code">return this;</xsl:element>
                </xsl:variable>
                <xsl:element name="ConstructorBody">
                    <xsl:for-each select="$constructorBodyElems/Code">
                        <xsl:element name="Code">
                            <xsl:attribute name="LineNum" select="position()"/>
                            <xsl:value-of select="."/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

            <xsl:element name="PrototypeChain">
                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getScreen'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">ndx</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.screens[ndx];</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'getNumScreens'" />
                    <xsl:element name="Params">
                        <xsl:element name="Param">ndx</xsl:element>
                    </xsl:element>
                    <xsl:element name="FunctionBody">
                        <xsl:element name="Code">return this.screens.length;</xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="Function">
                    <xsl:attribute name="FunctionName" select="'AddScreen'"/>
                    <xsl:element name="Params">
                        <xsl:element name="Param">screenType</xsl:element>
                        <xsl:element name="Param">ctorArgAry</xsl:element>
                    </xsl:element>
                    <xsl:variable name="functionBodyElems">
                        <xsl:element name="Code">var screen;</xsl:element>
                        <xsl:element name="Code">if (screenType == "Text")</xsl:element>
                        <xsl:element name="Code">screen = new IATTextInstructionScreen(ctorArgAry[0], ctorArgAry[1], ctorArgAry[2]);</xsl:element>
                        <xsl:element name="Code">else if (screenType == "Keyed")</xsl:element>
                        <xsl:element name="Code">screen = new IATKeyedInstructionScreen(ctorArgAry[0], ctorArgAry[1], ctorArgAry[2], ctorArgAry[3], ctorArgAry[4]);</xsl:element>
                        <xsl:element name="Code">else</xsl:element>
                        <xsl:element name="Code">screen = new IATMockItemInstructionScreen(ctorArgAry[0], ctorArgAry[1], ctorArgAry[2], ctorArgAry[3], ctorArgAry[4], ctorArgAry[5], ctorArgAry[6], ctorArgAry[7], ctorArgAry[8]);</xsl:element>
                        <xsl:element name="Code">this.screens.push(screen);</xsl:element>
                    </xsl:variable>
                    <xsl:element name="FunctionBody">
                        <xsl:for-each select="$functionBodyElems/Code">
                            <xsl:element name="Code">
                                <xsl:attribute name="LineNum" select="position()"/>
                                <xsl:value-of select="."/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>
    </xsl:variable>

    <xsl:variable name="processItemFunctions" >
        <xsl:call-template name="GenerateProcessItemFunctions" />
    </xsl:variable>


    <xsl:variable name="Functions">

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'BeginIAT'"/>
            <xsl:element name="Params">
                <xsl:element name="Param">event</xsl:element>
            </xsl:element>
            <xsl:variable name="functionBodyElems">
                <xsl:element name="Code">Display = new IATDisplay();</xsl:element>
                <xsl:element name="Code">event.stopPropagation();</xsl:element>
                <xsl:element name="Code">EventUtil.removeHandler(window, "click", BeginIAT);</xsl:element>
                <xsl:element name="Code">var dDiv = null;</xsl:element>
                <xsl:element name="Code">dDiv = Display.getDivTag();</xsl:element>
                <xsl:element name="Code">EventUtil.removeHandler(dDiv, "click", BeginIAT);</xsl:element>
                <xsl:element name="Code">while (dDiv.hasChildNodes())</xsl:element>
                <xsl:element name="Code">dDiv.removeChild(dDiv.firstChild);</xsl:element>
                <xsl:element name="Code">InitImages();</xsl:element>
                <xsl:element name="Code">GenerateEventList();</xsl:element>
                <xsl:element name="Code">EventCtr = 0;</xsl:element>
                <xsl:element name="Code">itemCtr = 0;</xsl:element>
                <xsl:element name="Code">EventList[0].Execute();</xsl:element>
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="$functionBodyElems/Code">
                    <xsl:element name="Code">
                        <xsl:attribute name="LineNum" select="position()"/>
                        <xsl:value-of select="."/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'InitImages'"/>
            <xsl:element name="Params"/>
            <xsl:variable name="functionBodyElems">
                <xsl:for-each select="//DisplayItemList/IATDisplayItem">
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('DI', ID, ' = new IATDI(', ID, ', img', ID, ', ', X, ', ', Y, ', ', Width, ', ', Height, ');')"/>
                    </xsl:element>
                </xsl:for-each>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('ErrorMark = DI', //ErrorMarkID, ';')"/>
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('ErrorMark.setImgTagID(DI', //ErrorMarkID, '.getImgTagID());')"/>
                </xsl:element>
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="$functionBodyElems/Code">
                    <xsl:element name="Code">
                        <xsl:attribute name="LineNum" select="position()"/>
                        <xsl:value-of select="."/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'GenerateEventList'" />
            <xsl:element name="Params" />
            <xsl:variable name="functionBodyElems">
                <xsl:element name="Code">EventCtr = 0;</xsl:element>
                <xsl:call-template name="GenerateEventInit" />
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="$functionBodyElems/Code">
                    <xsl:element name="Code">
                        <xsl:attribute name="LineNum" select="position()" />
                        <xsl:value-of select="." />
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:variable>

    <xsl:template match="ConfigFile">
        <xsl:element name="CodeFile">
            <xsl:attribute name="ElementName" select="IATName" />
            <xsl:element name="VarEntries">
                <xsl:copy-of select="$GlobalAbbreviations" />
            </xsl:element>
            <xsl:element name="Classes">
                <xsl:for-each select="$Classes/Class" >
                    <xsl:variable name="nodeName" select="name()" />
                    <xsl:element name="{$nodeName}">
                        <xsl:for-each select="attribute::*">
                            <xsl:copy-of select="." />
                        </xsl:for-each>
                        <xsl:attribute name="ClassPrefix" select="$classPrefix" />
                        <xsl:attribute name="ClassFunctionPrefix" select="$classFunctionPrefix" />
                        <xsl:copy-of select="child::*"/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="Functions">
                <xsl:for-each select="$processItemFunctions//Function">
                    <xsl:variable name="nodeName" select="name()" />
                    <xsl:element name="{$nodeName}">
                        <xsl:for-each select="attribute::*">
                            <xsl:copy-of select="." />
                        </xsl:for-each>
                        <xsl:attribute name="FunctionPrefix" select="$functionPrefix" />
                        <xsl:copy-of select="child::*" />
                    </xsl:element>
                </xsl:for-each>
                <xsl:for-each select="$Functions/Function">
                    <xsl:variable name="nodeName" select="name()" />
                    <xsl:element name="{$nodeName}">
                        <xsl:for-each select="attribute::*">
                            <xsl:copy-of select="."/>
                        </xsl:for-each>
                        <xsl:attribute name="FunctionPrefix" select="$functionPrefix" />
                        <xsl:copy-of select="child::*" />
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>


    <xsl:template name="MaskSpecifierArrayAppend">
        <xsl:param name="item"/>
        <xsl:param name="specifier" />
        <xsl:element name="Code">
            <xsl:value-of select="concat('KeyedDirInput = DynamicSpecValues[', $specifier/TestSpecifierID, '];')"/>
        </xsl:element>
        <xsl:if test="$item/KeyedDir eq 'DynamicLeft'">
            <xsl:element name="Code">if (KeyedDirInput == "True") {</xsl:element>
            <xsl:element name="Code">MaskItemTrueArray.push(new Array());</xsl:element>
            <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
            <xsl:element name="Code">} else {</xsl:element>
            <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
            <xsl:element name="Code">MaskItemFalseArray.push(new Array());</xsl:element>
            <xsl:element name="Code">}</xsl:element>
        </xsl:if>
        <xsl:if test="$item/KeyedDir eq 'DynamicRight'">
            <xsl:element name="Code">if (KeyedDirInput == "True") {</xsl:element>
            <xsl:element name="Code">MaskItemTrueArray.push(new Array());</xsl:element>
            <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
            <xsl:element name="Code">} else {</xsl:element>
            <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
            <xsl:element name="Code">MaskItemFalseArray.push(new Array());</xsl:element>
            <xsl:element name="Code">}</xsl:element>
        </xsl:if>
        <xsl:variable name="params"
                      select="concat('itemCtr++, DI', $item/StimulusDisplayID, ', KeyedDir, ', $item/ItemNum, ', ', $item/OriginatingBlock, ', ',  $item/BlockNum)"/>
        <xsl:element name="Code">if (KeyedDirInput == "True")</xsl:element>
        <xsl:element name="Code">
            <xsl:value-of select="concat('MaskItemTrueArray[MaskItemTrueArray.length - 1].push(new Array(', $params, '));')" />
        </xsl:element>
        <xsl:element name="Code">else</xsl:element>
        <xsl:element name="Code">
            <xsl:value-of select="concat('MaskItemFalseArray[MaskItemFalseArray.length - 1].push(new Array(', $params, '));')"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="MaskSpecifierArrayAppendRange">
        <xsl:param name="items"/>
        <xsl:param name="specifier" />
        <xsl:element name="Code">
            <xsl:value-of select="concat('KeyedDirInput = DynamicSpecValues[', $specifier/TestSpecifierID, '];')"/>
        </xsl:element>
        <xsl:for-each select="$items">
            <xsl:if test="KeyedDir eq 'DynamicLeft'">
                <xsl:element name="Code">if (KeyedDirInput == "True") {</xsl:element>
                <xsl:element name="Code">MaskItemTrueArray.push(new Array());</xsl:element>
                <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
                <xsl:element name="Code">} else {</xsl:element>
                <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
                <xsl:element name="Code">MaskItemFalseArray.push(new Array());</xsl:element>
                <xsl:element name="Code">}</xsl:element>
            </xsl:if>
            <xsl:if test="KeyedDir eq 'DynamicRight'">
                <xsl:element name="Code">if (KeyedDirInput == "True") {</xsl:element>
                <xsl:element name="Code">MaskItemTrueArray.push(new Array());</xsl:element>
                <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
                <xsl:element name="Code">} else {</xsl:element>
                <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
                <xsl:element name="Code">MaskItemFalseArray.push(new Array());</xsl:element>
                <xsl:element name="Code">}</xsl:element>
            </xsl:if>
            <xsl:variable name="params"
                          select="concat('itemCtr++, DI', StimulusDisplayID, ', KeyedDir, ', ItemNum, ', ', OriginatingBlock, ', ', BlockNum)"/>
            <xsl:element name="Code">if (KeyedDirInput == "True")</xsl:element>
            <xsl:element name="Code">
                <xsl:value-of select="concat('MaskItemTrueArray[MaskItemTrueArray.length - 1].push(new Array(', $params, '));')" />
            </xsl:element>
            <xsl:element name="Code">else</xsl:element>
            <xsl:element name="Code">
                <xsl:value-of select="concat('MaskItemFalseArray[MaskItemFalseArray.length - 1].push(new Array(', $params, '));')"/>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="GenerateEventInit">
        <xsl:element name="Code">var iatBlock, instructionBlock, IATBlocks = new Array(), InstructionBlocks = new Array(), NumItemsAry = new Array(), piFunctions = new Array(), pifAry, blockCtr, ctr, ctr2, ctr3, randomNum, sourceAry = 1, iatItem, lesserAry, greaterAry, bAlternate, itemBlockCtr, instructionBlockCtr, itemBlockOrder, instructionBlockOrder, ndx;</xsl:element>
        <xsl:element name="Code">bAlternate = (CookieUtil.get("Alternate") == "yes") ? true : false;</xsl:element>
        <xsl:for-each select="//EventList/BeginIATBlock">
            <xsl:variable name="blockPos" select="count(preceding-sibling::BeginIATBlock) + count(preceding-sibling::BeginInstructionBlock)" />
            <xsl:variable name="numItems" select="NumItems" />
            <xsl:variable name="blockItems" select="following-sibling::IATItem[position() le xs:integer($numItems)]" />
                <xsl:element name="Code">
                    <xsl:value-of select="concat('NumItemsAry.push(', NumPresentations, ');')" />
                </xsl:element>
            <xsl:element name="Code">pifAry = new Array();</xsl:element>
            <xsl:element name="Code">piFunctions.push(pifAry);</xsl:element>
            <xsl:variable name="blockNum" select="BlockNum" />
                <xsl:element name="Code">
                    <xsl:value-of select="concat('pifAry.push(PIF', $blockNum, ');')" />
                </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('iatBlock = new IATBlock(', BlockNum, ', ', $blockPos, ', ', NumPresentations, ', ', AlternatedWith, ');')"/>
                    </xsl:element>
            <xsl:element name="Code">
                <xsl:value-of select="concat('iatBlock.setBeginBlockEvent(new IATBeginBlock(', lower-case(./PracticeBlock), ', DI', ./LeftResponseDisplayID, ', DI', ./RightResponseDisplayID, ', DI', ./InstructionsDisplayID, '));')"/>
            </xsl:element>
            <xsl:element name="Code">IATBlocks.push(iatBlock);</xsl:element>
        </xsl:for-each>
        <xsl:element name="Code">
            <xsl:value-of select="concat('for (ctr = 0; ctr &lt; ', count(//EventList/child::BeginIATBlock), '; ctr++) {')" />
        </xsl:element>
        <xsl:choose>
            <xsl:when test="(//Is7Block eq 'True') and (//RandomizationType eq 'SetNumberOfPresentations')">
                <xsl:element name="Code">Items1 = new Array();</xsl:element>
                <xsl:element name="Code">Items2 = new Array();</xsl:element>
                <xsl:element name="Code">sourceAry = ((sourceAry == 2) || (ctr == 0)) ? 1 : 2;</xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:element name="Code">Items = new Array();</xsl:element>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:element name="Code">for (ctr2 = 0; ctr2 &lt; piFunctions[ctr].length; ctr2++)</xsl:element>
        <xsl:element name="Code">piFunctions[ctr][ctr2].call();</xsl:element>
        <xsl:element name="Code">if (Items1.length &lt; Items2.length) {</xsl:element>
        <xsl:element name="Code">lesserAry = Items1;</xsl:element>
        <xsl:element name="Code">greaterAry = Items2;</xsl:element>
        <xsl:element name="Code">} else {</xsl:element>
        <xsl:element name="Code">lesserAry = Items2;</xsl:element>
        <xsl:element name="Code">greaterAry = Items1;</xsl:element>
        <xsl:element name="Code">}</xsl:element>
        <xsl:element name="Code">for (ctr2 = 0; ctr2 &lt; NumItemsAry[ctr]; ctr2++) {</xsl:element>
                <xsl:element name="Code">if (lesserAry.length == 0)</xsl:element>
                <xsl:element name="Code">iatItem = greaterAry[Math.floor(Math.random() * greaterAry.length)];</xsl:element>
                <xsl:element name="Code">else {</xsl:element>
                <xsl:element name="Code">if (sourceAry == 1) {</xsl:element>
                <xsl:element name="Code">iatItem = Items1[Math.floor(Math.random() * Items1.length)];</xsl:element>
                <xsl:element name="Code">sourceAry = 2;</xsl:element>
                <xsl:element name="Code">} else {</xsl:element>
                <xsl:element name="Code">iatItem = Items2[Math.floor(Math.random() * Items2.length)];</xsl:element>
                <xsl:element name="Code">sourceAry = 1;</xsl:element>
                <xsl:element name="Code">}</xsl:element>
                <xsl:element name="Code">}</xsl:element>
        
        <xsl:element name="Code">IATBlocks[ctr].AddItem(iatItem);</xsl:element>
        <xsl:element name="Code">iatItem.BeginBlock = IATBlocks[ctr].BeginBlockEvent;</xsl:element>
        <xsl:element name="Code">}</xsl:element>
        <xsl:element name="Code">IATBlocks[ctr].setEndBlockEvent(new IATEndBlock());</xsl:element>
        <xsl:element name="Code">}</xsl:element>
        <xsl:for-each select="//EventList/child::*[name() eq 'BeginInstructionBlock']">
            <xsl:variable name="blockPosition" select="count(preceding-sibling::BeginInstructionBlock) + count(preceding-sibling::BeginIATBlock) + 1" />
            <xsl:variable name="numScreens" select="xs:integer(NumInstructionScreens)" />
            <xsl:element name="Code">
                <xsl:value-of select="concat('instructionBlock = new IATInstructionBlock(', AlternatedWith, ', ', $blockPosition, ');')" />
            </xsl:element>
            <xsl:for-each select="following-sibling::*[position() le $numScreens]">
                <xsl:choose>
                    <xsl:when test="name() eq 'TextInstructionScreen'">
                        <xsl:element name="Code">
                            <xsl:value-of select="concat('instructionBlock.AddScreen(&quot;Text&quot;, [', ContinueASCIIKeyCode, ', DI', ContinueInstructionsID, ', DI', InstructionsDisplayID, ']);')" />
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="name() eq 'KeyedInstructionScreen'">
                        <xsl:element name="Code">
                            <xsl:value-of select="concat('instructionBlock.AddScreen(&quot;Keyed&quot;, [', ContinueASCIIKeyCode, ', DI', ContinueInstructionsID, ', DI', InstructionsDisplayID, ', DI', LeftResponseDisplayID, ', DI', RightResponseDisplayID, ']);')" />
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="name() eq 'MockItemInstructionScreen'">
                        <xsl:element name="Code">
                            <xsl:value-of select="concat('instructionBlock.AddScreen(&quot;MockItem&quot;, [', ContinueASCIIKeyCode, ', DI', ContinueInstructionsID, ', DI', LeftResponseDisplayID, ', DI', RightResponseDisplayID, ', DI', StimulusDisplayID, ', DI', InstructionsDisplayID, ', ', lower-case(ErrorMarkIsDisplayed), ', ', lower-case(OutlineLeftResponse), ', ', lower-case(OutlineRightResponse), ']);')" />
                        </xsl:element>
                    </xsl:when>
                </xsl:choose>
            </xsl:for-each>
            <xsl:element name="Code">InstructionBlocks.push(instructionBlock);</xsl:element>
        </xsl:for-each>
        <xsl:element name="Code">
            <xsl:variable name="alternationValues" select="string-join(//EventList/child::BeginIATBlock/AlternatedWith, ', ')" />
            <xsl:value-of select="concat('itemBlockOrder = [', $alternationValues, '];')" />
        </xsl:element>
        <xsl:element name="Code">
            <xsl:variable name="alternationValues" select="string-join(//EventList/child::BeginInstructionBlock/AlternatedWith, ', ')" />
            <xsl:value-of select="concat('instructionBlockOrder = [', $alternationValues, '];')"/>
        </xsl:element>
        <xsl:variable name="EventList">
            <xsl:for-each select="//EventList/child::*[(name() eq 'BeginIATBlock') or (name() eq 'BeginInstructionBlock')]">
            <xsl:variable name="altWith" select="AlternatedWith" />
                <xsl:variable name="bType" select="@EventType" />
                <xsl:variable name="blockElems">
                    <xsl:copy-of select="." />
                    <xsl:if test="$bType eq 'BeginIATBlock'">
                        <xsl:for-each select="for $elem in following-sibling::IATEvent[(@EventType eq 'EndIATBlock') or (@EventType eq 'EndInstructionBlock')][1]/preceding-sibling::IATEvent return $elem">
                            <xsl:copy-of select="." />
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="$bType eq 'BeginInstructionBlock'">
                        <xsl:for-each select="following-sibling::IATEvent[position() le xs:integer(NumInstructionScreens)]">
                            <xsl:copy-of select="." />
                        </xsl:for-each>
                    </xsl:if>
                </xsl:variable>
                <xsl:element name="Block">
                    <xsl:attribute name="AlternatedWith" select="$altWith" />
                    <xsl:attribute name="BlockType" select="if ($bType eq 'BeginIATBlock') then 'IAT' else 'Instruction'" />
                    <xsl:copy-of select="$blockElems" />
                </xsl:element>
            </xsl:for-each>
        </xsl:variable>
        <xsl:for-each select="$EventList/Block">
            <xsl:variable name="blockType" select="@BlockType" />
            <xsl:variable name="ndx" select="count(preceding-sibling::Block[@BlockType eq $blockType])" />
            <xsl:if test="@AlternatedWith eq '-1'">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('ndx = ', $ndx, ';')"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@AlternatedWith ne '-1'">
                <xsl:element name="Code">if (!bAlternate)</xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('ndx = ', $ndx, ';')"/>
                </xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('ndx = ', xs:integer(@AlternatedWith) - 1, ';')"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@BlockType eq 'IAT'">
                <xsl:element name="Code">EventList.push(IATBlocks[ndx].getBeginBlockEvent());</xsl:element>
                <xsl:element name="Code">for (ctr2 = 0; ctr2 &lt; IATBlocks[ndx].getNumItems(); ctr2++)</xsl:element>
                <xsl:element name="Code">EventList.push(IATBlocks[ndx].getItem(ctr2));</xsl:element>
                <xsl:element name="Code">EventList.push(IATBlocks[ndx].getEndBlockEvent());</xsl:element>
            </xsl:if>
            <xsl:if test="@BlockType eq 'Instruction'">
                <xsl:element name="Code">for (ctr2 = 0; ctr2 &lt; InstructionBlocks[ndx].getNumScreens(); ctr2++)</xsl:element>
                <xsl:element name="Code">EventList.push(InstructionBlocks[ndx].getScreen(ctr2));</xsl:element>
            </xsl:if>
        </xsl:for-each>
        <xsl:element name="Code">EventList.push(new IATSubmitEvent());</xsl:element>
    </xsl:template>

    <xsl:template name="WriteVars">
        <xsl:param name="CodeLines" />
        <xsl:element name="Code">
            <xsl:value-of select="concat(string-join(for $i in 1 to count(CodeLines) return replace(CodeLines[$i], '(var(\s+)[A-Za-z_][A-Za-z0-9_]*)(.*)', '$1'), ', '), '&#x0A;')" />
        </xsl:element>
    </xsl:template>


    <xsl:template name="ProcessNoSpecItems" >
        <xsl:param name="items" />
        <xsl:element name="Code">Items1 = new Array();</xsl:element>
        <xsl:element name="Code">Items2 = new Array();</xsl:element>
        <xsl:for-each select="$items" >
            <xsl:variable name="params"
                          select="concat('EventCtr++, DI', ./StimulusDisplayID, ', ', ./ItemNum, ',  &quot;',  KeyedDir, '&quot;, ', ./BlockNum, ', iatBlocks')"/>
                    <xsl:if test="OriginatingBlock eq '0'">
                        <xsl:element name="Code">
                            <xsl:value-of select="concat('Items1.push(new IATItem(', $params, '));')"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="OriginatingBlock eq '1'">
                        <xsl:element name="Code">
                            <xsl:value-of select="concat('Items2.push(new IATItem(', $params, '));')"/>
                        </xsl:element>
                    </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="ProcessTrueFalseSpecItems" >
        <xsl:param name="items" />
        <xsl:variable name="specifier" select="//DynamicSpecifier[every $i in $items satisfies $i/SpecifierID eq TestSpecifierID]" />
        <xsl:element name="Code">var randomNum, TrueFalseAry = new Array();</xsl:element>
        <xsl:element name="Code">
            <xsl:value-of select="concat('KeyedDirInput = DynamicSpecValues[', $specifier/TestSpecifierID, '];')"/>
        </xsl:element>
        <xsl:for-each select="$items" >
            <xsl:if test="KeyedDir eq 'DynamicLeft'">
                <xsl:element name="Code">if (KeyedDirInput == "True")</xsl:element>
                <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
            </xsl:if>
            <xsl:if test="KeyedDir eq 'DynamicRight'">
                <xsl:element name="Code">if (KeyedDirInput == "False")</xsl:element>
                <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
            </xsl:if>
            <xsl:variable name="params" select="concat('EventCtr++, DI', StimulusDisplayID, ', ', ItemNum, ', KeyedDir, ', OriginatingBlock, ', ', BlockNum)" />
            <xsl:element name="Code">
                <xsl:value-of select="concat('TrueFalseAry.push(new Array(', $params, '));')" />
            </xsl:element>
        </xsl:for-each>
        <xsl:element name="Code">randomNum = Math.floor(Math.random() * TrueFalseAry.length);</xsl:element>
        <xsl:choose>
            <xsl:when test="(//Is7Block eq 'True') and (//RandomizationType eq 'SetNumberOfPresentations')" >
                <xsl:element name="Code">if (TrueFalseAry[randomNum][4] == 1)</xsl:element>
                <xsl:element name="Code">Items1.push(new IATItem(TrueFalseAry[randomNum][0], TrueFalseAry[randomNum][1], TrueFalseAry[randomNum][2], TrueFalseAry[randomNum][3], TrueFalseAry[randomNum][5]));</xsl:element>
                <xsl:element name="Code">if (TrueFalseAry[randomNum][4] == 2)</xsl:element>
                <xsl:element name="Code">Items2.push(new IATItem(TrueFalseAry[randomNum][0], TrueFalseAry[randomNum][1], TrueFalseAry[randomNum][2], TrueFalseAry[randomNum][3], TrueFalseAry[randomNum][5]));</xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:element name="Code">Items.push(new IATItem(TrueFalseAry[randomNum][0], TrueFalseAry[randomNum][1], TrueFalseAry[randomNum][2], TrueFalseAry[randomNum][3], TrueFalseAry[randomNum][5]));</xsl:element>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="ProcessRangeSpecItems" >
        <xsl:param name="items" />
        <xsl:variable name="specifier" select="//DynamicSpecifier[every $i in $items satisfies $i/SpecifierID eq TestSpecifierID]" />
        <xsl:element name="Code">
            <xsl:value-of select="concat('KeyedDirInput = DynamicSpecValues[', $specifier/TestSpecifierID, '];')"/>
        </xsl:element>
        <xsl:element name="Code">if (KeyedDirInput == "Exclude")</xsl:element>
        <xsl:element name="Code">return;</xsl:element>
        <xsl:element name="Code">var RangeItemAry = new Array();</xsl:element>
        <xsl:for-each select="$items">
            <xsl:if test="KeyedDir eq 'DynamicLeft'">
                <xsl:element name="Code">if (KeyedDirInput == "True")</xsl:element>
                <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
            </xsl:if>
            <xsl:if test="KeyedDir eq 'DynamicRight'">
                <xsl:element name="Code">if (KeyedDirInput == "False")</xsl:element>
                <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
            </xsl:if>
            <xsl:variable name="params">
                <xsl:value-of select="concat('itemCtr++, DI', StimulusDisplayID, ', ', ItemNum, ', KeyedDir, ', OriginatingBlock, ', ', BlockNum, ');')" />
            </xsl:variable>
            <xsl:element name="Code">
                <xsl:value-of select="concat('RangeItemAry.push(', $params, ');')"/>
            </xsl:element>
        </xsl:for-each>
        <xsl:element name="Code">var randomNum = Math.floor(Math.random() * RangeItemAry.length);</xsl:element>
        <xsl:choose>
            <xsl:when test="(//Is7Block eq 'True') and (//RandomizationType eq 'SetNumberOfPresentations')" >
                <xsl:element name="Code">if (RangeItemAry[randomNum][4] == 1)</xsl:element>
                <xsl:element name="Code">Items1.push(new IATItem(RangeItemAry[randomNum][0], RangeItemAry[randomNum][1], RangeItemAry[randomNum][2], RangeItemAry[randomNum][3], RangeItemAry[randomNum][5]));</xsl:element>
                <xsl:element name="Code">if (RangeItemAry[randomNum][4] == 2)</xsl:element>
                <xsl:element name="Code">Items2.push(new IATItem(RangeItemAry[randomNum][0], RangeItemAry[randomNum][1], RangeItemAry[randomNum][2], RangeItemAry[randomNum][3], RangeItemAry[randomNum][5]));</xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:element name="Code">Items.push(new IATItem(RangeItemAry[randomNum][0], RangeItemAry[randomNum][1], RangeItemAry[randomNum][3], RangeItemAry[randomNum][2], RangeItemAry[randomNum][5]));</xsl:element>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template name="ProcessSelectionSpecItems" >
        <xsl:param name="items" />
        <xsl:variable name="specifier" select="//DynamicSpecifier[every $i in $items satisfies $i/SpecifierID eq TestSpecifierID]" />
        <xsl:element name="Code">var SelectionStimulusArray = new Array(), ctr, lesser, lesserLen, ndx1, ndx2, itemBlock, SelectedItem;</xsl:element>
        <xsl:element name="Code">
            <xsl:value-of select="concat('SelectedItem = parseInt(DynamicSpecValues[', $specifier/TestSpecifierID, '], 10) - 1;')"/>
        </xsl:element>
        <xsl:element name="Code">var RandomItem = SelectedItem;</xsl:element>
        <xsl:for-each-group select="$items[SpecifierID eq $specifier/TestSpecifierID]" group-by="SpecifierArg" >
            <xsl:sort select="current-grouping-key()" order="ascending" />
            <xsl:variable name="choiceNum" select="position()" />
            <xsl:element name="Code">SelectionStimulusArray.push(new Array());</xsl:element>
            <xsl:if test="count(current-group()) eq 1" >
                <xsl:variable name="params"
                              select="concat(./SpecifierArg, ', DI', StimulusDisplayID, ', &quot;', KeyedDir, '&quot;, ', ItemNum, ', ', ./OriginatingBlock, ', ', BlockNum)"/>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('SelectionStimulusArray[', xs:integer($choiceNum) - 1, '].push(new Array(', $params, '));')" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="count(current-group()) gt 1" >
                <xsl:for-each select="current-group()" >
                    <xsl:variable name="params"
                                  select="concat(./SpecifierArg, ', DI', StimulusDisplayID, ', &quot;', KeyedDir, '&quot;, ', ItemNum, ', ', ./OriginatingBlock, ', ', BlockNum)"/>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('SelectionStimulusArray[', xs:integer($choiceNum) - 1, '].push(new Array(', $params, '));')" />
                    </xsl:element>
                </xsl:for-each>
            </xsl:if>
        </xsl:for-each-group>
        <xsl:element name="Code">RandomItem = SelectedItem;</xsl:element>
        <xsl:element name="Code">while (RandomItem == SelectedItem)</xsl:element>
        <xsl:element name="Code">RandomItem = Math.floor(Math.random() * SelectionStimulusArray.length);</xsl:element>
        <xsl:element name="Code">if (SelectionStimulusArray[RandomItem].length &gt; SelectionStimulusArray[SelectedItem].length) {</xsl:element>
        <xsl:element name="Code">lesser = SelectedItem;</xsl:element>
        <xsl:element name="Code">lesserLen = SelectionStimulusArray[SelectedItem].length;</xsl:element>
        <xsl:element name="Code">} else if (SelectionStimulusArray[RandomItem].length &lt;= SelectionStimulusArray[SelectedItem].length) {</xsl:element>
        <xsl:element name="Code">lesser = RandomItem;</xsl:element>
        <xsl:element name="Code">lesserLen = SelectionStimulusArray[RandomItem].length;</xsl:element>
        <xsl:element name="Code">}</xsl:element>
        <xsl:element name="Code">for (ctr = 0; ctr &lt; lesserLen; ctr++) {</xsl:element>
        <xsl:element name="Code">if (lesser == SelectedItem) {</xsl:element>
        <xsl:element name="Code">ndx1 = ctr;</xsl:element>
        <xsl:element name="Code">ndx2 = Math.floor(Math.random() * SelectionStimulusArray[RandomItem].length);</xsl:element>
        <xsl:element name="Code">} else {</xsl:element>
        <xsl:element name="Code">ndx1 = Math.floor(Math.random() * SelectionStimulusArray[SelectedItem].length);</xsl:element>
        <xsl:element name="Code">ndx2 = ctr;</xsl:element>
        <xsl:element name="Code">}</xsl:element>
        <xsl:choose>
            <xsl:when test="(//Is7Block eq 'True') and (//RandomizationType eq 'SetNumberOfPresentations')">
                <xsl:element name="Code">if (SelectionStimulusArray[SelectedItem][ndx1][4] == 1)</xsl:element>
                <xsl:element name="Code">itemBlock = Items1;</xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">itemBlock = Items2;</xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:element name="Code">itemBlock = Items;</xsl:element>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:element name="Code">if (SelectionStimulusArray[SelectedItem][ndx1][2] == "DynamicLeft")</xsl:element>
        <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
        <xsl:element name="Code">else</xsl:element>
        <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
        <xsl:element name="Code">itemBlock.push(new IATItem(EventCtr++, SelectionStimulusArray[SelectedItem][ndx1][1], SelectionStimulusArray[SelectedItem][ndx1][3], KeyedDir, SelectionStimulusArray[SelectedItem][ndx1][5]));</xsl:element>
        <xsl:element name="Code">if (SelectionStimulusArray[RandomItem][ndx2][2] == "DynamicLeft")</xsl:element>
        <xsl:element name="Code">KeyedDir = "Right";</xsl:element>
        <xsl:element name="Code">else</xsl:element>
        <xsl:element name="Code">KeyedDir = "Left";</xsl:element>
        <xsl:element name="Code">itemBlock.push(new IATItem(EventCtr++, SelectionStimulusArray[RandomItem][ndx2][1], SelectionStimulusArray[RandomItem][ndx2][3], KeyedDir, SelectionStimulusArray[SelectedItem][ndx1][5]));</xsl:element>
        <xsl:element name="Code">if (lesser == SelectedItem)</xsl:element>
        <xsl:element name="Code">SelectionStimulusArray[RandomItem].splice(ndx2, 1);</xsl:element>
        <xsl:element name="Code">else</xsl:element>
        <xsl:element name="Code">SelectionStimulusArray[SelectedItem].splice(ndx1, 1);</xsl:element>
        <xsl:element name="Code">}</xsl:element>
    </xsl:template>


    <xsl:template name="GenerateProcessItemFunctions">
        <xsl:for-each select="//EventList/BeginIATBlock">
            <xsl:variable name="i" select="position()" />
            <xsl:variable name="block" select="." />
            <xsl:element name="ProcessItemsFunctions">
                <xsl:attribute name="BlockNum" select="$i" />
                <xsl:variable name="items" select="following-sibling::IATItem[position() le xs:integer($block/NumItems)]" />
                <xsl:element name="Function">
                  <xsl:attribute name="FunctionName" select="concat('PIF', $i)" />
                  <xsl:element name="Params"/>
                  <xsl:element name="FunctionBody">
                    <xsl:call-template name="ProcessNoSpecItems">
                      <xsl:with-param name="items" select="$items" />
                    </xsl:call-template>
                  </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="OutputConstructorDefinition">
        <xsl:param name="class"/>
        <xsl:variable name="params" select="$class/Constructor/Params"/>
        <xsl:variable name="paramList" select="string-join($params/Param, ', ')"/>
        <xsl:element name="CodeLine">
            <xsl:attribute name="Type" select="'ConstructorStart'"/>
            <xsl:attribute name="Name" select="$class/@ClassName"/>
            <xsl:value-of select="concat('function ', $class/@ClassName, '(', $paramList, ') {')"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="OutputConstructorBody">
        <xsl:param name="class"/>
        <xsl:for-each select="$class/Constructor/ConstructorBody/Code">
            <xsl:element name="CodeLine">
                <xsl:attribute name="Type" select="'ConstructorCode'"/>
                <xsl:attribute name="Name" select="$class/@ClassName" />
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:for-each>
        <xsl:element name="CodeLine">
            <xsl:attribute name="Type" select="'ConstructorEnd'"/>
            <xsl:attribute name="Name" select="$class/@ClassName" />
            <xsl:value-of select="'}'"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="OutputMemberFunctionDefinition">
        <xsl:param name="function"/>
        <xsl:param name="className"/>
        <xsl:variable name="paramList" select="string-join($function/Params, ', ')"/>
        <xsl:element name="CodeLine">
            <xsl:attribute name="Type" select="'FunctionStart'"/>
            <xsl:attribute name="Name" select="concat($className, '.', $function/@FunctionName)"/>
            <xsl:value-of select="concat($className, '.prototype.', $function/@FunctionName, ' = function(', $paramList, ') {')"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="OutputFunctionDefinition">
        <xsl:param name="function"/>
        <xsl:variable name="params" select="$function/Params"/>
        <xsl:variable name="paramList" select="string-join($function/Params/Param, ', ')" />
        <xsl:element name="CodeLine">
            <xsl:attribute name="Type" select="'FunctionStart'"/>
            <xsl:attribute name="Name" select="$function/@FunctionName"/>
            <xsl:value-of select="concat('function ', $function/@FunctionName, '(', $paramList, ') {')"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="OutputFunctionBody" match="FunctionBody[not(DynamicSpecLoad)]" >
        <xsl:for-each select="Code">
            <xsl:element name="CodeLine">
                <xsl:attribute name="Type" select="'FunctionCode'"/>
                <xsl:value-of select="concat(., '&#x0A;')"/>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="OutputDynamicSpecFunctionBody" match="FunctionBody[DynamicSpecLoad]" >
        <xsl:for-each select="DynamicSpecLoad">
            <xsl:element name="DynamicSpecLoad">
                <xsl:for-each select="Code">
                    <xsl:element name="CodeLine">
                        <xsl:attribute name="Type" select="'FunctionCode'"/>
                        <xsl:value-of select="."/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>


    <xsl:template name="OutputPrototypeChain">
        <xsl:param name="class"/>
        <xsl:variable name="prototype" select="$class/PrototypeChain"/>
        <xsl:element name="PrototypeChain">
            <xsl:attribute name="NumFunctions" select="count($prototype/Function) + 1" />
            <xsl:element name="CodeLine">
                <xsl:attribute name="Type" select="'ConstructorDefinition'"/>
                <xsl:attribute name="Name" select="$class/@ClassName"/>
                <xsl:value-of select="concat($class/@ClassName, '.prototype.constructor = ', $class/@ClassName, ';')"/>
            </xsl:element>
            <xsl:for-each select="$class/PrototypeChain/Function">
                <xsl:element name="MemberFunction">
                    <xsl:attribute name="NumLines" select="count(FunctionBody/Code) + 2" />
                    <xsl:call-template name="OutputMemberFunctionDefinition">
                        <xsl:with-param name="function" select="."/>
                        <xsl:with-param name="className" select="$class/@ClassName"/>
                    </xsl:call-template>
                    <xsl:apply-templates select="FunctionBody" />
                    <xsl:element name="CodeLine">
                        <xsl:attribute name="Type" select="'FunctionEnd'"/>
                        <xsl:attribute name="Name" select="concat($class/@ClassName, '.', @FunctionName)"/>
                        <xsl:value-of select="'};'"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template name="OutputClass">
        <xsl:param name="class"/>
        <xsl:element name="Class">
            <xsl:attribute name="ClassName" select="$class/@ClassName" />
            <xsl:element name="Constructor">
                <xsl:attribute name="NumLines" select="count($class/Constructor/ConstructorBody/Code) + 2" />
                <xsl:call-template name="OutputConstructorDefinition">
                    <xsl:with-param name="class" select="$class"/>
                </xsl:call-template>
                <xsl:call-template name="OutputConstructorBody">
                    <xsl:with-param name="class" select="$class"/>
                </xsl:call-template>
            </xsl:element>
            <xsl:call-template name="OutputPrototypeChain">
                <xsl:with-param name="class" select="$class"/>
            </xsl:call-template>
        </xsl:element>
    </xsl:template>

    <xsl:template name="OutputFunction">
        <xsl:param name="function"/>
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="$function/@FunctionName" />
            <xsl:attribute name="NumLines" select="count($function/FunctionBody/Code) + 2" />
            <xsl:call-template name="OutputFunctionDefinition">
                <xsl:with-param name="function" select="."/>
            </xsl:call-template>
            <xsl:apply-templates select="$function/FunctionBody" />
            <xsl:element name="CodeLine">
                <xsl:attribute name="Type" select="'FunctionEnd'"/>
                <xsl:attribute name="Name" select="$function/@FunctionName" />
                <xsl:value-of select="'}'"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>