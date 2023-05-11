<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0" exclude-result-prefixes="xs">

	<xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8" indent="yes" />

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

	<xsl:variable name="orderTableStyles">
		<xsl:element name="Table">
			<xsl:value-of select="'border: none; border-collapse: collapse; margin-left: 400px; margin-bottom: 30px;'" />
		</xsl:element>
		<xsl:element name="ItemDescCell">
			<xsl:value-of select="'font-family: Arial, Helvetica, sans-serif; font-size: 1em; color: #000; margin-top: 0px; margin-bottom: 0px; padding-bottom: 0px; padding-top: 0px; font-style: italic; text-align: right; padding-right: 1em;'" />
		</xsl:element>
		<xsl:element name="ItemPriceCell">
			<xsl:value-of select="'font-family: Arial, Helvetica, sans-serif; font-size: 1em; margin-top: 0px; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: #000;'" />
		</xsl:element>
		<xsl:element name="TotalRow">
			<xsl:value-of select="'margin-top: 15px; margin-top: 0px; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; height: 2em; min-height: 2em; vertical-align: bottom;'" />
		</xsl:element>
		<xsl:element name="TotalCell">
			<xsl:value-of select="'font-family: Arial, Helvetica, sans-serif; font-size: 1em; color: #000; margin-top: 0px; padding-top: 0px; font-weight: bold; border-top: 1px solid #000;'" />
		</xsl:element>
	</xsl:variable>


	<xsl:variable name="paraIndent">
		<StyleElem>
			<ElemName>text-indent</ElemName>
			<ElemValue>25px</ElemValue>
		</StyleElem>
	</xsl:variable>


	<xsl:variable name="openingParagraphs">
		<Paragraph>
			<xsl:variable name="purchase">
				<xsl:if test="count($root//Resource) eq 3">
					<xsl:value-of select="concat($root//Resource[@Type eq 'Iat']/Quantity, ' additional IATs, ', $root//Resource[@Type eq 'DiskSpace']/Quantity,
            'MB of disk space, and ', $root//Resource[@Type eq 'Administration']/Quantity, ' test administrations')" />
				</xsl:if>
				<xsl:if test="count($root//Resource) eq 2">
					<xsl:if test="count($root//Resource[@Type eq 'Administration']) eq 0">
						<xsl:value-of select="concat($root//Resource[@Type eq 'Iat']/Quantity, ' additional IATs and ', $root//Resource[@Type eq 'DiskSpace']/Quantity,
              'MB of disk space')" />
					</xsl:if>
					<xsl:if test="count($root//Resource[@Type eq 'DiskSpace']) eq 0">
						<xsl:value-of select="concat($root//Resource[@Type eq 'Iat']/Quantity, ' additional IATs and ', $root//Resource[@Type eq 'Administration']/Quantity,
              ' test administrations')" />
					</xsl:if>
					<xsl:if test="count($root//Resource[@Type eq 'Iat']) eq 0">
						<xsl:value-of select="concat($root//Resource[@Type eq 'DiskSpace']/Quantity, 'MB of disk space and ', $root//Resource[@Type eq 'Administration']/Quantity,
              ' test administrations')" />
					</xsl:if>
				</xsl:if>
				<xsl:if test="count($root//Resource) eq 1">
					<xsl:if test="count($root//Resource[@Type eq 'Administration']) eq 1">
						<xsl:value-of select="concat($root//Resource[@Type eq 'Administration']/Quantity, ' test administrations')" />
					</xsl:if>
					<xsl:if test="count($root//Resource[@Type eq 'Iat']) eq 1">
						<xsl:value-of select="concat($root//Resource[@Type eq 'Iat']/Quantity, ' additional IATs')" />
					</xsl:if>
					<xsl:if test="count($root//Resource[@Type eq 'DiskSpace']) eq 1">
						<xsl:value-of select="concat($root//Resource[@Type eq 'DiskSpace']/Quantity, 'MB of disk space')" />
					</xsl:if>
				</xsl:if>
			</xsl:variable>
			<xsl:text>Thank you for your purchase of </xsl:text>
			<xsl:value-of select="$purchase" />
			<xsl:text>. 
      These resources have already been credited to your account. If you need to download the test design program again, you
      may do so at </xsl:text>
			<xsl:element name="a">
				<xsl:attribute name="href" select="concat('http://www.iatsoftware.net/RequestDownload.html?DownloadPassword=', $root//DownloadPassword)" />
				<xsl:value-of select="concat('http://www.iatsoftware.net/RequestDownload.html?DownloadPassword=', $root//DownloadPassword)" />
			</xsl:element>
			<xsl:text>. Your product key, should you need to activate the software, is </xsl:text>
			<xsl:value-of select="$root//ProductKey" />
		</Paragraph>
	</xsl:variable>

	<xsl:variable name="closingParagraphs">
		<Paragraph>
			<xsl:text>If you have any questions regarding this purchase, the use of your product, or encounter any errors with the software,
       please do not hesitate to contact us at </xsl:text>
			<a href="mailto:admin@iatsoftware.net">admin@iatsoftware.net</a>
		</Paragraph>
	</xsl:variable>

	<xsl:variable name="signature">
		<Paragraph>Michael Janda</Paragraph>
		<Paragraph>IAT Software</Paragraph>
	</xsl:variable>

	<xsl:variable name="root" select="/" />

	<xsl:template match="PurchaseConfirmation">
		<xsl:element name="html">
			<xsl:element name="head">
				<xsl:value-of select="'&#x0A;'" />
			</xsl:element>
			<xsl:element name="body">
				<div style="text-align: center;">
					<div style="text-align: left; width: 800px; margin: 0px auto;">
						<img style="margin-top: 30px; margin-bottom: 30px" src="http://www.iatsoftware.net/logo_small.png" />
						<xsl:call-template name="WriteParagraph">
							<xsl:with-param name="text" select="concat(//FirstName, ' ', //LastName, ',')" />
							<xsl:with-param name="indent" select="'no'" />
						</xsl:call-template>
						<xsl:for-each select="$openingParagraphs/Paragraph">
							<xsl:call-template name="WriteParagraph">
								<xsl:with-param name="text" select="node()" />
								<xsl:with-param name="indent" select="'yes'" />
							</xsl:call-template>
							<xsl:call-template name="WriteParagraph">
								<xsl:with-param name="text" select="'Order Summary:'" />
								<xsl:with-param name="indent" select="'no'" />
							</xsl:call-template>
						</xsl:for-each>
						<xsl:element name="table">
							<xsl:attribute name="style" select="$orderTableStyles/Table" />
							<xsl:if test="count($root//Resource[@Type eq 'Iat']) eq 1">
								<tr>
									<xsl:element name="td">
										<xsl:attribute name="style" select="$orderTableStyles/ItemDescCell" />
										<xsl:if test="xs:integer($root//Resource[@Type eq 'Iat']/Quantity) eq 1">
											<xsl:value-of select="'1 IAT'" />
										</xsl:if>
										<xsl:if test="xs:integer($root//Resource[@Type eq 'Iat']/Quantity) gt 1">
											<xsl:value-of select="concat($root//Resource[@Type eq 'Iat']/Quantity, ' IATs')" />
										</xsl:if>
									</xsl:element>
									<xsl:element name="td">
										<xsl:attribute name="style" select="$orderTableStyles/ItemPriceCell" />
										<xsl:value-of select="concat('$', $root//Resource[@Type eq 'Iat']/Price)" />
									</xsl:element>
								</tr>
							</xsl:if>
							<xsl:if test="count($root//Resource[@Type eq 'DiskSpace']) eq 1">
								<tr>
									<xsl:element name="td">
										<xsl:attribute name="style" select="$orderTableStyles/ItemDescCell" />
										<xsl:value-of select="concat($root//Resource[@Type eq 'DiskSpace']/Quantity, 'MB of Disk Space')" />
									</xsl:element>
									<xsl:element name="td">
										<xsl:attribute name="style" select="$orderTableStyles/ItemPriceCell" />
										<xsl:value-of select="concat('$', $root//Resource[@Type eq 'DiskSpace']/Price)" />
									</xsl:element>
								</tr>
							</xsl:if>
							<xsl:if test="count($root//Resource[@Type eq 'Administration']) eq 1">
								<tr>
									<xsl:element name="td">
										<xsl:attribute name="style" select="$orderTableStyles/ItemDescCell" />
										<xsl:value-of select="concat($root//Resource[@Type eq 'Administration']/Quantity, ' Test Administrations')" />
									</xsl:element>
									<xsl:element name="td">
										<xsl:attribute name="style" select="$orderTableStyles/ItemPriceCell" />
										<xsl:value-of select="concat('$', $root//Resource[@Type eq 'Administration']/Price)" />
									</xsl:element>
								</tr>
							</xsl:if>
							<xsl:element name="tr">
								<xsl:attribute name="style" select="$orderTableStyles/TotalRow" />
								<xsl:element name="td" />
								<xsl:element name="td">
									<xsl:attribute name="style" select="$orderTableStyles/TotalCell" />
									<xsl:value-of select="concat('$', $root//Total)" />
								</xsl:element>
							</xsl:element>
						</xsl:element>
						<xsl:call-template name="WriteParagraph">
							<xsl:with-param name="text" select="concat('Billed to: ', $root//CardholderFirstName, ' ', $root//CardholderLastName)" />
							<xsl:with-param name="indent" select="'no'" />
						</xsl:call-template>
						<xsl:call-template name="WriteParagraph">
							<xsl:with-param name="text" select="concat($root//CreditCard, ' xxxxxxxxxxxx', $root//EndingCardDigits, ' Expires: ', $root//CardExpMonth, '/', $root//CardExpYear)" />
							<xsl:with-param name="indent" select="'yes'" />
						</xsl:call-template>
						<br />
						<xsl:for-each select="$closingParagraphs/Paragraph">
							<xsl:call-template name="WriteParagraph">
								<xsl:with-param name="text" select="node()" />
								<xsl:with-param name="indent" select="'yes'" />
							</xsl:call-template>
						</xsl:for-each>
						<br />
						<xsl:for-each select="$signature/Paragraph">
							<xsl:call-template name="WriteParagraph">
								<xsl:with-param name="text" select="node()" />
								<xsl:with-param name="indent" select="'no'" />
							</xsl:call-template>
						</xsl:for-each>

					</div>
				</div>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="WriteParagraph">
		<xsl:param name="text" />
		<xsl:param name="indent" />
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
			<xsl:copy-of select="$text" />
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>