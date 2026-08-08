<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0" exclude-result-prefixes="xs">

    <xsl:output method="xml" encoding="utf-8" indent="yes" cdata-section-elements="Function Declaration FunctionConstructor" />


    <xsl:variable name="root" select="/" />
    <xsl:variable name="variableDeclarations">
        <Declarations>
            <Declaration>var TestURL;</Declaration>
            <Declaration>var AppURL;</Declaration>
            <Declaration>var questionListNode;</Declaration>
            <Declaration>var StopTimer;</Declaration>
            <Declaration>var CryptJSON;</Declaration>
            <Declaration>var uniqueResponseStatus = "none";</Declaration>
            <Declaration>var answers;</Declaration>
            <Declaration>var questionElems;</Declaration>
            <Declaration>var submitted = false;</Declaration>
            <Declaration>var backUrlObj;</Declaration>
            <Declaration>
                <xsl:value-of select="concat('var serverPath = &quot;', //ServerPath, '&quot;;')" />
            </Declaration>
            <Declaration>
                <xsl:value-of select="concat('var adminHost = &quot;', //ServerPath, '/Admin&quot;;')" />
            </Declaration>
            <Declaration>var IsAborting = false;</Declaration>
        </Declarations>
    </xsl:variable>

    <xsl:variable name="functionPrefix">
        <xsl:value-of select="'shF'" />
    </xsl:variable>

    <xsl:variable name="globalVariablePrefix">
        <xsl:value-of select="'shG'" />
    </xsl:variable>

    <xsl:variable name="globalCodePrefix">
        <xsl:value-of select="'shGC'" />
    </xsl:variable>

    <xsl:variable name="Globals">
        <xsl:for-each select="$variableDeclarations/Declarations/Declaration">
            <xsl:variable name="ndx" select="position()" />
            <xsl:analyze-string select="." regex="^var\s*([A-Za-z_][A-Za-z0-9_]*)((\s*=\s*)(.+))?;">
                <xsl:matching-substring>
                    <xsl:element name="Entry">
                        <xsl:attribute name="type" select="'global'" />
                        <xsl:element name="OrigName">
                            <xsl:value-of select="regex-group(1)" />
                        </xsl:element>
                        <xsl:element name="NewName">
                            <xsl:value-of select="concat('_', $globalVariablePrefix, $ndx)" />
                        </xsl:element>
                        <xsl:element name="Assign">
                            <xsl:value-of select="regex-group(4)" />
                        </xsl:element>
                    </xsl:element>
                </xsl:matching-substring>
            </xsl:analyze-string>
        </xsl:for-each>
    </xsl:variable>

    <xsl:variable name="Functions">
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnUnload'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">var lastAdminPhase = false;</xsl:element>
                <xsl:element name="Code">if (sessionStorage.getItem("LastAdminPhase") == "true")</xsl:element>
                <xsl:element name="Code">lastAdminPhase = true;</xsl:element>
                <xsl:element name="Code">if (submitted &amp;&amp; lastAdminPhase) {</xsl:element>
                <xsl:element name="Code">sessionStorage.clear();</xsl:element>
                <xsl:element name="Code">CookieUtil.set("AdminPhase", "0");</xsl:element>
                <xsl:element name="Code">}</xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'appendFormData'" />
            <xsl:element name="Params">
                <xsl:element name="Param">form</xsl:element>
                <xsl:element name="Param">name</xsl:element>
                <xsl:element name="Param">value</xsl:element>
            </xsl:element>
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">var elem = document.createElement("input");</xsl:element>
                <xsl:element name="Code">elem.type = "hidden";</xsl:element>
                <xsl:element name="Code">elem.name = name;</xsl:element>
                <xsl:element name="Code">elem.value = value;</xsl:element>
                <xsl:element name="Code">form.appendChild(elem);</xsl:element>
            </xsl:element>
        </xsl:element>


        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'AbortTest'" />
            <xsl:element name="Params">
                <xsl:element name="Param">reason</xsl:element>
            </xsl:element>
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">var form = document.getElementById("SurveyForm");</xsl:element>
                <xsl:element name="Code">if (sessionStorage.getItem("IATSESSIONID") != null)</xsl:element>
                <xsl:element name="Code">appendFormData(form, "IATSESSIONID", sessionStorage.getItem("IATSESSIONID"));</xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">appendFormData(form, "IATSESSIONID", CookieUtil.get("IATSESSIONID"));</xsl:element>
                <xsl:element name="Code">appendFormData(form, "target", "adminV2");</xsl:element>
                <xsl:element name="Code">appendFormData(form, "ABORT", "TRUE");</xsl:element>
                <xsl:element name="Code">var referer = sessionStorage.getItem("HTTP_REFERER");</xsl:element>
                <xsl:element name="Code">appendFormData(form, "HTTP_REFERER", referer);</xsl:element>
                <xsl:element name="Code">appendFormData(form, "TestURL", location.href);</xsl:element>
                <xsl:element name="Code">sessionStorage.clear();</xsl:element>
                <xsl:element name="Code">form.submit();</xsl:element>
            </xsl:element>
        </xsl:element>


        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnPopState'" />
            <xsl:element name="Params">
                <xsl:element name="Param">evt</xsl:element>
            </xsl:element>
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">if (evt.state.path === undefined) {</xsl:element>
                <xsl:element name="Code">return;</xsl:element>
                <xsl:element name="Code">}</xsl:element>
                <xsl:element name="Code">sessionStorage.clear();</xsl:element>
                <xsl:element name="Code">window.location.assign(evt.state.path);</xsl:element>
            </xsl:element>
        </xsl:element>




        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnLoad'" />
            <xsl:element name="Params" />
            <xsl:variable name="functionBody">
                <xsl:value-of select="concat('var testElem = &quot;', //Survey/@FileName, '&quot;;&#x0A;')" />
                <xsl:if test="//Survey/@UniqueResponseItem ne '-1'">
                    <xsl:value-of select="concat('EventUtil.addHandler(document.getElementById(&quot;Item', //Survey/@UniqueResponseItem, '&quot;), &quot;onblur&quot;);&#x0A;')" />
                </xsl:if>
                <xsl:variable name="itemNodes" select="for $i in $root//Survey/child::*[(name() eq 'SurveyItem') or (name() eq 'SurveyImage')] 
            return $i" />
                <xsl:variable name="liNdxs" select="for $i in $itemNodes return if (not($i/child::Text/following-sibling::*[1][Format])) then (0) 
            else (index-of($itemNodes, $i))" />
            	<xsl:variable name="questionElems">
            		<xsl:value-of select="replace(concat('questionElems = [', string-join(for $i in $liNdxs return if (xs:integer($i) eq 0) 
                    	then ('') else (concat('document.getElementById(&quot;ItemLITag', $i, '&quot;)')), ', '), '];&#x0A;'), '(, ){2,}', '')" />
                </xsl:variable>
                <xsl:value-of select="replace(replace($questionElems, ', \]', ']'), '\[, ', '[')" />
                <xsl:variable name="n" select="count($root//SurveyItem/node()/Format)" />
                <xsl:value-of select="concat('answers = [ ', string-join(for $i in 1 to $n return 
                    concat('document.getElementsByName(&quot;Item', $i, '&quot;)'), ', '), '];&#x0A;')" />
                <xsl:text>
                document.getElementById("SubmitButton").onclick = OnSubmit;
                questionListNode = document.getElementById("QuestionList");
                    if (!sessionStorage.getItem("HTTP_REFERER")) {
                       if (CookieUtil.checkCookie("HTTP_REFERER")) {
                            sessionStorage.setItem("HTTP_REFERER", CookieUtil.get("HTTP_REFERER"));
                        } else {
                            sessionStorage.setItem("HTTP_REFERER", "-");
                        }
                    }
                    var obj = JSON.parse("{ \"path\" : \"/\" }");
                    if (sessionStorage.getItem("IATSESSIONID") == null) {
                        sessionStorage.setItem("IATSESSIONID", CookieUtil.get("IATSESSIONID"));
                    } 
                    sessionStorage.setItem("TestURL", window.location.href);
                    EventUtil.addHandler(window, "popstate", OnPopState);
                    sessionStorage.setItem("corrupted", "false");
                    sessionStorage.setItem("TestSegment", CookieUtil.get("TestSegment"));
                    CookieUtil.deleteCookie("IATSESSIONID");
                    CookieUtil.deleteCookie("AdminPhase");
                    CookieUtil.deleteCookie("LastAdminPhase");
                    CookieUtil.deleteCookie("HTTP_REFERER");
                    CookieUtil.deleteCookie("Alternate");
                    CookieUtil.deleteCookie("TestSegment");
                    document.getElementById("mainContent").focus();
                 </xsl:text>
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="tokenize($functionBody, '&#x0A;')">
                    <xsl:if test="string-length(normalize-space(.)) gt 0">
                        <xsl:element name="Code">
                            <xsl:value-of select="normalize-space(.)" />
                        </xsl:element>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>

    </xsl:variable>


    <xsl:variable name="GlobalCode"></xsl:variable>


    <xsl:template match="Survey">
        <xsl:element name="CodeFile">
            <xsl:element name="VarEntries">
                <xsl:copy-of select="$Globals" />
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
</xsl:stylesheet>