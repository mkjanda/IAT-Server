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

	<xsl:variable name="GlobalCode">
		<xsl:element name="Declaration">
			<xsl:value-of select="concat('NumImages = ', count(//DisplayItemList/IATDisplayItem))"/>
		</xsl:element>
		<xsl:for-each select="//IATDisplayItem">
			<xsl:element name="Declaration">
				<xsl:value-of select="concat('img', ID)"/>
			</xsl:element>
		</xsl:for-each>
		<Declaration>imgTable</Declaration>
		<Declaration>ImageLoadComplete = false</Declaration>
		<Declaration>CodeProcessingComplete = false</Declaration>
		<Declaration>ImageLoadCtr = 0</Declaration>
		<Declaration>ImageLoadStatusTextElement</Declaration>
		<Declaration>LoadingImagesProgressElement</Declaration>
		<Declaration>ClickToStartElement</Declaration>
		<Declaration>ClickToStartText</Declaration>
		<Declaration>abort = false</Declaration>
		<Declaration>AllImagesLoaded = false</Declaration>
		<Declaration>submitted = false</Declaration>
		<Declaration>IsAborting = false</Declaration>
		<Declaration>backUrlObj = { url : "https://iatsoftware.net" }</Declaration>
		<xsl:if test="count(//DynamicSpecifier) gt 0">
			<Declaration>var DynamicSpecValues</Declaration>
			<Declaration>var DynamicSpecValuesLoaded = false</Declaration>
		</xsl:if>
		<Declaration>
			<xsl:value-of select="concat('var adminHost = &quot;', $root//ServerPath, '/Admin&quot;')" />
		</Declaration>
	</xsl:variable>


	<xsl:variable name="functionPrefix">
		<xsl:value-of select="'ihF'"/>
	</xsl:variable>

	<xsl:variable name="globalVariablePrefix">
		<xsl:value-of select="'gv'"/>
	</xsl:variable>

	<xsl:variable name="globalCodePrefix">
		<xsl:value-of select="'ihGC'"/>
	</xsl:variable>


	<xsl:variable name="Globals">
		<xsl:for-each select="$GlobalCode/Declaration">
			<xsl:variable name="ndx" select="position()" />
			<xsl:analyze-string select="." regex="^(var\s*)?([A-Za-z_][A-Za-z0-9_]*)(\s*=\s*)?(.+)?">
				<xsl:matching-substring>
					<xsl:element name="Entry">
						<xsl:attribute name="type" select="'global'" />
						<xsl:element name="OrigName">
							<xsl:value-of select="regex-group(2)" />
						</xsl:element>
						<xsl:element name="NewName">
							<xsl:value-of select="concat('_', $globalVariablePrefix, $ndx)" />
						</xsl:element>
						<xsl:element name="Assign">
							<xsl:value-of select="regex-group(4)"/>
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
					document.getElementById("h4Msg").innerHTML = "Loading image #" + (ImageLoadCtr + 1).toString() + " of " + NumImages.toString(); 
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
                    imgTable[img.src].onload = OnImageLoad;
                    imgTable[img.src].onerror = OnImageLoadError;
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
			<xsl:text>
				var displayDiv = document.getElementById("IATDisplayDiv");
				LoadingImagesElement = document.createElement("h3");
				LoadingImagesElement.innerHTML = "Please Wait";
				displayDiv.appendChild(LoadingImagesElement);			
				var elem = document.createElement("h4");
				elem.setAttribute('id', 'h4Msg');	
				elem.innerHTML = "Loading image #1 of " + NumImages.toString();
				displayDiv.appendChild(elem);
			</xsl:text>
				<xsl:value-of select="'imgTable = [];&#x0A;'" />
				<xsl:for-each select="$root//DisplayItemList/IATDisplayItem">
					<xsl:variable name="imageUrl" select="concat('/IAT/resource/', $root//ClientID, '/', $root//IATName, '/', ID, '/img')" />
					<xsl:variable name="imageTableEntry" select="concat('imgTable[&quot;', $imageUrl, '&quot;]')" />
					<xsl:value-of select="concat($imageTableEntry, ' = new Image();&#x0A;')" />
					<xsl:value-of select="concat($imageTableEntry, '.onload = OnImageLoad;&#x0A;')" />

					<xsl:value-of select="concat($imageTableEntry, '.onerror = OnImageLoadError;&#x0A;')" />
					<xsl:value-of select="concat($imageTableEntry, '.src = &quot;', $imageUrl, '&quot;;&#x0A;')" />
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
				<xsl:for-each select="$root//DisplayItemList/IATDisplayItem">
					<xsl:variable name="imgSrc" select="string-join(('/IAT/resource', $root//ClientID, $root//IATName, ID, 'img'), '/')" />
					<xsl:value-of select="concat('img', ID, ' = imgTable[&quot;', $imgSrc, '&quot;];&#x0A;')" />
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
					sessionStorage.clear();
                   window.location.assign("/");
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
                    if (!sessionStorage.getItem("IATSESSIONID")) {
                    sessionStorage.setItem("IATSESSIONID", CookieUtil.get("IATSESSIONID"));
                    sessionStorage.setItem("AdminPhase", "0");
					adminPhase = 0;
					} else {
					adminPhase = parseInt(sessionStorage.getItem("AdminPhase"), 10);
                    sessionStorage.setItem("AdminPhase", (adminPhase + 1).toString());
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
     //               window.history.pushState({}, "IAT Software", '/');
	 				window.onpopstate = (evt) => window.location.assign("/");
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
			<VarEntries>
				<xsl:copy-of select="$Globals" />
			</VarEntries>
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
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>