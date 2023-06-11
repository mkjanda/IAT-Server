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


	<xsl:variable name="GlobalCode">
		<xsl:element name="Declaration">
			<xsl:value-of select="concat('NumImages = ', count(//IATDisplayItem))"/>
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

		<xsl:variable name="Globals">
		<xsl:for-each select="$GlobalCode/Declaration">
			<xsl:variable name="ndx" select="position()" />
			<xsl:analyze-string select="." regex="^(var\s*)?([A-Za-z_][A-Za-z0-9_]*)(\s*=\s*)?(.+)?">
				<xsl:matching-substring>
					<xsl:element name="Global">
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



		<xsl:template match="ConfigFile">
			<xsl:element name="Globals">
					<xsl:copy-of select="$Globals" />
			</xsl:element>
		</xsl:template>    
</xsl:stylesheet>
