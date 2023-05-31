<xsl:stylesheet
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                                                 exclude-result-prefixes="xs">
    
    <xsl:output method="xml" encoding="UTF-8" indent="yes" />
    
    <xsl:variable name="root" select="/" />
    
    <xsl:variable name="functionPrefix">
        <xsl:value-of select="'svF'" />
    </xsl:variable>
    
    <xsl:variable name="globalVariablePrefix">
        <xsl:value-of select="'svG'"/>
    </xsl:variable>
    
    <xsl:variable name="globalCodePrefix">
        <xsl:value-of select="'svGC'" />
    </xsl:variable>
    
    <xsl:variable name="Functions">
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'addPostDataItem'" />
            <xsl:attribute name="FunctionType" select="'global'" />
            <xsl:element name="Params">
                <Param>name</Param>
                <Param>value</Param>
            </xsl:element>
            <xsl:element name="FunctionBody">
                <Code>var form = document.querySelector("form#SurveyForm");</Code>
                <Code>var formData = new FormData(form);</Code>
                <Code>formData.set(name, value);</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'addAdditionalPostData'" />
            <xsl:attribute name="FunctionType" select="'global'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <Code>addPostDataItem("IATSESSIONID", sessionStorage.getItem("IATSESSIONID"));</Code>
                <Code>addPostDataItem("TestSegment", sessionStorage.getItem("TestSegment"));</Code>
                <Code>addPostDataItem("corrupted", sessionStorage.getItem("corrupted"));</Code>
                <Code>addPostDataItem("target", "adminV2");</Code>
                <Code>addPostDataItem("HTTP_REFERER", sessionStorage.getItem("HTTP_REFERER"));</Code>
                <Code><xsl:value-of select="concat('int numItems = ', count($root//SurveyItem[not(@Image)]) - count($root//SurveyItem/Response[@Type ne 'Instruction']))" /></Code>
                <Code>addPostDataItem("NumItems", numItems.toString());</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('addPostDataItem(&quot;numItems&quot;, &quot;', count($root//SurveyItem[not(@Image)]/Response[@Type ne 'Instruction']), '&quot;);')" />
                </xsl:element>
                <Code>if (sessionStorage.getItem("LastAdminPhase") == "true")</Code>
                <Code>sessionStorage.clear();</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'isNumber'" />
            <xsl:attribute name="FunctionType" select="'global'" />
            <xsl:element name="Params">
                <Param>n</Param>
            </xsl:element>
            <xsl:element name="FunctionBody">
                <Code>var exp = /^[1-9]?[0-9]*?(0?\.?[0-9]+)$/;</Code>
                <Code>return exp.test(n);</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:if test="$root//Survey/@UniqueResponseItem ne '-1'">
            <xsl:element name="Function">
                <xsl:attribute name="FunctionName" select="'checkUniqueResponseAjaxCallback'" />
                <xsl:attribute name="FunctionType" select="'global'" />
                <xsl:element name="Params">
                    <Param>ajax</Param>
                </xsl:element>
                <xsl:element name="FunctionBody">
                    <Code>uniqueResponseStatus = ajax;</Code>
                    <Code>var errorMsg = "";</Code>
                    <Code>if (uniqueResponseStatus === "taken")</Code>
                    <Code>errorMsg = "This value has been taken by a user who has not completed the test. Please enter a different value or try again in an hour.";</Code>
                    <Code>if (uniqueResponseStatus === "consumed")</Code>
                    <Code>errorMsg = "This value has alreay been used.";</Code>
                    <Code>if (uniqueResponseStatus === "invalid")</Code>
                    <Code>errorMsg = "This is not a valid response.";</Code>
                    <Code>if (uniqueResponseStatus === "abort")</Code>
                    <Code>AbortTest();</Code>
                    <Code>if (errorMsg !== "") {</Code>
                    <Code>var errorMsgElem = document.getElementById("uniqueResponseErrorMsg");</Code>
                    <Code>if (errorMsgElem === null) {</Code>
                    <Code>errorMsgElem = document.createElement("p");</Code>
                    <Code>errorMsgElem.id = "uniqueResponseErrorMsg";</Code>
                    <Code>errorMsgElem.className = "Error";</Code>
                    <xsl:variable name="itemNum" select="count($root//SurveyItem[Response/@Type ne 'Instruction'][xs:integer($root//Survey/@UniqueResponseItem)]/preceding-sibling::SurveyItem) + 1" />
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('document.getElementById(&quot;ItemLITag', $itemNum, '&quot;).appendChild(errorMsgElem);')" />
                    </xsl:element>
                    <Code>}</Code>
                    <Code>errorMsgElem.innerText = errorMsg;</Code>
                    <Code>} else if (document.getElementById("uniqueResponseErrorMsg"))</Code>
                    <Code>document.getElementById("uniqueResponseErrorMsg").innerText = "";</Code>
                    <Code>if (uniqueResponseStatus === "success")</Code>
                    <Code>document.getElementById("SubmitButton").enabled = true;</Code>
                </xsl:element>
            </xsl:element>
            
            <xsl:element name="Function">
                <xsl:attribute name="FunctionName" select="'CheckUniqueResponse'" />
                <xsl:attribute name="FunctionType" select="'global'" />
                <xsl:element name="Params">
                    <Param>evt</Param>
                </xsl:element>
                <xsl:element name="FunctionBody">
                    <Code>document.getElementById("SubmitButton").enabled = false;</Code>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('var requestSrc = window.location.protocol + &quot;//&quot; + window.location.hostname + (window.location.port ? &quot;:&quot; + window.location.port.toString() : &quot;&quot;) + &quot;', $root//ServerPath, '&quot; + &quot;/', //ClientID, '/', //IATName, '/', //Survey/@FileName, '.html&quot;;&#x0A;')"/>
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('var requestURL = &quot;', $root//ServerPath, '/Admin/Ajax/VerifyUniqueResponse&quot;;')" />
                    </xsl:element>
                    <Code>evt.target.value = evt.target.value.trim();</Code>
                    <Code>var ajax = new AjaxCallv2(requestURL, requestSrc, sessionStorage.getItem("TestSegment"), "text/plain");</Code>
                    <Code>ajax.call(checkUniqueResponseAjaxCallback, null, "POST", evt.target.value, "text/plain");</Code>
                </xsl:element>
            </xsl:element>
        </xsl:if>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'validateSurvey'" />
            <xsl:attribute name="FunctionType" select="'global'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <Code>var nErrors = 0;</Code>
                <Code>var ctr = 0;</Code>
                <Code>while (ctr &lt; questionListNode.childNodes.length) {</Code>
                <Code>if (questionListNode.childNodes[ctr].className == "Error")</Code>
                <Code>questionListNode.removeChild(questionListNode.childNodes[ctr]);</Code>
                <Code>else</Code>
                <Code>ctr++;</Code>
                <Code>}</Code>
                <Code>nErrors = 0;</Code>
                <xsl:if test="//Survey/@UniqueResponseItem ne '-1'">
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('var uniqueNum = ', //Survey/@UniqueResponseItem, ';')"/>
                    </xsl:element>
                </xsl:if>
                <Code>var itemErrors;</Code>
                <xsl:for-each select="for $n in 1 to count(//SurveyItem[Response/@Type ne 'Instruction']) return $n">
                    <xsl:variable name="ctr" select="." />
                    <xsl:variable name="ndx" select="count($root//SurveyItem[Response/@Type ne 'Instruction'][$ctr]/preceding-sibling::SurveyItem) + 1" />
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('itemErrors = validateFunctions[', xs:integer($ctr) - 1, '].call();')" />
                    </xsl:element>
                    <xsl:if test="$root//SurveyItem[$ndx]/@Optional eq 'True'">
                        <Code>if (itemErrors === -1) {</Code>
                        <xsl:element name="Code">
                            <xsl:value-of select="concat('questionElems[', xs:integer($ctr) - 1, '].style.display = &quot;none&quot;;')" />
                        </xsl:element>
                        <Code>itemErrors = 0;</Code>
                        <Code>} else if (itemErrors != 0)</Code>
                    </xsl:if>
                    <xsl:if test="$root//SurveyItem[$ndx]/@Optional eq 'False'">
                        <Code>if (itemErrors != 0)</Code>
                    </xsl:if>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('initFunctions[', xs:integer($ctr) - 1, '].call();')"/>
                    </xsl:element>
                    <xsl:if test="$root//Survey/@UniqueResponseItem ne '-1'">
                        <xsl:element name="Code">
                            <xsl:value-of select="'else if (uniqueNum == ', $ctr, ') {'" />
                        </xsl:element>
                        <Code>if (uniqueResponseStatus !== "success")</Code>
                        <Code>nErrors++;</Code>
                        <Code>}</Code>
                    </xsl:if>
                    <Code>nErrors += itemErrors;</Code>
                </xsl:for-each>
                <Code>return nErrors;</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnSubmit'" />
            <xsl:attribute name="FunctionType" select="'global'" />
            <xsl:element name="Params">
                <Param>event</Param>
            </xsl:element>
            <xsl:element name="FunctionBody">
                <Code>var form = document.querySelector("form#SurveyForm");</Code>
                <Code>var formData = new FormData(form);</Code>
                <Code>if (IsAborting) {</Code>
                <Code>addAdditionalPostData();</Code>
                <Code>formData.set("ABORT", "true");</Code>
                <Code>submitted = true;</Code>
                <Code>return;</Code>
                <Code>}</Code>
                <Code>if (ForceSubmit == true) {</Code>
                <Code>document.getElementById("SubmitButton").disabled = true;</Code>
                <Code>addAdditionalPostData();</Code>
                <Code>submitted = true;</Code>
                <Code>return;</Code>
                <Code>}</Code>
                <Code>var nErrors = validateSurvey();</Code>
                <Code>if (nErrors &gt; 0) {</Code>
                <Code>var errorsExist = document.querySelector("h3#ErrorsExistDiv");</Code>
                <Code>errorsExist.appendChild(document.createTextNode("Response errors detected. Please review the above survey for error messages and then resubmit."));</Code>
                <Code>}</Code>
                <Code>EventUtil.preventDefault(event);</Code>
                <Code>} else {</Code>
                <Code>var submitButton = document.getElementById("SubmitButton");</Code>
                <Code>addAdditionalPostData();</Code>
                <Code>submitted = true;</Code>
                <Code>submitButton.disabled = true;</Code>
                <Code>}</Code>
            </xsl:element>
        </xsl:element>
        <xsl:if test="xs:integer(//Survey/@TimeoutMillis) ne 0">
            <xsl:element name="Function">
                <xsl:attribute name="FunctionName" select="'DoForceSubmit'" />
                <xsl:attribute name="FunctionType" select="'global'" />
                <xsl:element name="Params" />
                <xsl:element name="FunctionBody">
                    <Code>ForceSubmit = true;</Code>
                    <Code>validateSurvey();</Code>
                    <Code>document.getElementById("SurveyForm").submit();</Code>
                </xsl:element>
            </xsl:element>
        </xsl:if>
        <xsl:apply-templates select="//SurveyItem" />
    </xsl:variable>
    
    <xsl:variable name="VariableDeclarations">
        <Declarations>
            <Declaration>var ForceSubmit = false;</Declaration>
            <Declaration>var questionListNode = document.getElementById("QuestionList");</Declaration>
            <Declaration>var initFunctions = new Array();</Declaration>
            <Declaration>var validateFunctions = new Array();</Declaration>
            <Declaration>var form = document.getElementById("SurveyForm");</Declaration>
            <Declaration>var uniqueRespErrorLI = null;</Declaration>
            <xsl:variable name="questionNdxs">
                <xsl:for-each select="for $n in 1 to count(//SurveyItem[Response/@Type ne 'Instruction']) return $n">
                    <xsl:variable name="ctr" select="." />
                    <xsl:variable name="ndx" select="count($root//SurveyItem[Response/@Type ne 'Instruction'][$ctr]/preceding-sibling::SurveyItem) + 1" />
                    <xsl:element name="elem">
                        <xsl:value-of select="concat('document.getElementById(&quot;ItemLITag', $ndx, '&quot;)') "/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:variable>
            <Declaration>
                <xsl:value-of select="concat('var questionElems = [', string-join($questionNdxs/elem, ', '), '];')" />
            </Declaration>
        </Declarations>
    </xsl:variable>
    
    
    <xsl:variable name="GlobalAbbreviations">
        <xsl:for-each select="$VariableDeclarations/Declarations/Declaration">
            <xsl:variable name="pos" select="position()" />
            <xsl:analyze-string select="." regex="([A-Za-z_][A-Za-z0-9_]*)(\s*=\s*)(.+);$">
                <xsl:matching-substring>
                    <xsl:element name="Entry">
                        <xsl:attribute name="type" select="'global'" />
                        <xsl:element name="OrigName">
                            <xsl:value-of select="regex-group(1)" />
                        </xsl:element>
                        <xsl:element name="NewName">
                            <xsl:value-of select="concat('_', $globalVariablePrefix, $pos)" />
                        </xsl:element>
                        <xsl:element name="Assign">
                            <xsl:value-of select="normalize-space(regex-group(3))" />
                        </xsl:element>
                    </xsl:element>
                </xsl:matching-substring>
            </xsl:analyze-string>
        </xsl:for-each>
    </xsl:variable>
    
    <xsl:variable name="GlobalCode">
        <xsl:if test="//Survey/@TimeoutMillis ne '0'">
            <xsl:element name="Code">
                <xsl:value-of select="concat('setTimeout(DoForceSubmit, ', //Survey/@TimeoutMillis, ');')"/>
            </xsl:element>
        </xsl:if>
        <xsl:for-each select="for $i in 1 to count(//SurveyItem/Response[@Type ne 'Instruction']) return $i">
            <xsl:element name="Code">
                <xsl:value-of select="concat('initFunctions.push(InitializeItem', ., ');')" />
            </xsl:element>
            <xsl:element name="Code">
                <xsl:value-of select="concat('validateFunctions.push(ValidateItem', ., ');')" />
            </xsl:element>
        </xsl:for-each>
    </xsl:variable>
    
    <xsl:template match="Survey">
        <xsl:element name="CodeFile">
            <xsl:element name="VarEntries">
                <xsl:copy-of select="$GlobalAbbreviations"/>
            </xsl:element>
            <xsl:element name="Functions">
                <xsl:for-each select="$Functions/Function">
                    <xsl:variable name="nodeName" select="name()" />
                    <xsl:element name="{$nodeName}">
                        <xsl:for-each select="attribute::*">
                            <xsl:copy-of select="." />
                        </xsl:for-each>
                        <xsl:attribute name="FunctionPrefix" select="$functionPrefix" />
                        <xsl:copy-of select="child::*" />
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="GlobalCode">
                <xsl:attribute name="CodePrefix" select="$globalCodePrefix" />
                <xsl:copy-of select="$GlobalCode" />
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="SurveyItem">
        <xsl:variable name="precedingNodes" select="preceding-sibling::node()" />
        <xsl:variable name="precedingSurveyItems" select="$precedingNodes[compare(name(), 'SurveyItem') eq 0]" />
        <xsl:variable name="itemNum" select="count($precedingSurveyItems/Response[compare(@Type, 'Instruction') ne 0]) + 1" />
        <xsl:if test="Response/@Type ne 'Instruction'" >
            <xsl:apply-templates select="Response">
                <xsl:with-param name="itemNum" as="xs:integer" select="$itemNum" />
                <xsl:with-param name="liNum" as="xs:integer" select="count($precedingSurveyItems) + 1" />
                <xsl:with-param name="optional" as="xs:string" select="@Optional" />
            </xsl:apply-templates>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Likert']">
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="concat('InitializeItem', $itemNum)" />
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>for (var ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>answerInputs[ctr].checked = false;</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>var ctr;</Code>
                <Code>var selectionMade = false;</Code>
                <Code>for (ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>if (answerInputs[ctr].checked)</Code>
                <Code>selectionMade = true;</Code>
                <Code>if (!selectionMade) {</Code>
                <xsl:if test="$optional eq 'True'">
                    <Code>answerInputs[0].checked = true;</Code>
                    <Code>answerInputs[0].value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                </xsl:if>
                <Code>if (!ForceSubmit) {</Code>
                <Code>var errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <Code>var errorMsg = document.createTextNode("Please select a response to the question below.");</Code>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>} else {</Code>
                <Code>answerInputs[0].checked = true;</Code>
                <Code>answerInputs[0].value = "__ForceSubmittedUnanswered__";</Code>
                <Code>}</Code>
                <Code>return 1;</Code>
                <Code>}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Boolean']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>for (var ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>answerInputs[ctr].checked = false;</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>var ctr = 0;</Code>
                <Code>var selectionMade = false;</Code>
                <Code>for (ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>if (answerInputs[ctr].checked)</Code>
                <Code>selectionMade = true;</Code>
                <Code>if (!selectionMade) {</Code>
                <xsl:if test="$optional eq 'True'">
                    <Code>answerInputs[0].checked = true;</Code>
                    <Code>answerInputs[0].value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                </xsl:if>
                <Code>if (!ForceSubmit) {</Code>
                <Code>var errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <Code>var errorMsg = document.createTextNode("Please select a response to the question below.");</Code>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>} else {</Code>
                <Code>answerInputs[0].checked = true;</Code>
                <Code>answerInputs[0].value = "__ForceSubmittedUnanswered__";</Code>
                <Code>}</Code>
                <Code>return 1;</Code>
                <Code>}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Multiple Choice']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>for (var ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>answerInputs[ctr].checked = false;</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>var ctr = 0;</Code>
                <Code>var selectionMade = false;</Code>
                <Code>for (ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>if (answerInputs[ctr].checked)</Code>
                <Code>selectionMade = true;</Code>
                <Code>if (!selectionMade) {</Code>
                <xsl:if test="$optional eq 'True'">
                    <Code>answerInputs[0].checked = true;</Code>
                    <Code>answerInputs[0].value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                </xsl:if>
                <xsl:if test="$optional ne 'True'">
                    <Code>if (!ForceSubmit) {</Code>
                    <Code>var errorMsgLI = document.createElement("li");</Code>
                    <Code>errorMsgLI.className = "Error";</Code>
                    <Code>var errorMsg = document.createTextNode("Please select a response to the question below.");</Code>
                    <Code>errorMsgLI.appendChild(errorMsg);</Code>
                    <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                    <Code>} else {</Code>
                    <Code>answerInputs[0].checked = true;</Code>
                    <Code>answerInputs[0].value = "__ForceSubmittedUnanswered__";</Code>
                    <Code>}</Code>
                    <Code>return 1;</Code>
                </xsl:if>
                <Code>}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    
    <xsl:template match="Response[@Type='Weighted Multiple Choice']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>for (var ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>answerInputs[ctr].checked = false;</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInputs = document.getElementsByName(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>var ctr = 0;</Code>
                <Code>var selectionMade = false;</Code>
                <Code>for (ctr = 0; ctr &lt; answerInputs.length; ctr++)</Code>
                <Code>if (answerInputs[ctr].checked)</Code>
                <Code>selectionMade = true;</Code>
                <Code>if (!selectionMade) {</Code>
                <xsl:if test="$optional eq 'True'">
                    <Code>answerInputs[0].checked = true;</Code>
                    <Code>answerInputs[0].value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                </xsl:if>
                <xsl:if test="$optional ne 'True'">
                    <Code>if (!ForceSubmit) {</Code>
                    <Code>var errorMsgLI = document.createElement("li");</Code>
                    <Code>errorMsgLI.className = "Error";</Code>
                    <Code>var errorMsg = document.createTextNode("Please select a response to the question below.");</Code>
                    <Code>errorMsgLI.appendChild(errorMsg);</Code>
                    <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                    <Code>} else {</Code>
                    <Code>answerInputs[0].checked = true;</Code>
                    <Code>answerInputs[0].value = "__ForceSubmittedUnanswered__";</Code>
                    <Code>}</Code>
                    <Code>return 1;</Code>
                </xsl:if>
                <Code>}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <!-- The template that generates the JavaScript to initialize and validate the 
        item takes the item number, the index of the <li> tag the item is contained in,
        and a boolen 'optional' value to indicate whether the question must be answered. -->
    <xsl:template match="Response[@Type='Multiple Selection']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <!-- For multiple selection items, the initialization function performs no work. -->
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody" />
        </xsl:element>
        
        <!-- This function will validate that the user has provided an appropriate
            response to the question. In the case of multiple selection items,
            a valid response consists of checking a prescribed minimum and maximum 
            number of choices. -->
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <!-- What follows is simplicistic JavaScript wrapped in XSLT logic. -->
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <Code>var ctr = 0, responseElement;</Code>
                <!-- A special response element with its own name is created to hold the answer, if it does not already
                    exist. -->
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var responseId = &quot;MultiSelectItem', $itemNum, '&quot;;')" />
                </xsl:element>
                <Code>responseElement = document.getElementById('responseId');</Code>
                <Code>if (responseElement === null) {</Code>
                <Code>responseElement = document.createElement("input");</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('responseElement.name = &quot;Item', $itemNum, '&quot;;')" />
                </xsl:element>
                <Code>responseElement.type = "hidden";</Code>
                <Code>responseElement.id = responseId;</Code>
                <Code>questionListNode.appendChild(responseElement);</Code>
                <Code>}</Code>
 
                <!-- The checkboxes are looped through. -->
                <Code>var nChecked = 0;</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('for (ctr = 0; ctr &lt; ', count(./Labels/Label), '; ctr++) {')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var selectionInput = document.getElementById(&quot;Item', $itemNum, 
                        '_&quot;.concat((ctr + 1).toString()));')" />
                </xsl:element>
                <!-- If the checkbox is checked, it is added to the tally. -->
                <Code>if (selectionInput.checked)</Code>
                <Code>nChecked++;</Code>
                <Code>}</Code>
                <Code>var errorMsgLI, errorMsg;</Code>
                <!-- If answering this item is optional, the value of the input is set to "__Unanswered__" and a -1
                    is returned to the calling validation function. -->
                <xsl:if test="$optional eq 'True'">
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('if ((nChecked === 0) &amp;&amp; (', ./MinSelections, ' &gt; 0)) {')" />
                    </xsl:element>
                    <Code>responseElement.value = "__Unanswered__";</Code>
                    <!-- Return -1 to indicate the question was option and unanswered. -->
                    <Code>return -1;</Code>
                    <Code>}</Code>
                </xsl:if>

                <!-- Questionnaires may have time limits. In this instance, if the question is unanswered,
                    the responseElement is given an appropriate value. -->
                <xsl:element name="Code">
                    <xsl:value-of select="concat('if (((nChecked &lt; ', ./MinSelections, ') || (nChecked &gt; ', 
                        ./MaxSelections, ')) &amp;&amp; ForceSubmit){')"/>
                </xsl:element>
                <Code>responseElement.value = "__ForceSubmittedUnanswered__";</Code>
                <!-- Return 1 to indicate the response is flawed, whether it is to be 
                    force submitted because the time limit was exceeded or not. -->
                <Code>return 1;</Code>
                <Code>}</Code>

                <!-- Else, check to ensure the minimum number of elements have been selected. -->
                <xsl:element name="Code">
                    <xsl:value-of select="concat('if (nChecked &lt; ', ./MinSelections, ') {')"/>
                </xsl:element>
                <Code>errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <!-- Insert an appropriate error messsage if not. -->
                <xsl:if test="./MinSelections eq '1'" >
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('errorMsgLI.InnerText = &quot;Please select at least ', ./MinSelections, 
                            ' response to the question below.&quot;')"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="./MinSelections ne '1'" >
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('errorMsgLI.InnerText = &quot;Please select at least ', ./MinSelections, 
                            ' responses to the question below.&quot;')" />
                    </xsl:element>
                </xsl:if>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <!-- Return 1 to indicate the response is flawed. -->
                <Code>return 1;</Code>

                <!-- Else, check to ensure the maximum number of elements have not been exceeded. -->
                <xsl:element name="Code">
                    <xsl:value-of select="concat('} else if (nChecked &gt; ', ./MaxSelections, ') {')" />
                </xsl:element>
                <!-- Insert an appropriate error messsage if not. -->
                <Code>errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <xsl:if test="./MaxSelections eq '1'" >
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('errorMsgLI.InnerText = &quot;Please select no more than ', ./MaxSelections, 
                            ' response to the question below.&quot;')" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="./MaxSelections ne '1'" >
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('errorMsgLI.InnerText = &quot;Please select no more than ', ./MaxSelections, 
                            ' responses to the question below.&quot;')" />
                    </xsl:element>
                </xsl:if>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <!-- Return 1 to indicate the response is flawed. -->
                <Code>return 1;</Code>
                <Code>}</Code>
                <Code>responseElement.value = "";</Code>

                <!-- The response is valid, so tally the checks as a binary string, with the most significant 
                    digit representing the first checkbox. -->
                <xsl:element name="Code">
                    <xsl:value-of select="concat('for (ctr = 0; ctr &lt; ', count(./Labels/Label), '; ctr++) {')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('selectionInput = document.getElementById(&quot;Item', $itemNum, 
                        '_&quot;.concat((ctr + 1).toString()));')" />
                </xsl:element>
                <Code>if (selectionInput.checked)</Code>
                <Code>responseElement.value += "1";</Code>
                <Code>else</Code>
                <Code>responseElement.value += "0";</Code>
                <Code>}</Code>

                <!-- Return 0 for success. -->
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Date']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>answerInput.value = "";</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)"/>
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <Code>var daysInMonths = new Array();</Code>
                <Code>daysInMonths.splice(0, 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>var answer = answerInput.value;</Code>
                <xsl:if test="$optional eq 'True'">
                    <Code>if (answer.length === 0) {</Code>
                    <Code>answerInput.value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                    <Code>}</Code>
                </xsl:if>
                <Code>var error = 0;</Code>
                <Code>var dateExp = /^[0-2]?[0-9]\/([1-3][0-9]|0?[1-9])\/[0-9]{4}$/;</Code>
                <Code>if (!dateExp.test(answer))</Code>
                <Code>error = 1;</Code>
                <Code>if (error == 0) {</Code>
                <Code>var vals = answer.split("/");</Code>
                <Code>var month;</Code>
                <Code>var month = parseInt(vals[0], 10);</Code>
                <Code>day = parseInt(vals[1], 10);</Code>
                <Code>var year = parseInt(vals[2], 10);</Code>
                <Code>if (isNaN(month))</Code>
                <Code>error = 2;</Code>
                <Code>else if (isNaN(day))</Code>
                <Code>error = 3;</Code>
                <Code>else if (isNaN(year))</Code>
                <Code>error = 4;</Code>
                <Code>else if ((month &lt; 1) || (month &gt; 12))</Code>
                <Code>error = 2;</Code>
                <Code>else if ((day &lt; 1) || (day &gt; daysInMonths[month - 1]))</Code>
                <Code>error = 3;</Code>
                <Code>else if ((day == 29) &amp;&amp; (month == 2) &amp;&amp; (year % 4))</Code>
                <Code>error = 3;</Code>
                <Code>else if (year &lt; 0)</Code>
                <Code>error = 4;</Code>
                <xsl:if test="./StartDate[@HasValue eq 'True']" >
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('if (year &lt; ', ./StartDate/Year, ')')" />
                    </xsl:element>
                    <Code>error = 5;</Code>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('else if (year == ', StartDate/Year, ') {')" />
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('if (month &lt; ', StartDate/Month, ')')"/>
                    </xsl:element>
                    <Code>error = 5;</Code>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('else if (month == ', StartDate/Month, ')')"/>
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('if (day &lt; ', StartDate/Day, ')')"/>
                    </xsl:element>
                    <Code>error = 5;</Code>
                    <Code>}</Code>
                </xsl:if>
                <xsl:if test="./EndDate[@HasValue eq 'True']" >
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('if (year &gt; ', EndDate/Year, ')')"/>
                    </xsl:element>
                    <Code>error = 6;</Code>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('else if (year == ', EndDate/Year, ') {')" />
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('if (month &gt; ', EndDate/Month, ')')" />
                    </xsl:element>
                    <Code>error = 6;</Code>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('else if (month == ', EndDate/Month, ')')" />
                    </xsl:element>
                    <xsl:element name="Code">
                        <xsl:value-of select="concat('if (day &gt; ', EndDate/Day, ')')"/>
                    </xsl:element>
                    <Code>error = 6;</Code>
                    <Code>}</Code>
                </xsl:if>
                <Code>}</Code>
                <Code>var monthNames = new Array();</Code>
                <Code>monthNames.push("January");</Code>
                <Code>monthNames.push("February");</Code>
                <Code>monthNames.push("March");</Code>
                <Code>monthNames.push("April");</Code>
                <Code>monthNames.push("May");</Code>
                <Code>monthNames.push("April");</Code>
                <Code>monthNames.push("May");</Code>
                <Code>monthNames.push("June");</Code>
                <Code>monthNames.push("August");</Code>
                <Code>monthNames.push("September");</Code>
                <Code>monthNames.push("October");</Code>
                <Code>monthNames.push("November");</Code>
                <Code>monthNames.push("December");</Code>
                <Code>if (error != 0) {</Code>
                <Code>if (!ForceSubmit) {</Code>
                <Code>var errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <Code>var errorMsg;</Code>
                <Code>if (error == 1)</Code>
                <Code>errorMsg = document.createTextNode("Please enter a date in MM/DD/YYYY format for the question below.");</Code>
                <Code>else if (error == 2)</Code>
                <Code>errorMsg = document.createTextNode("The supplied month is not valid.");</Code>
                <Code>else if (error == 3)</Code>
                <Code>errorMsg = document.createTextNode("The supplied day is not valid.");</Code>
                <Code>else if (error == 4)</Code>
                <Code>errorMsg = document.createTextNode("The supplied year is not valid.");</Code>
                <Code>else if (error == 5)</Code>
                <xsl:element name="Code">
                    <xsl:variable name="date" select="concat('monthNames[', StartDate/Month, ' - 1] + &quot; ', StartDate/Day, ', ', StartDate/Year, '&quot;')" />
                    <xsl:value-of select="concat('errorMsg = document.createTextNode(&quot;Please enter a date that falls on or after &quot; + ', $date, ');')" />
                </xsl:element>
                <Code>else if (error == 6)</Code>
                <xsl:element name="Code">
                    <xsl:variable name="date" select="concat('monthNames[', EndDate/Month, ' - 1] + &quot; ', EndDate/Day, ', ', EndDate/Year, '&quot;')" />
                    <xsl:value-of select="concat('errorMsg = document.createTextNode(&quot;Please enter a date that falls on or before &quot; + ', $date, ');')" />
                </xsl:element>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>return 1;</Code>
                <Code>}} else if (error != 0) {</Code>
                <Code>answerInput.value = "__ForceSubmittedUnanswered__";</Code>
                <Code>return 1;</Code>
                <Code>}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Bounded Length']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)"/>
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>answerInput.value = "";</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)"/>
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>var answer = answerInput.value.trim();</Code>
                <xsl:if test="($optional eq 'True') and (xs:integer(MinLength) gt 0)">
                    <Code>if (answer.length === 0) {</Code>
                    <Code>answerInput.value = "Unanswered";</Code>
                    <Code>return -1;</Code>
                    <Code>}</Code>
                </xsl:if>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('if ((answer.length &lt; ', MinLength, ') || (answer.length &gt; ', MaxLength, ')) {')" />
                </xsl:element>
                <Code>if (!ForceSubmit) {</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <Code>var errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var errorMsg = document.createTextNode(&quot;Please provide a response between ', MinLength, ' and ', MaxLength, ' characters in length for the question below.&quot;);')" />
                </xsl:element>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>return 1;</Code>
                <Code>} else {</Code>
                <Code>answerInput.value = "__ForceSubmittedUnanswered__";</Code>
                <Code>return 1;</Code>
                <Code>}}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Bounded Number']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)"/>
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>answerInput.value = "";</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')"/>
                </xsl:element>
                <xsl:if test="$optional eq 'True'">
                    <Code>if (answerInput.value.length === 0) {</Code>
                    <Code>answerInput.value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                    <Code>}</Code>
                </xsl:if>
                <Code>var answer = answerInput.value;</Code>
                <Code>if (!isNumber(answer)) {</Code>
                <Code>if (!ForceSubmit) {</Code>
                <Code>var errorMsgLI = document.createElement("li");</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <Code>errorMsgLI.className = "Error";</Code>
                <Code>errorMsgLI.appendChild(document.createTextNode("Please enter a numerical response for the question below."));</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>return 1;</Code>
                <Code>} else {</Code>
                <Code>answerInput.value = "__ForceSubmittedUnanswered__";</Code>
                <Code>return 1;</Code>
                <Code>}}</Code>
                <Code>var num;</Code>
                <Code>if (!answer)</Code>
                <Code>num = Number.NaN;</Code>
                <Code>else</Code>
                <Code>num = parseFloat(answer);</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')"/>
                </xsl:element>
                <Code>var errorMsgLI, errorMsg;</Code>
                <Code>if (isNaN(num)) {</Code>
                <Code>if (!ForceSubmit) {</Code>
                <Code>errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <Code>errorMsg = document.createTextNode("Please enter a numerical response for the question below.");</Code>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>return 1;</Code>
                <Code>} else {</Code>
                <Code>answerInput.value = "__ForceSubmittedUnanswered__";</Code>
                <Code>return 1;</Code>
                <Code>}}</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('else if ((num &lt; ', MinValue, ') || (num &gt; ', MaxValue, ')) {')" />
                </xsl:element>
                <Code>if (!ForceSubmit) {</Code>
                <Code>errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('errorMsg = document.createTextNode(&quot;Please enter a numerical response between ', MinValue, ' and ', MaxValue, ' for the question below.&quot;);')" />
                </xsl:element>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>return 1;</Code>
                <Code>} else {</Code>
                <Code>answerInput.value = "NULL";</Code>
                <Code>return 1;</Code>
                <Code>}}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Fixed Digit']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionName">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, ');')" />
                </xsl:element>
                <Code>answerInput.value = "";</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <xsl:if test="$optional eq 'True'">
                    <Code>if (answerInput.value.length === 0) {</Code>
                    <Code>answerInput.value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                    <Code>}</Code>
                </xsl:if>
                <Code>var answer = answerInput.value;</Code>
                <Code>var num = parseInt(answer, 10);</Code>
                <Code>var errorMsgLI, errorMsg;</Code>
                <Code>var bErrorInResponse = false;</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var intExp = /[0-9]{', NumDigs, '}/;')" />
                </xsl:element>
                <Code>if (!intExp.test(num))</Code>
                <Code>bErrorInResponse = true;</Code>
                <Code>if (bErrorInResponse) {</Code>
                <Code>if (!ForceSubmit) {</Code>
                <Code>errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('errorMsg = document.createTextNode(&quot;Please enter a numerical response of ', NumDigs, ' digits for the question below.&quot;);')" />
                </xsl:element>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>return 1;</Code>
                <Code>} else {</Code>
                <Code>answerInput.value = "__ForceSubmittedUnanswered__";</Code>
                <Code>return 1;</Code>
                <Code>}}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="Response[@Type='Regular Expression']" >
        <xsl:param name="itemNum" as="xs:integer" />
        <xsl:param name="liNum" as="xs:integer" />
        <xsl:param name="optional" as="xs:string" />
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('InitializeItem', $itemNum)"/>
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'initialization'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')"/>
                </xsl:element>
                <Code>answerInput.value = "";</Code>
            </xsl:element>
        </xsl:element>
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName">
                <xsl:value-of select="concat('ValidateItem', $itemNum)" />
            </xsl:attribute>
            <xsl:attribute name="FunctionType" select="'validation'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var answerInput = document.getElementById(&quot;Item', $itemNum, '&quot;);')" />
                </xsl:element>
                <Code>var answer = answerInput.value;</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var regEx = /', Expression, '/;')" />
                </xsl:element>
                <Code>if (answer.search(regEx) != 0) {</Code>
                <xsl:if test="$optional eq 'True'">
                    <Code>answerInput.value = "__Unanswered__";</Code>
                    <Code>return -1;</Code>
                </xsl:if>
                <Code>if (!ForceSubmit) {</Code>
                <xsl:element name="Code">
                    <xsl:value-of select="concat('var questionLI = document.getElementById(&quot;ItemLITag', $liNum, '&quot;);')" />
                </xsl:element>
                <Code>var errorMsgLI = document.createElement("li");</Code>
                <Code>errorMsgLI.className = "Error";</Code>
                <Code>var errorMsg = document.createTextNode("Invalid input supplied for the question below.");</Code>
                <Code>errorMsgLI.appendChild(errorMsg);</Code>
                <Code>questionListNode.insertBefore(errorMsgLI, questionLI);</Code>
                <Code>return 1;</Code>
                <Code>} else {</Code>
                <Code>answerInput.value = "__ForceSubmittedUnanswered__";</Code>
                <Code>return 1;</Code>
                <Code>}}</Code>
                <Code>return 0;</Code>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>

