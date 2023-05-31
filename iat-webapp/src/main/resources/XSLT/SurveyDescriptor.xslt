<xsl:stylesheet
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    exclude-result-prefixes="xs">
  <xsl:output method="text" encoding="utf-8" indent="no" />
  <xsl:variable name="root" select="/" />

  <xsl:variable name="itemTypeValues" >
    <ResponseTypes>
      <ResponseType>Boolean</ResponseType>
      <ResponseType>Bounded Length</ResponseType>
      <ResponseType>Bounded Number</ResponseType>
      <ResponseType>Date</ResponseType>
      <ResponseType>Fixed Digit</ResponseType>
      <ResponseType>Instruction</ResponseType>
      <ResponseType>Likert</ResponseType>
      <ResponseType>Multiple Selection</ResponseType>
      <ResponseType>Multiple Choice</ResponseType>
      <ResponseType>Regular Expression</ResponseType>
      <ResponseType>Weighted Multiple Choice</ResponseType>
    </ResponseTypes>
  </xsl:variable>

  <xsl:template match="Survey" >
    <xsl:variable name="surveyItemTypes" >
      <xsl:if test="count(SurveyItem/child::*[@Type ne 'Instruction']) eq 0" >
        <xsl:value-of select="0" />
      </xsl:if>
      <xsl:if test="count(SurveyItem/child::*[@Type ne 'Instruction']) ne 0" >
        <xsl:for-each select="SurveyItem/child::*[@Type ne 'Instruction']" >
          <xsl:variable name="type" select = "@Type" />
          <xsl:value-of select="count($itemTypeValues/ResponseTypes/ResponseType[. eq $type]/preceding-sibling::ResponseType)" />
        </xsl:for-each>
      </xsl:if>
    </xsl:variable>
    <xsl:value-of select="$surveyItemTypes" />
  </xsl:template>
</xsl:stylesheet>