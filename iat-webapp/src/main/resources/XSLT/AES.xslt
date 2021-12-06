<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                exclude-result-prefixes="xs">

  <xsl:output method="xml" encoding="utf-8" indent="yes"/>

  <xsl:variable name="VariableDeclarations">
    <Declarations>
      <Declaration>var B64;</Declaration>
      <Declaration>var decryptor;</Declaration>
    </Declarations>
  </xsl:variable>

  <xsl:variable name="classPrefix">
    <xsl:value-of select="'aesC'"/>
  </xsl:variable>

  <xsl:variable name="classFunctionPrefix">
    <xsl:value-of select="'aesCF'"/>
  </xsl:variable>

  <xsl:variable name="globalVariablePrefix">
    <xsl:value-of select="'aesG'"/>
  </xsl:variable>

  <xsl:variable name="globalCodePrefix">
    <xsl:value-of select="'aesGC'"/>
  </xsl:variable>

  <xsl:variable name="GlobalAbbreviations">
    <xsl:variable name="Globals" select="string-join(for $elem in $VariableDeclarations/Declarations/Declaration return replace($elem, '^var\s+(.+);$', '$1,,'), '')" />
    <xsl:analyze-string select="$Globals" regex="([A-Za-z_][A-Za-z0-9_]*)(\s*=\s*(\[|\s+|[^,;=/&#34;\(\[\]]+|(&#34;[^&#xA;&#xD;&#34;]*?&#34;)+|\(([^;,=&#34;]*(,)?(&#34;[^&#xA;&#xD;&#34;]*?&#34;)?)*?\)|/[^/\n]+?/|\](\s*,)?)+)?,">
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
            <xsl:value-of select="regex-group(2)" />
          </xsl:element>
        </xsl:element>
      </xsl:matching-substring>
    </xsl:analyze-string>
  </xsl:variable>

  <xsl:variable name="Classes">
    <xsl:element name="Class">
      <xsl:attribute name="ClassName" select="'StringBuffer'" />
      <xsl:element name="Super">
        <xsl:attribute name="Has" select="'no'" />
      </xsl:element>
      <xsl:element name="Constructor">
        <xsl:element name="Params" />
        <xsl:element name="ConstructorBody">
          <xsl:element name="Code">this.buffer = [];</xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:element name="PrototypeChain">
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'append'" />
          <xsl:element name="Params">
            <xsl:element name="Param">string</xsl:element>
          </xsl:element>
          <xsl:variable name="functionBodyElems">
            <xsl:element name="Code">this.buffer.push(string);</xsl:element>
            <xsl:element name="Code">return this;</xsl:element>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="$functionBodyElems/Code">
              <xsl:element name="Code">
                <xsl:value-of select="." />
              </xsl:element>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'toString'" />
          <xsl:element name="Params" />
          <xsl:variable name="functionBodyElems">
            <xsl:element name="Code">return this.buffer.join("");</xsl:element>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="$functionBodyElems/Code">
              <xsl:element name="Code">
                <xsl:value-of select="." />
              </xsl:element>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>

    <xsl:element name="Class">
      <xsl:attribute name="ClassName" select="'Base64'" />
      <xsl:element name="Super">
        <xsl:attribute name="Has" select="'no'" />
      </xsl:element>
      <xsl:element name="Constructor">
        <xsl:element name="Params" />
        <xsl:element name="ConstructorBody">
          <xsl:element name="Code">this.codex = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";</xsl:element>
          <xsl:element name="Code">return this;</xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:element name="PrototypeChain">
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'encode'" />
          <xsl:element name="Params">
            <xsl:element name="Param">input</xsl:element>
          </xsl:element>
          <xsl:variable name="functionBodyElems">
            <xsl:element name="Code">var output = new StringBuffer();</xsl:element>
            <xsl:element name="Code">var enumerator = new Utf8EncodeEnumerator(input);</xsl:element>
            <xsl:element name="Code">while (enumerator.moveNext()) {</xsl:element>
            <xsl:element name="Code">var chr1 = enumerator.getCurrent();</xsl:element>
            <xsl:element name="Code">enumerator.moveNext();</xsl:element>
            <xsl:element name="Code">var chr2 = enumerator.getCurrent();</xsl:element>
            <xsl:element name="Code">enumerator.moveNext();</xsl:element>
            <xsl:element name="Code">var chr3 = enumerator.getCurrent();</xsl:element>
            <xsl:element name="Code">var enc1 = chr1 &gt;&gt; 2;</xsl:element>
            <xsl:element name="Code">var enc2 = ((chr1 &amp; 3) &lt;&lt; 4) | (chr2 &gt;&gt; 4);</xsl:element>
            <xsl:element name="Code">var enc3 = ((chr2 &amp; 15) &lt;&lt; 2) | (chr3 &gt;&gt; 6);</xsl:element>
            <xsl:element name="Code">var enc4 = chr3 &amp; 63;</xsl:element>
            <xsl:element name="Code">if (isNaN(chr2)) {</xsl:element>
            <xsl:element name="Code">enc3 = enc4 = 64;</xsl:element>
            <xsl:element name="Code">} else if (isNaN(chr3)) {</xsl:element>
            <xsl:element name="Code">enc4 = 64;</xsl:element>
            <xsl:element name="Code">output.append(this.codex.charAt(enc1) + this.codex.charAt(enc2) + this.codex.charAt(enc3) + this.codex.charAt(enc4));</xsl:element>
            <xsl:element name="Code">}}</xsl:element>
            <xsl:element name="Code">return output.toString();</xsl:element>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="$functionBodyElems/Code">
              <xsl:element name="Code">
                <xsl:value-of select="." />
              </xsl:element>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'decode'" />
          <xsl:element name="Params">
            <xsl:element name="Param">input</xsl:element>
          </xsl:element>
          <xsl:variable name="functionBodyElems">
            <xsl:element name="Code">var n, output = new Array();</xsl:element>
            <xsl:element name="Code">var byteOutput = new Array();</xsl:element>
            <xsl:element name="Code">var enumerator = new Base64DecodeEnumerator(input);</xsl:element>
            <xsl:element name="Code">while (enumerator.moveNext())</xsl:element>
            <xsl:element name="Code">byteOutput.push(enumerator.getCurrent());</xsl:element>
            <xsl:element name="Code">while (byteOutput.length &gt;= 4) {</xsl:element>
            <xsl:element name="Code">n = 0;</xsl:element>
            <xsl:element name="Code">n |= (byteOutput.shift()) &lt;&lt; 24;</xsl:element>
            <xsl:element name="Code">n |= (byteOutput.shift()) &lt;&lt; 16;</xsl:element>
            <xsl:element name="Code">n |= (byteOutput.shift()) &lt;&lt; 8;</xsl:element>
            <xsl:element name="Code">n |= byteOutput.shift();</xsl:element>
            <xsl:element name="Code">output.push(n);</xsl:element>
            <xsl:element name="Code">}</xsl:element>
            <xsl:element name="Code">return output;</xsl:element>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="$functionBodyElems/Code">
              <xsl:element name="Code">
                <xsl:value-of select="." />
              </xsl:element>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'getCodexIndex'" />
          <xsl:element name="Params">
            <xsl:element name="Param">ch</xsl:element>
          </xsl:element>
          <xsl:element name="FunctionBody">
            <xsl:element name="Code">return this.codex.indexOf(ch);</xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>

    <xsl:element name="Class">
      <xsl:attribute name="ClassName" select="'Utf8EncodeEnumerator'" />
      <xsl:element name="Super">
        <xsl:attribute name="Has" select="'no'" />
      </xsl:element>
      <xsl:element name="Constructor">
        <xsl:element name="Params">
          <xsl:element name="Param">input</xsl:element>
        </xsl:element>
        <xsl:variable name="constructorBodyElems">
          <xsl:element name="Code">this._input = input;</xsl:element>
          <xsl:element name="Code">this._index = -1;</xsl:element>
          <xsl:element name="Code">this._buffer = [];</xsl:element>
          <xsl:element name="Code">this.current = Number.NaN;</xsl:element>
          <xsl:element name="Code">return this;</xsl:element>
        </xsl:variable>
        <xsl:element name="ConstructorBody">
          <xsl:for-each select="$constructorBodyElems/Code">
            <xsl:element name="Code">
              <xsl:value-of select="." />
            </xsl:element>
          </xsl:for-each>
        </xsl:element>
      </xsl:element>
      <xsl:element name="PrototypeChain">
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'moveNext'" />
          <xsl:element name="Params" />
          <xsl:variable name="functionBodyElems">
            <xsl:element name="Code">if (this._buffer.length &gt; 0) {</xsl:element>
            <xsl:element name="Code">this.current = this._buffer.shift();</xsl:element>
            <xsl:element name="Code">return true;</xsl:element>
            <xsl:element name="Code">} else if (this._index &gt;= (this._input._length - 1)) {</xsl:element>
            <xsl:element name="Code">this.current = Number.NaN;</xsl:element>
            <xsl:element name="Code">return false;</xsl:element>
            <xsl:element name="Code">} else {</xsl:element>
            <xsl:element name="Code">var charCode = this._input.charCodeAt(++this._index);</xsl:element>
            <xsl:element name="Code">if ((charCode == 13) &amp;&amp; (this._input.charCodeAt(this._index + 1) == 10)) {</xsl:element>
            <xsl:element name="Code">charCode = 10;</xsl:element>
            <xsl:element name="Code">this._index += 2;</xsl:element>
            <xsl:element name="Code">}</xsl:element>
            <xsl:element name="Code">if (charCode &lt; 128) {</xsl:element>
            <xsl:element name="Code">this.current = charCode;</xsl:element>
            <xsl:element name="Code">} else if ((charCode &gt; 127) &amp;&amp; (charCode &lt; 248)) {</xsl:element>
            <xsl:element name="Code">this.current = (charCode &gt;&gt; 6) | 192;</xsl:element>
            <xsl:element name="Code">this._buffer.push((charCode &amp; 63) | 128);</xsl:element>
            <xsl:element name="Code">} else {</xsl:element>
            <xsl:element name="Code">this.current = (charCode &gt;&gt; 12) | 224;</xsl:element>
            <xsl:element name="Code">this._buffer.push(((charCode &gt;&gt; 6) &amp; 63) | 128);</xsl:element>
            <xsl:element name="Code">}}</xsl:element>
            <xsl:element name="Code">return true;</xsl:element>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="$functionBodyElems/Code">
              <xsl:element name="Code">
                <xsl:value-of select="." />
              </xsl:element>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'getCurrent'" />
          <xsl:element name="FunctionBody">
            <xsl:element name="Code">return this.current;</xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>

    <xsl:element name="Class">
      <xsl:attribute name="ClassName" select="'Base64DecodeEnumerator'" />
      <xsl:element name="Super">
        <xsl:attribute name="Has" select="'no'" />
      </xsl:element>
      <xsl:element name="Constructor">
        <xsl:element name="Params">
          <xsl:element name="Param">input</xsl:element>
        </xsl:element>
        <xsl:variable name="constructorBodyElems">
          <xsl:element name="Code">this._input = input;</xsl:element>
          <xsl:element name="Code">this._index = -1;</xsl:element>
          <xsl:element name="Code">this._buffer = [];</xsl:element>
          <xsl:element name="Code">this.current = 64;</xsl:element>
          <xsl:element name="Code">return this;</xsl:element>
        </xsl:variable>
        <xsl:element name="ConstructorBody">
          <xsl:for-each select="$constructorBodyElems/Code">
            <xsl:element name="Code">
              <xsl:value-of select="." />
            </xsl:element>
          </xsl:for-each>
        </xsl:element>
      </xsl:element>
      <xsl:element name="PrototypeChain">
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'moveNext'" />
          <xsl:element name="Params" />
          <xsl:variable name="functionBodyElems">
            <xsl:element name="Code">var byte1, byte2, byte3</xsl:element>
            <xsl:element name="Code">if (this._buffer.length &gt; 0) {</xsl:element>
            <xsl:element name="Code">this.current = this._buffer.shift();</xsl:element>
            <xsl:element name="Code">return true;</xsl:element>
            <xsl:element name="Code">} else if (this._index &gt;= (this._input.length - 1)) {</xsl:element>
            <xsl:element name="Code">this.current = 64;</xsl:element>
            <xsl:element name="Code">return false;</xsl:element>
            <xsl:element name="Code">} else {</xsl:element>
            <xsl:element name="Code">var enc1 = B64.getCodexIndex(this._input.charAt(++this._index));</xsl:element>
            <xsl:element name="Code">var enc2 = B64.getCodexIndex(this._input.charAt(++this._index));</xsl:element>
            <xsl:element name="Code">var enc3;</xsl:element>
            <xsl:element name="Code">if (this._index + 1 &lt; this._input.length)</xsl:element>
            <xsl:element name="Code">enc3 = B64.getCodexIndex(this._input.charAt(++this._index));</xsl:element>
            <xsl:element name="Code">else</xsl:element>
            <xsl:element name="Code">enc3 = 64;</xsl:element>
            <xsl:element name="Code">var enc4;</xsl:element>
            <xsl:element name="Code">if (this._index + 1 &lt; this._input.length)</xsl:element>
            <xsl:element name="Code">enc4 = B64.getCodexIndex(this._input.charAt(++this._index));</xsl:element>
            <xsl:element name="Code">else</xsl:element>
            <xsl:element name="Code">enc4 = 64;</xsl:element>
            <xsl:element name="Code">var byte1 = ((enc1 &amp; 63) &lt;&lt; 2) | ((enc2 &amp; 48) &gt;&gt; 4);</xsl:element>
            <xsl:element name="Code">if (enc3 == 64)</xsl:element>
            <xsl:element name="Code">byte2 = -1;</xsl:element>
            <xsl:element name="Code">else</xsl:element>
            <xsl:element name="Code">byte2 = ((enc2 &amp; 15) &lt;&lt; 4) | ((enc3 &amp; 60) &gt;&gt; 2);</xsl:element>
            <xsl:element name="Code">if (enc4 == 64)</xsl:element>
            <xsl:element name="Code">byte3 = -1;</xsl:element>
            <xsl:element name="Code">else</xsl:element>
            <xsl:element name="Code">byte3 = ((enc3 &amp; 3) &lt;&lt; 6) | ((enc4 &amp; 63));</xsl:element>
            <xsl:element name="Code">this.current = byte1;</xsl:element>
            <xsl:element name="Code">if (byte2 != -1)</xsl:element>
            <xsl:element name="Code">this._buffer.push(byte2);</xsl:element>
            <xsl:element name="Code">if (byte3 != -1)</xsl:element>
            <xsl:element name="Code">this._buffer.push(byte3);</xsl:element>
            <xsl:element name="Code">}</xsl:element>
            <xsl:element name="Code">return true;</xsl:element>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="$functionBodyElems/Code">
              <xsl:element name="Code">
                <xsl:value-of select="." />
              </xsl:element>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'getCurrent'" />
          <xsl:element name="FunctionBody">
            <xsl:element name="Code">return this.current;</xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>
    <xsl:element name="Class">
      <xsl:attribute name="ClassName" select="'Decryptor'" />
      <xsl:element name="Super">
        <xsl:attribute name="Has" select="'no'" />
      </xsl:element>
      <xsl:element name="Constructor">
        <xsl:element name="Params">
          <xsl:element name="Param">ajaxURL</xsl:element>
          <xsl:element name="Param">serverContext</xsl:element>
          <xsl:element name="Param">onDone</xsl:element>
        </xsl:element>
        <xsl:variable name="constructorBodyElems">
          <xsl:element name="Code">decryptor = this;</xsl:element>
          <xsl:element name="Code">this.Keys = null;</xsl:element>
          <xsl:element name="Code">if (onDone)</xsl:element>
          <xsl:element name="Code">this.OnDone = onDone;</xsl:element>
          <xsl:element name="Code">else</xsl:element>
          <xsl:element name="Code">this.OnDone = null;</xsl:element>
          <xsl:element name="Code">this.ClientID = new URLSearchParams(window.location.search).get("ClientID");</xsl:element>
          <xsl:element name="Code">this.TestName = new URLSearchParams(window.location.search).get("IATName");</xsl:element>
          <xsl:element name="Code">this.ServerContext = serverContext;</xsl:element>
          <xsl:element name="Code">this.AjaxURL = ajaxURL;</xsl:element>
          <xsl:element name="Code">this.Lines = new Array();</xsl:element>
          <xsl:element name="Code">this.RootURL = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ":" + window.location.port.toString() : "") + "/" + this.ServerContext + "/";</xsl:element>
          <xsl:element name="Code">this.RequestSrc = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ":" + window.location.port.toString() : "") + "/" + this.ServerContext + "/Resource/" + this.ClientID.toString() + "/" + this.TestName + "/";</xsl:element>
          <xsl:element name="Code">return this;</xsl:element>
        </xsl:variable>
        <xsl:element name="ConstructorBody">
          <xsl:for-each select="$constructorBodyElems/Code">
            <xsl:element name="Code">
              <xsl:value-of select="." />
            </xsl:element>
          </xsl:for-each>
        </xsl:element>
      </xsl:element>

      <xsl:element name="PrototypeChain">
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'fetchKeys'" />
          <xsl:element name="Params">
            <xsl:element name="Param">segmentID</xsl:element>
            <xsl:element name="Param">ajaxURL</xsl:element>
          </xsl:element>
          <xsl:element name="FunctionBody">
            <xsl:element name="Code">this.segmentID = segmentID;</xsl:element>
            <xsl:element name="Code">this.fetchPublicKey(ajaxURL);</xsl:element>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'encryptWithRSA'" />
          <xsl:element name="Params">
            <xsl:element name="Param">rsaJson</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
          <xsl:text>
                var publicKey = BigInt(JSON.parse(rsaJson).publicKeyHex);
                var modulus = BigInt(JSON.parse(rsaJson).modulusHex);
                var value, encValue;
                var ctr1, ctr2;
                var encWords = new Array();
                this.KeyCipherWords = new Array();
                var wordSize = Math.pow(2, 32);
                for (ctr1 = 0; ctr1 &lt; 4; ctr1++) {
                    value = BigInt(Math.floor(Math.random() * wordSize));
                    encValue = value;
                    ctr2 = BigInt(0);
                    while (ctr2 &lt; publicKey) {
                      encValue = (encValue * value) % modulus;
                      ctr2++;
                    }
                    encWords.push(encValue.toString(16));
                    this.KeyCipherWords.push(Number(value));
                }
            var ajaxCall = new AjaxCallv2("/IAT/Admin/Ajax/KeySet", this.RootURL, this.segmentID, "text/json");
            ajaxCall.call(this.processKeys, this, "POST", JSON.stringify(encWords), "text/json");
          </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'fetchPublicKey'" />
          <xsl:element name="Params">
            <xsl:element name="Param">ajaxURL</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
            var ajaxCall = new AjaxCallv2("/IAT/Admin/Ajax/RSA", this.RootURL, this.segmentID, "text/json");
            ajaxCall.call(this.encryptWithRSA, this, "GET");
            </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>


        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'decryptKeys'" />
          <xsl:element name="Params">
            <xsl:element name="Param">keySet</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
      var keyAES = new AES(this.KeyCipherWords);
      var aesAry = new Array();
      var decOut = new Array();
      var ctr = 0;
      for (ctr = 0; ctr &lt; keySet.length; ctr++) {
        decOut = keyAES.decrypt(new Array(keySet[ctr].keyWords[0], keySet[ctr].keyWords[1], keySet[ctr].keyWords[2], keySet[ctr].keyWords[3]));
        aesAry.push(new AES(decOut));
      }
      return aesAry;
            </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'processKeys'" />
          <xsl:element name="Params">
            <xsl:element name="Param">ajaxResult</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
              var encryptedKeys = JSON.parse(ajaxResult);
              this.AESAry = this.decryptKeys(encryptedKeys);
              var ajaxCall = new AjaxCallv2("/IAT/Admin/Ajax/Code", this.RootURL, this.segmentID, "text/json");
              ajaxCall.call(this.processCode, this, "GET");
            </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'processCode'" />
          <xsl:element name="Params">
            <xsl:element name="Param">ajaxDoc</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
      var ctr, declLine, tocLine, globalLine;
      var encCode = JSON.parse(ajaxDoc);
      for (ctr = 0; ctr &lt; encCode.length; ctr++) {
        if (encCode[ctr].Type === "TOC") {
          tocLine = this.decryptLine(encCode[ctr].CL, encCode[ctr].ANDX, encCode[ctr].BNDX, encCode[ctr].Lines);
          eval(window, tocLine);
        }
      }
      for (ctr = 0; ctr &lt; encCode.length; ctr++) {
        if (encCode[ctr].Type == "CODE") 
          this.Lines.push(encCode[ctr].CL, encCode[ctr].ANDX, encCode[ctr].BNDX, encCode[ctr].Lines);
        
      }
      for (ctr = 0; ctr &lt; encCode.length; ctr++) {
        if ((encCode[ctr].Type == "CONSTRUCTOR") || (encCode[ctr].Type == "DECLARATION")) {
          declLine = this.decryptLine(encCode[ctr].CL, encCode[ctr].ANDX, encCode[ctr].BNDX, encCode[ctr].Lines);
          eval.call(window, declLine);
        }
      }
      for (ctr = 0; ctr &lt; encCode.length; ctr++) {
        if ((encCode[ctr].Type == "GLOBAL_DECLARATION") || (encCode[ctr].Type == "GLOBAL_CODE")) {
          declLine = this.decryptLine(encCode[ctr].CL, encCode[ctr].ANDX, encCode[ctr].BNDX,  encCode[ctr].Lines);
          eval.call(window, declLine);
        }
      }

      if (this.OnDone != null)
        this.OnDone.call();
    </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'decryptNode'" />
          <xsl:element name="Params">
            <xsl:element name="Param">lineXML</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
      return this.decryptLine(lineXML.CL, lineXML.ANDX, lineXML.BNDX, lineXML.SegmentNodes);
    </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'decryptLine'" />
          <xsl:element name="Params">
            <xsl:element name="Param">cl</xsl:element>
            <xsl:element name="Param">andx</xsl:element>
            <xsl:element name="Param">bndx</xsl:element>
            <xsl:element name="Param">encCode</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
      var aesNum = andx ^ bndx;
      var ctr, ctr2, codeNode, code;
      var segments = [ B64.decode(encCode[0]), B64.decode(encCode[1]), B64.decode(encCode[2]), B64.decode(encCode[3]) ];
      var wordSegments = new Array();
      for (ctr = 0; ctr &lt; 4; ctr++)
        wordSegments.push(new Array());
      var segLen = segments[0].length;
      var outWordAry = new Array();
      for (ctr = 0; ctr &lt; segLen; ctr++)  {
        var inWordAry = [ segments[0][ctr], segments[1][ctr], segments[2][ctr], segments[3][ctr]];
        outWordAry = outWordAry.concat(this.AESAry[aesNum].decrypt(inWordAry));
      }
      var result = new String();
      var segCtr = 0, wordCtr = 0;
      for (ctr = 0; ctr &lt; (cl &gt;&gt; 2) + 1; ctr++) {
        var w = outWordAry[ctr];
        for (ctr2 = 0; ctr2 &lt; 4; ctr2++) {
          if ((ctr &lt;&lt; 2) + ctr2 &lt; cl)
            result += String.fromCharCode((w &amp; (0xFF &lt;&lt; (24 - (8 * ctr2)))) >>> (24 - (8 * ctr2)));
        }
      }            
      return result;
    </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'getTOCEntry'" />
          <xsl:element name="Params">
            <xsl:element name="Param">name</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
              return TOC[name];
            </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'getLine'" />
          <xsl:element name="Params">
            <xsl:element name="Param">ndx</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
              return this.Lines[ndx];
            </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

      </xsl:element>
    </xsl:element>

    <xsl:element name="Class">
      <xsl:attribute name="ClassName" select="'SubFunct'" />
      <xsl:element name="Super">
        <xsl:attribute name="Has" select="'no'" />
      </xsl:element>
      <xsl:element name="Constructor">
        <xsl:element name="Params">
          <xsl:element name="Param">name</xsl:element>
          <xsl:element name="Param">childFunct</xsl:element>
        </xsl:element>
        <xsl:variable name="constructorCode">
          <xsl:text>
    this.Evaluated = false;
    this.Decryptor = decryptor;
    this.ndx1 = TOC[name].ndx1;
    this.ndx2 = TOC[name].ndx2;
    this.ChildFunct = childFunct;
    return this;
  </xsl:text>
        </xsl:variable>
        <xsl:element name="ConstructorBody">
          <xsl:for-each select="tokenize($constructorCode, '&#x0A;')">
            <xsl:if test="string-length(normalize-space(.)) gt 0">
              <xsl:element name="Code">
                <xsl:value-of select="normalize-space(.)" />
              </xsl:element>
            </xsl:if>
          </xsl:for-each>
        </xsl:element>
      </xsl:element>

      <xsl:element name="PrototypeChain">
        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'fEval'" />
          <xsl:element name="Params">
            <xsl:element name="Param">param</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
<!--          _to_.refresh();  -->
            var f;
            var code = this.Decryptor.decryptLine(this.Decryptor.getLine(this.ndx1 ^ this.ndx2));
            if (this.ChildFunct)
              f = eval("(function(_l) { " + code + " })");
            else           
              f = eval("(function(_p) { " + code + " })");
          
          return f.call(window, param);
      </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>

        <xsl:element name="Function">
          <xsl:attribute name="FunctionName" select="'cEval'" />
          <xsl:element name="Params">
            <xsl:element name="Param">t</xsl:element>
            <xsl:element name="Param">param</xsl:element>
          </xsl:element>
          <xsl:variable name="functionCode">
            <xsl:text>
<!--          _to_.refresh(); -->
            var code = this.Decryptor.decryptLine(this.Decryptor.getLine(this.ndx1 ^ this.ndx2));
            var f;
            if (this.ChildFunct) 
              f = eval("(function(_l) { " + code + " })");
            else 
              f = eval("(function(_p) { " + code + " })");
          
          return f.call(t, param);
      </xsl:text>
          </xsl:variable>
          <xsl:element name="FunctionBody">
            <xsl:for-each select="tokenize($functionCode, '&#x0A;')">
              <xsl:if test="string-length(normalize-space(.)) gt 0">
                <xsl:element name="Code">
                  <xsl:value-of select="normalize-space(.)" />
                </xsl:element>
              </xsl:if>
            </xsl:for-each>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:variable>

  <xsl:variable name="GlobalCode">
    <xsl:element name="Code">B64 = new Base64();</xsl:element>
  </xsl:variable>

  <xsl:template match="ConfigFile">
    <xsl:element name="CodeFile">
      <xsl:element name="VarEntries">
        <xsl:copy-of select="$GlobalAbbreviations" />
      </xsl:element>
      <xsl:element name="Classes">
        <xsl:for-each select="$Classes/Class">
          <xsl:variable name="nodeName" select="name()" />
          <xsl:element name="{$nodeName}">
            <xsl:for-each select="attribute::*">
              <xsl:copy-of select="." />
            </xsl:for-each>
            <xsl:attribute name="ClassPrefix" select="$classPrefix" />
            <xsl:attribute name="ClassFunctionPrefix" select="$classFunctionPrefix" />
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