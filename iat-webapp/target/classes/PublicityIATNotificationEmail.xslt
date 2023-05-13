<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mine="http://www.iatsoftware.net"
                version="2.0"
                exclude-result-prefixes="xs mine">

  <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"
               indent="yes" />

  <xsl:variable name="paragraphStyle">
    <StyleElem>
      <ElemName>font-family</ElemName>
      <ElemValue>Arial, Helvetica, sans-serif</ElemValue>
    </StyleElem>
    <StyleElem>
      <ElemName>font-size</ElemName>
      <ElemValue>1em</ElemValue>
    </StyleElem>
    <StyleElem>
      <ElemName>color</ElemName>
      <ElemValue>#000</ElemValue>
    </StyleElem>
    <StyleElem>
      <ElemName>margin-left</ElemName>
      <ElemValue>2em</ElemValue>
    </StyleElem>
    <StyleElem>
      <ElemName>margin-right</ElemName>
      <ElemValue>2em</ElemValue>
    </StyleElem>
  </xsl:variable>



  <xsl:variable name="paraIndent">
    <StyleElem>
      <ElemName>text-indent</ElemName>
      <ElemValue>25px</ElemValue>
    </StyleElem>
  </xsl:variable>

  <xsl:function name="mine:closeInTag">
    <xsl:param name="tagName" />
    <xsl:param name="text" />
    <xsl:value-of select="concat('&#x3c;', $tagName, '&#x3e;', $text, '&#x3c;/', $tagName, '&#x3e;')" />
  </xsl:function>


  <xsl:variable name="openingParagraphs">
    <Paragraph>
      <xsl:text>A publicity IAT has been uploaded by </xsl:text>
      <xsl:value-of select="concat($root//UserTitle, ' ', $root//UserFirstName, ' ', $root//UserLastName)" />
      <xsl:text> with product key </xsl:text>
      <xsl:value-of select="$root//ProductKey" />
      <xsl:text>. The test link is, </xsl:text>
      <xsl:value-of select="concat('&lt;a href=&quot;', $root//IatLink, '&quot;&gt;', $root//IatLink, '&lt;/a&gt;')" />
      <xsl:text> This test has been alotted </xsl:text>
      <xsl:value-of select="$root//PublicityAdministrations" />
      <xsl:text> administrations and the user has been alotted </xsl:text>
      <xsl:value-of select="$root//PooledAdministrations" />
      <xsl:text> administrations.</xsl:text>
    </Paragraph>
  </xsl:variable>

  <xsl:variable name="root" select="/" />

  <xsl:template match="PublicityIatEmail">
    <xsl:element name="html">
      <xsl:element name="head">
        <xsl:value-of select="'&#x0A;'" />
      </xsl:element>
      <xsl:element name="body">
        <div style="text-align: center;">
          <div style="text-align: left; width: 800px; margin: 0px auto;">
            <img style="margin-top: 30px; margin-bottom: 30px" src="http://www.iatsoftware.net/logo_small.png" />
            <xsl:for-each select="$openingParagraphs/Paragraph">
              <xsl:call-template name="WriteParagraph">
                <xsl:with-param name="textNode" select="node()" />
                <xsl:with-param name="indent" select="'yes'" />
              </xsl:call-template>
            </xsl:for-each>
          </div>
        </div>
      </xsl:element>
    </xsl:element>
  </xsl:template>

  <xsl:template name="WriteParagraph">
    <xsl:param name="textNode" />
    <xsl:param name="indent" />
    <xsl:variable name="text">
      <xsl:value-of disable-output-escaping="yes" select="$textNode" />
    </xsl:variable>
    <xsl:element name="p">
      <xsl:variable name="styleAttr">
        <xsl:variable name="styleRules">
          <xsl:for-each select="$paragraphStyle/StyleElem">
            <xsl:element name="StyleRule">
              <xsl:value-of select="concat(ElemName, ': ', ElemValue, ';')" />
            </xsl:element>
          </xsl:for-each>
          <xsl:if test="$indent eq 'yes'">
            <xsl:element name="StyleRule">
              <xsl:value-of select="concat($paraIndent//ElemName, ': ', $paraIndent//ElemValue, ';')" />
            </xsl:element>
          </xsl:if>
        </xsl:variable>
        <xsl:value-of select="string-join($styleRules/StyleRule, ' ')" />
      </xsl:variable>
      <xsl:attribute name="style" select="$styleAttr" />
      <xsl:value-of disable-output-escaping="yes" select="$text" />
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>