<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                exclude-result-prefixes="xs">

    <xsl:output method="xml" indent="yes"/>

    <xsl:variable name="root" select="/" />


	<xsl:variable name="globalVariablePrefix">
		<xsl:value-of select="'gv'"/>
	</xsl:variable>

	<xsl:template match="//GlobalVars">
		<xsl:element name="Globals">
			<xsl:element name="GlobalDecl">
				<xsl:value-of select="GlobalDecl" />
			</xsl:element>
			<xsl:for-each select="GlobalVarNameTable/VarTableEntry">
				<xsl:element name="Global">
					<xsl:element name="OrigName">
						<xsl:value-of select="OrigName" />
					</xsl:element>
					<xsl:element name="NewName">
						<xsl:value-of select="NewName" />
					</xsl:element>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<xsl:template match="/CodeFile">
		<xsl:apply-templates select="GlobalVars" />
	</xsl:template>
</xsl:stylesheet>
