<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                exclude-result-prefixes="xs">
  
  <xsl:output method="xml" encoding="utf-8" indent="yes" />
  
  <xsl:template match="/CodeFile">
    <xsl:copy-of select="//GlobalVarNameTable" />
  </xsl:template>
</xsl:stylesheet>