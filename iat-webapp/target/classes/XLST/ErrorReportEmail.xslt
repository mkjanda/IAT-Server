<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mine="http://www.iatsoftware.net"
                version="2.0"
                exclude-result-prefixes="xs mine">

    <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"
                indent="yes" />

    <xsl:variable name="root" select="/" />

    <xsl:function name="mine:getStyleString">
        <xsl:param name="styleVariable" />
        <xsl:for-each select="$styleVariable/StyleRule">
            <xsl:if test="position() eq last()">
                <xsl:value-of select="concat(Name, ': ', Value, ';')" />
            </xsl:if>
            <xsl:if test="position() ne last()">
                <xsl:value-of select="concat(Name, ': ', Value, '; ')" />
            </xsl:if>
        </xsl:for-each>
    </xsl:function>

    <xsl:variable name="header">
        <xsl:element name="Line">
            <xsl:value-of select="concat('User name: ', //UserName)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('User email: ', //UserEmail)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Client ID: ', //ClientID)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Product code: ', //ProductCode)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Error timestamp: ', //Timestamp)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Product Version: ', //Version)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Save File Version: ', //ClientErrorReport/SaveFileVersion)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('File opened at: ', //ClientErrorReport/TimeOpened)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Errors Reported: ', //ClientErrorReport/ErrorsReported)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Error Count: ', //ClientErrorReport/ErrorCount)" />
        </xsl:element>
        <xsl:element name="Line">
            <xsl:value-of select="concat('Message: ', //ClientMessage)" />
        </xsl:element>
    </xsl:variable>

    <xsl:variable name="lineStyle">
        <StyleRule>
            <Name>font-family</Name>
            <Value>calibri, sans-serif</Value>
        </StyleRule>
        <StyleRule>
            <Name>font-size</Name>
            <Value>16px</Value>
        </StyleRule>
        <StyleRule>
            <Name>color</Name>
            <Value>#000</Value>
        </StyleRule>
        <StyleRule>
            <Name>margin</Name>
            <Value>0px</Value>
        </StyleRule>
    </xsl:variable>

    <xsl:variable name="stackTraceLineStyle">
        <StyleRule>
            <Name>font-family</Name>
            <Value>calibri, sans-serif</Value>
        </StyleRule>
        <StyleRule>
            <Name>font-size</Name>
            <Value>14px</Value>
        </StyleRule>
        <StyleRule>
            <Name>color</Name>
            <Value>#000</Value>
        </StyleRule>
        <StyleRule>
            <Name>margin</Name>
            <Value>0px 50px</Value>
        </StyleRule>
        <StyleRule>
            <Name>text-indent</Name>
            <Value>-25px</Value>
        </StyleRule>
    </xsl:variable>


    <xsl:template name="HistoryEntry">
        <xsl:param name="he" />
        <xsl:call-template name="WriteLine">
            <xsl:with-param name="line" select="concat('Time Opened: ', $he/TimeOpened)" />
            <xsl:with-param name="style" select="$stackTraceLineStyle" />
        </xsl:call-template>
        <xsl:call-template name="WriteLine">
            <xsl:with-param name="line" select="concat('Product key: ', $he/ProductKey)" />
            <xsl:with-param name="style" select="$stackTraceLineStyle" />
        </xsl:call-template>
        <xsl:call-template name="WriteLine">
            <xsl:with-param name="line" select="concat('Error count: ', $he/ErrorCount)" />
            <xsl:with-param name="style" select="$stackTraceLineStyle" />
        </xsl:call-template>
        <xsl:call-template name="WriteLine">
            <xsl:with-param name="line" select="concat('Errors reported: ', $he/ErrorsReported)" />
            <xsl:with-param name="style" select="$stackTraceLineStyle" />
        </xsl:call-template>
        <xsl:call-template name="WriteLine">
            <xsl:with-param name="line" select="concat('Save file version: ', $he/SaveFileVersion)" />
            <xsl:with-param name="style" select="$stackTraceLineStyle" />
        </xsl:call-template>
    </xsl:template>
    
    <xsl:template name="WriteLine">
        <xsl:param name="line" />
        <xsl:param name="style" />
        <xsl:element name="p">
            <xsl:attribute name="style" select='mine:getStyleString($style)' />
            <xsl:value-of disable-output-escaping="yes" select="$line" />
        </xsl:element>
    </xsl:template>

    <xsl:template match="/ClientErrorReport">
        <xsl:element name="html">
            <xsl:element name="head">
                <xsl:value-of select='" "' />
            </xsl:element>
            <xsl:element name="body">
                <xsl:for-each select="$header/Line">
                    <xsl:call-template name="WriteLine">
                        <xsl:with-param name="line" select="." />
                        <xsl:with-param name="style" select="$lineStyle" />
                    </xsl:call-template>
                </xsl:for-each>
                <br />
                <xsl:call-template name="WriteLine">
                    <xsl:with-param name="line" select="'History:'" />
                    <xsl:with-param name="style" select="$lineStyle" />
                </xsl:call-template>
                <table style="width: 1100px; border-collapse: collapse; border: none; margin-left: 20px;">
                    <xsl:variable name="nrows" select="floor(count($root//HistoryEntry) div 3)" />
                    <xsl:for-each select="1 to xs:integer($nrows)">
                        <xsl:variable name="row" select="." />
                        <tr>
                            <xsl:if test="$row eq $nrows">
                                <xsl:for-each select="1 to (count($root//HistoryEntry) mod 3)">
                                    <xsl:variable name="col" select="." />
                                    <td style="padding-left: 10px; padding-bottom: 10px; width: 350px; border: none;">
                                        <xsl:call-template name="HistoryEntry">
                                            <xsl:with-param name="he" select="$root//HistoryEntry[($row - 1) * 3 + $col]" />
                                        </xsl:call-template>
                                    </td>
                                </xsl:for-each>
                            </xsl:if>
                            <xsl:if test="$row ne $nrows">
                                <xsl:for-each select="1 to 3">
                                    <xsl:variable name="col" select="." />
                                    <td style="padding-left: 10px; padding-bottom: 10px; width: 350px; border: none;">
                                        <xsl:call-template name="HistoryEntry">
                                            <xsl:with-param name="he" select="$root//HistoryEntry[($row - 1) * 3 + $col]" />
                                        </xsl:call-template>
                                    </td>
                                </xsl:for-each>
                            </xsl:if>
                        </tr>
                    </xsl:for-each>
                    </table>
                <xsl:element name="br" />
                <xsl:call-template name="WriteLine">
                    <xsl:with-param name="line" select="concat('Error message: ', //Exception/ExceptionMessage)" />
                    <xsl:with-param name="style" select="$lineStyle" />
                </xsl:call-template>
                <xsl:element name="p" />
                <xsl:call-template name="WriteLine">
                    <xsl:with-param name="line" select="'Stack trace:'" />
                    <xsl:with-param name="style" select="$lineStyle" />
                </xsl:call-template>
                <xsl:for-each select="//Exception/StackTraceElement">
                    <xsl:call-template name="WriteLine">
                        <xsl:with-param name="line" select="." />
                        <xsl:with-param name="style" select="$stackTraceLineStyle" />
                    </xsl:call-template>
                </xsl:for-each>
                <xsl:for-each select="//Exception/InnerException">
                    <xsl:call-template name="WriteLine">
                        <xsl:with-param name="line" select="concat('Caused by . . .', ExceptionMessage)" />
                        <xsl:with-param name="style" select="$lineStyle" />
                    </xsl:call-template>
                    <xsl:for-each select="StackTraceElement">
                        <xsl:call-template name="WriteLine">
                            <xsl:with-param name="line" select="." />
                            <xsl:with-param name="style" select="$stackTraceLineStyle" />
                        </xsl:call-template>
                    </xsl:for-each>
                </xsl:for-each>
                <br />
                <xsl:call-template name="WriteLine">
                    <xsl:with-param name="line" select="'Activity Log'" />
                    <xsl:with-param name="style" select="$lineStyle" />
                </xsl:call-template>
                <xsl:for-each select="//Event">
                    <xsl:call-template name="WriteLine">
                        <xsl:with-param name="line" select="LogMessage" />
                        <xsl:with-param name="style" select="$stackTraceLineStyle" />
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>