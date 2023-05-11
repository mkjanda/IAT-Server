<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mine="http://www.iatsoftware.net"                
                version="2.0"
                exclude-result-prefixes="xs">

    <xsl:output method="xml" encoding="utf-8" indent="yes"/>

    <xsl:variable name="root" select="/" />

    <xsl:variable name="serverURLParts">
        <xsl:analyze-string select="//ServerURL" regex="[^/]+">
            <xsl:matching-substring>
                <xsl:element name="serverURLPart">
                    <xsl:if test="contains(., '.')">
                        <xsl:if test="$root//ServerPort eq '80'">
                            <xsl:value-of select="." />
                        </xsl:if>
                        <xsl:if test="$root//ServerPort ne '80'">
                            <xsl:value-of select="concat(., ':', $root//ServerPort)" />
                        </xsl:if>
                    </xsl:if>
                    <xsl:if test="not(contains(., '.'))">
                        <xsl:value-of select="." />
                    </xsl:if>
                </xsl:element>
            </xsl:matching-substring>
            <xsl:non-matching-substring>
                <xsl:element name="serverURLPart">
                    <xsl:value-of select="." />
                </xsl:element>
            </xsl:non-matching-substring>
        </xsl:analyze-string>
    </xsl:variable>
    <xsl:variable name="serverURL">
        <xsl:value-of select="string-join($serverURLParts/serverURLPart, '')" />
    </xsl:variable>
    <xsl:variable name="testURL">
        <xsl:value-of select="concat($root//ServerPath, '/', //ClientID, '/', //IATName)" />
    </xsl:variable>

    <xsl:variable name="variableDeclarations">
        <Declarations>
            <xsl:element name="Declaration">
                <xsl:value-of select="concat('var NumImages = ', count(distinct-values(//IATDisplayItem/Filename)), ';')"/>
            </xsl:element>
            <xsl:for-each select="//IATDisplayItem">
                <xsl:element name="Declaration">
                    <xsl:value-of select="concat('var img', ID, ';')"/>
                </xsl:element>
            </xsl:for-each>
            <Declaration>var imgTable;</Declaration>
            <Declaration>var ImageLoadComplete = false;</Declaration>
            <Declaration>var CodeProcessingComplete = false;</Declaration>
            <Declaration>var ImageLoadCtr = 0;</Declaration>
            <Declaration>var ImageLoadStatusTextElement;</Declaration>
            <Declaration>var ClickToStartElement;</Declaration>
            <Declaration>var ClickToStartText;</Declaration>
            <Declaration>var abort = false;</Declaration>
            <Declaration>var AllImagesLoaded = false;</Declaration>
            <Declaration>var submitted = false;</Declaration>
            <Declaration>var IsAborting = false;</Declaration>
            <Declaration>var backUrlObj = { url : "https://iatsoftware.net" };</Declaration>
            <xsl:if test="count(//DynamicSpecifier) gt 0">
                <Declaration>var DynamicSpecValues;</Declaration>
                <Declaration>var DynamicSpecValuesLoaded = false;</Declaration>
            </xsl:if>
            <Declaration>
                <xsl:value-of select="concat('var adminHost = &quot;', $root//ServerPath, '/Admin&quot;;')" />
            </Declaration>
        </Declarations>
    </xsl:variable>


    <xsl:variable name="functionPrefix">
        <xsl:value-of select="'ihF'"/>
    </xsl:variable>

    <xsl:variable name="globalVariablePrefix">
        <xsl:value-of select="'ihG'"/>
    </xsl:variable>

    <xsl:variable name="globalCodePrefix">
        <xsl:value-of select="'ihGC'"/>
    </xsl:variable>


    <xsl:variable name="Globals">
        <xsl:for-each select="$variableDeclarations/Declaration/Declaration">
            <xsl:variable name="ndx" select="position()" />
            <xsl:analyze-string select="." regex="^var\s*([A-Za-z_][A-Za-z0-9_]*)(\s*=\s*)?(.+)?;">
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
                            <xsl:value-of select="regex-group(3)"/>
                        </xsl:element>
                    </xsl:element>
                </xsl:matching-substring>
            </xsl:analyze-string>
        </xsl:for-each>
    </xsl:variable>

    <xsl:variable name="Functions">
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnImageLoad'" />
            <xsl:element name="Params" />
            <xsl:variable name="functionBody">
                <xsl:text>
                    ImageLoadCtr++;
                    ImageLoadStatusTextElement.nodeValue = "Loading image #" + (ImageLoadCtr + 1).toString() + " of " + NumImages.toString();
                    if (ImageLoadCtr == NumImages) 
                    ImageLoadCompleted();
                </xsl:text>
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="tokenize($functionBody, '&#x0A;')" >
                    <xsl:if test="string-length(normalize-space(.)) gt 0">
                        <xsl:element name="Code">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnImageLoadError'" />
            <xsl:element name="Params">
                <xsl:element name="Param">event</xsl:element>
            </xsl:element>
            <xsl:variable name="functionBody">
                <xsl:text>
                    var e = EventUtil.getEvent(event); 
                    var img = e.currentTarget;
                    imgTable[img.src] = new Image();
                    EventUtil.addHandler(imgTable[img.src], 'load', OnImageLoad);
                    EventUtil.addHandler(imgTable[img.src], 'error', OnImageLoadError);
                    imgTable[img.src].src = img.src;
                </xsl:text>
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="tokenize($functionBody, '&#x0A;')" >
                    <xsl:if test="string-length(normalize-space(.)) gt 0">
                        <xsl:element name="Code">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>


        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'StartImageLoad'" />
            <xsl:element name="Params" />
            <xsl:variable name="functionBody">
                <xsl:variable name="imgAry">
                    <xsl:for-each select="distinct-values(//IATDisplayItem/Filename)">
                        <xsl:element name="ImageTableEntry">
                            <xsl:value-of select="concat('&quot;', $testURL, '/', ., '&quot; : new Image()')"/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:variable>
                <xsl:value-of select="concat('imgTable = { ', string-join($imgAry/ImageTableEntry, ', '), ' };')" />
                <xsl:text>
                    var LoadingImagesElement = document.createElement("h3");
                    var LoadingImagesText = document.createTextNode("Please Wait");
                    LoadingImagesElement.appendChild(LoadingImagesText);
                    var LoadingImagesProgressElement = document.createElement("h4");
                    ImageLoadStatusTextElement = document.createTextNode("Loading image #1 of " + NumImages.toString());
                    LoadingImagesProgressElement.appendChild(ImageLoadStatusTextElement);
                    var displayDiv = document.getElementById("IATDisplayDiv");
                    displayDiv.appendChild(LoadingImagesElement);
                    displayDiv.appendChild(LoadingImagesProgressElement);
                </xsl:text>
                <xsl:for-each select="//IATDisplayItem">
                    <xsl:variable name="imgSrc" select="string-join(('/IAT/resources/image/', //ClientID,  //IATName, //ID), '/')" />
                    <xsl:value-of select="concat('EventUtil.addHandler(imgTable[&quot;', $testURL, '/', Filename, '&quot;], &quot;load&quot;,
                        OnImageLoad);&#x0A;')" />
                    <xsl:value-of select="concat('EventUtil.addHandler(imgTable[&quot;', $testURL, '/', Filename, '&quot;], &quot;error&quot;, 
                        OnImageLoadError);&#x0A;')"/>
                    <xsl:value-of select="concat('imgTable[&quot;', $testURL, '/', Filename, '&quot;].src = ', $imgSrc, ';')" />
                </xsl:for-each>
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="tokenize($functionBody, '&#x0A;')">
                    <xsl:if test="string-length(normalize-space(.)) gt 0">
                        <xsl:element name="Code">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'ImageLoadCompleted'" />
            <xsl:element name="Params" />
            <xsl:variable name="functionBodyCode">
                <xsl:for-each select="//IATDisplayItem">
                    <xsl:value-of select="concat('img', ID, ' = imgTable[&quot;', $testURL, '/', Filename, '&quot;];&#x0A;')" />
                </xsl:for-each>
                <xsl:text>
                    TestReady("Images");
                </xsl:text>
            </xsl:variable>
            <xsl:element name="FunctionBody">
                <xsl:for-each select="tokenize($functionBodyCode, '&#x0A;')">
                    <xsl:if test="string-length(normalize-space(.)) gt 0">
                        <xsl:element name="Code">
                            <xsl:value-of select="normalize-space(.)" />
                        </xsl:element>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'TestReady'" />
            <xsl:element name="Params">
                <xsl:element name="Param">elem</xsl:element>
            </xsl:element>
            <xsl:variable name="functionBody">
                <xsl:text>
                    var displayDiv = document.getElementById("IATDisplayDiv");
                    if (elem == "Images")
                    ImageLoadComplete = true;
                </xsl:text>
                <xsl:if test="count(//DynamicSpecifier) gt 0">
                    <xsl:text>
                        else if (elem == "DynamicSpecifiers")
                        DynamicSpecValuesLoaded = true;
                        if (ImageLoadComplete &amp;&amp; DynamicSpecValuesLoaded) {
                    </xsl:text>
                </xsl:if>
                <xsl:if test="count(//DynamicSpecifier) eq 0">
                    <xsl:value-of select="'if (ImageLoadComplete) {&#x0A;'"/>
                </xsl:if>
                <xsl:text>
                    while (displayDiv.firstChild)
                    displayDiv.removeChild(displayDiv.firstChild);
                    ClickToStartElement = document.createElement("h4");
                    ClickToStartText = document.createTextNode("Click Here to Begin");
                    ClickToStartElement.appendChild(ClickToStartText);
                    displayDiv.appendChild(ClickToStartElement);
                    EventUtil.addHandler(window, "click", BeginIAT);
                    EventUtil.addHandler(ClickToStartElement, "click", BeginIAT);
                    EventUtil.addHandler(displayDiv, "click", BeginIAT);
                    } else if (ImageLoadComplete) {
                    while (displayDiv.firstChild)
                    displayDiv.removeChild(displayDiv.firstChild);
                    var PleaseWaitElement = document.createElement("h3");
                    var PleaseWaitText = document.createTextNode("Preparing Administration");
                    PleaseWaitElement.appendChild(PleaseWaitText);
                    displayDiv.appendChild(PleaseWaitElement);
                    }
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


        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnUnload'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">var lastAdminPhase = false;</xsl:element>
                <xsl:element name="Code">if (sessionStorage.getItem("LastAdminPhase") == "true")</xsl:element>
                <xsl:element name="Code">lastAdminPhase = true;</xsl:element>
                <xsl:element name="Code">if (submitted &amp;&amp; lastAdminPhase) {</xsl:element>
                <xsl:element name="Code">sessionStorage.removeItem("IATSESSIONID");</xsl:element>
                <xsl:element name="Code">sessionStorage.removeItem("AdminPhase");</xsl:element>
                <xsl:element name="Code">sessionStorage.removeItem("TestSegment");</xsl:element>
                <xsl:element name="Code">sessionStorage.removeItem("LastAdminPhase");</xsl:element>
                <xsl:element name="Code">sessionStorage.removeItem("corrupted");</xsl:element>
                <xsl:element name="Code">CookieUtil.set("AdminPhase", "0");</xsl:element>
                <xsl:element name="Code">}</xsl:element>
            </xsl:element>
        </xsl:element>
		
        <xsl:if test="count(//DynamicSpecifier)">
            <xsl:element name="Function">
                <xsl:attribute name="FunctionName" select="'OnDynamicSpecLoad'" />
                <xsl:element name="Params">
                    <xsl:element name="Param">responseText</xsl:element>
                </xsl:element>
                <xsl:variable name="functionBody">
                    <xsl:text>
                        DynamicSpecValues = JSON.parse(responseText);
                        TestReady("DynamicSpecifiers");
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
        </xsl:if>
        
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
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">var form = document.getElementById("IATForm");</xsl:element>
                <xsl:element name="Code">if (sessionStorage.getItem("IATSESSIONID") != null)</xsl:element>
                <xsl:element name="Code">appendFormData(form, "IATSESSIONID", sessionStorage.getItem("IATSESSIONID"));</xsl:element>
                <xsl:element name="Code">else</xsl:element>
                <xsl:element name="Code">appendFormData(form, "IATSESSIONID", CookieUtil.get("IATSESSIONID"));</xsl:element>
                <xsl:element name="Code">appendFormData(form, "target", "adminV2");</xsl:element>
                <xsl:element name="Code">appendFormData(form, "ABORT", "TRUE");</xsl:element>
                <xsl:element name="Code">appendFormData(form, "HTTP_REFERER", sessionStorage.getItem("HTTP_REFERER"));</xsl:element>
                <xsl:element name="Code">sessionStorage.clear();</xsl:element>
                <xsl:element name="Code">form.submit();</xsl:element>
            </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnPopState'" />
            <xsl:element name="Params">
                <xsl:element name="Param">evt</xsl:element>
            </xsl:element>
            <xsl:variable name="functionBody">
                <xsl:text>
                    window.location.assign(evt.state.url);
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
            
        
        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnLoad'" />
            <xsl:element name="Params" />
            <xsl:variable name="functionBody">
                <xsl:value-of select="concat('var corruptAdminCookie = &quot;', //ClientID, '-', //IATName, '-corrupt&quot;;&#x0A;')"/>
                <xsl:text>
                    var adminPhase, localAdminPhase, testSegment;
                    if (!sessionStorage.getItem("HTTP_REFERER")) {
                    if (CookieUtil.checkCookie("HTTP_REFERER"))
                    sessionStorage.setItem("HTTP_REFERER", CookieUtil.get("HTTP_REFERER"));
                    else 
                    sessionStorage.setItem("HTTP_REFERER", "-");
                    }
                    if (!CookieUtil.checkCookie("AdminPhase") || !CookieUtil.checkCookie("TestSegment") || !CookieUtil.checkCookie("LastAdminPhase")) {
                    AbortTest();
                    return;
                    }
                    if (!sessionStorage.getItem("IATSESSIONID")) {
                    sessionStorage.setItem("IATSESSIONID", CookieUtil.get("IATSESSIONID"));
                    sessionStorage.setItem("AdminPhase", "0");
                    if (parseInt(CookieUtil.get("AdminPhase"), 10) !== 0) {
                    AbortTest();
                    return;
                    }
                    if (sessionStorage.getItem("corrupted") == "true") {
                    AbortTest();
                    return;
                    }
                    sessionStorage.setItem("corrupted", false);

                    } else if (sessionStorage.getItem("corrupted") === true) {
                            AbortTest();
                    return;
                            } else if (sessionStorage.getItem("AdminPhase")) {
                        adminPhase = parseInt(CookieUtil.get("AdminPhase"), 10);
                        localAdminPhase = parseInt(sessionStorage.getItem("AdminPhase"), 10);
                        if (adminPhase !== localAdminPhase + 1) {
                    
                    AbortTest();
                    return;
                    }
                    sessionStorage.setItem("AdminPhase", adminPhase.toString());
                    } else {
                    AbortTest();
                    return;
                    }
                    sessionStorage.setItem("TestSegment", CookieUtil.get("TestSegment"));
                    sessionStorage.setItem("LastAdminPhase", CookieUtil.get("LastAdminPhase"));
                    testSegment = CookieUtil.get("TestSegment");
                    CookieUtil.deleteCookie("IATSESSIONID");
                    CookieUtil.deleteCookie("AdminPhase");
                    CookieUtil.deleteCookie("LastAdminPhase");
                    CookieUtil.deleteCookie("HTTP_REFERER");
                    CookieUtil.deleteCookie("TestSegment");
                    CookieUtil.deleteCookie("Alternate");
                </xsl:text>
                <xsl:value-of select="concat('var requestSrc = window.location.protocol + &quot;//&quot; + window.location.hostname + (window.location.port ? &quot;:&quot; + window.location.port.toString() : &quot;&quot;) + &quot;', $root//ServerPath, '&quot; + &quot;/Resource/', //ClientID, '/', //IATName, '/', //IATName, '.html&quot;;&#x0A;')"/>
                <xsl:value-of select="concat('var testElem = &quot;', //IATName, '&quot;;&#x0A;')" />
                <xsl:if test="count(//DynamicSpecifier) gt 0">
                    <xsl:text>
                        var dynamicSpecCall = new AjaxCallv2(adminHost + "/Ajax/DynamicSpecifiers.json", requestSrc, testSegment, "text/json");
                        dynamicSpecCall.call(OnDynamicSpecLoad, null, "GET");
                    </xsl:text>
                </xsl:if>
                <xsl:text>
                    DisplayDiv = document.getElementById("IATDisplayDiv");
                    window.history.pushState(backUrlObj, "IAT Software", "https://iatsoftware.net");
                    EventUtil.addHandler(window, "popstate", OnPopState);
                    var alternateTag = document.getElementById("Alternate");
                    alternateTag.setAttribute("value", CookieUtil.get("Alternate"));
                    CookieUtil.deleteCookie("IATSESSIONID");
                    CookieUtil.deleteCookie("AdminPhase");
                    CookieUtil.deleteCookie("LastAdminPhase");
                    CookieUtil.deleteCookie("HTTP_REFERER");
                    CookieUtil.deleteCookie("TestSegment");
                    CookieUtil.deleteCookie("Alternate");
                    StartImageLoad();
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

        <xsl:element name="Function">
            <xsl:attribute name="FunctionName" select="'OnRetrieveScript'" />
            <xsl:element name="Params" />
            <xsl:element name="FunctionBody">
                <xsl:element name="Code">TestElemReady("Code");</xsl:element>
            </xsl:element>
        </xsl:element>
    </xsl:variable>

    <xsl:template match="ConfigFile">
        <xsl:element name="CodeFile">

            <xsl:element name="VarEntries">
                <xsl:copy-of select="$Globals" />
            </xsl:element>
            <xsl:element name="Functions">
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
            <xsl:element name="GlobalCode">
                <xsl:attribute name="CodePrefix" select="$globalCodePrefix" />
                <xsl:copy-of select="$GlobalCode"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>