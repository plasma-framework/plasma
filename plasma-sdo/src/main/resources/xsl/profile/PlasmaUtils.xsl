<?xml version="1.0" encoding="UTF-8"?>
<!--
-->
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:lxslt="http://xml.apache.org/xslt"
  xmlns:cfg="http://www.servicelabs.org/plasma/config">


<xsl:template name="emitHeader">
	<xsl:text>//==============================================================================</xsl:text>
	<xsl:text>&#13;//                                                          </xsl:text>
	<xsl:text>&#13;// This software artifact was generated using PlasmaSDO    </xsl:text>
	<xsl:text>&#13;// provisioning tools.                                      </xsl:text>
	<xsl:text>&#13;//                                                          </xsl:text>
	<xsl:text>&#13;//==============================================================================</xsl:text>
</xsl:template>


<xsl:template name="findPackageName">

    <xsl:param name="uri" />
    <xsl:param name="model-uri" />

    <xsl:variable name="package-name">
	    <xsl:choose>
	        <xsl:when test="$uri != ''">
	            <xsl:variable name="entity-package-name">
				    <xsl:value-of select="document($cfg)/cfg:PlasmaConfiguration/SDO/Namespace[@uri = $uri]/Provisioning/@packageName"/>
                </xsl:variable>	            
	            <xsl:if test="$entity-package-name = ''">
                    <xsl:message terminate="yes">could not find SDO namespace definition in Plasma Configuration for package level uri '<xsl:value-of select="$uri"/>'</xsl:message>
	            </xsl:if>
	            <xsl:value-of select="$entity-package-name"/>
	        </xsl:when>
	        <xsl:otherwise>
	            <xsl:variable name="model-package-name">
				    <xsl:value-of select="document($cfg)/cfg:PlasmaConfiguration/SDO/Namespace[@uri = $model-uri]/Provisioning/@packageName"/>
                </xsl:variable>
	            <xsl:if test="$model-package-name = ''">
                    <xsl:message terminate="yes">could not find SDO namespace definition in Plasma Configuration for model level uri '<xsl:value-of select="$uri"/>'</xsl:message>
	            </xsl:if>
	            <xsl:value-of select="$model-package-name"/>
	        </xsl:otherwise>
	    </xsl:choose>
    </xsl:variable>
    
    <xsl:value-of select="$package-name"/>
</xsl:template>

<xsl:template name="findServicePackageName">

    <xsl:param name="service" />
    <xsl:param name="uri" />
    <xsl:param name="model-uri" />

    <xsl:variable name="package-name">
        <xsl:choose>
            <xsl:when test="$uri != ''">
                <xsl:variable name="entity-package-name">
                    <xsl:value-of select="document($cfg)/cfg:PlasmaConfiguration/SDO/DataAccessServices/DataAccessServiceConfiguration[@name=$service]/Provisioning[@namespaceUri = $uri]/@packageName"/>
                </xsl:variable>             
                <xsl:if test="$entity-package-name = ''">
                    <xsl:message terminate="yes">could not find SDO <xsl:value-of select="$service"/> Data Access Service definition in Plasma Configuration for package level uri '<xsl:value-of select="$uri"/>'</xsl:message>
                </xsl:if>
                <xsl:value-of select="$entity-package-name"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="model-package-name">
                    <xsl:value-of select="document($cfg)/cfg:PlasmaConfiguration/SDO/DataAccessServices/DataAccessServiceConfiguration[@name=$service]/Provisioning[@namespaceUri = $uri]/@packageName"/>
                </xsl:variable>
                <xsl:if test="$model-package-name = ''">
                    <xsl:message terminate="yes">could not find SDO <xsl:value-of select="$service"/> Data Access Service definition in Plasma Configuration for model level uri '<xsl:value-of select="$uri"/>'</xsl:message>
                </xsl:if>
                <xsl:value-of select="$model-package-name"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
    
    <xsl:value-of select="$package-name"/>
</xsl:template>


<xsl:template name="findConstantsPackageName">

    <xsl:param name="uri" />
    <xsl:param name="model-uri" />

    <xsl:variable name="package-name">
	    <xsl:choose>
	        <xsl:when test="$uri != ''">
	            <xsl:variable name="entity-package-name">
				    <xsl:variable name="test-package-name" select="document($cfg)/cfg:PlasmaConfiguration/SDO/Namespace[@uri = $uri]/Provisioning/@constantsPackageName"/>
				    <xsl:choose>
				        <xsl:when test="$test-package-name != ''">
				            <xsl:value-of select="$test-package-name"/>
				        </xsl:when>
				        <xsl:otherwise>
				            <xsl:value-of select="document($cfg)/cfg:PlasmaConfiguration/SDO/Namespace[@uri = $uri]/Provisioning/@packageName"/>
				        </xsl:otherwise>
				    </xsl:choose>
                </xsl:variable>
	            
	            <xsl:if test="$entity-package-name = ''">
                    <xsl:message terminate="yes">could not find SDO namespace definition in Plasma Configuration for package level uri '<xsl:value-of select="$uri"/>'</xsl:message>
	            </xsl:if>
	            <xsl:value-of select="$entity-package-name"/>
	        </xsl:when>
	        <xsl:otherwise>
	            <xsl:variable name="model-package-name">
				    <xsl:variable name="test-package-name" select="document($cfg)/cfg:PlasmaConfiguration/SDO/Namespace[@uri = $model-uri]/Provisioning/@constantsPackageName"/>
				    <xsl:choose>
				        <xsl:when test="$test-package-name != ''">
				            <xsl:value-of select="$test-package-name"/>
				        </xsl:when>
				        <xsl:otherwise>
				            <xsl:value-of select="document($cfg)/cfg:PlasmaConfiguration/SDO/Namespace[@uri = $model-uri]/Provisioning/@packageName"/>
				        </xsl:otherwise>
				    </xsl:choose>
                </xsl:variable>
	            <xsl:if test="$model-package-name = ''">
                    <xsl:message terminate="yes">could not find SDO namespace definition in Plasma Configuration for model level uri '<xsl:value-of select="$uri"/>'</xsl:message>
	            </xsl:if>
	            <xsl:value-of select="$model-package-name"/>
	        </xsl:otherwise>
	    </xsl:choose>
    </xsl:variable>
    
    <xsl:value-of select="$package-name"/>
</xsl:template>


<xsl:template name="hasMathUtilType">
  <xsl:for-each select="PropertyDef">
      <xsl:if test="@datatype = 'Integer' or @datatype = 'Decimal'">
          <xsl:value-of select="'true'"/>
      </xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template name="hasDateUtilType">
  <xsl:for-each select="PropertyDef">
      <xsl:if test="@datatype = 'Date' or @datatype = 'DateTime'">
          <xsl:value-of select="'true'"/>
      </xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template name="mapType">
  <xsl:param name="value" />
  <xsl:choose>
    <xsl:when test="$value = 'String'">
      <xsl:value-of select="'String'"/>
    </xsl:when>
    <xsl:when test="$value = 'Character'">
      <xsl:value-of select="'Character'"/>
    </xsl:when>
    <xsl:when test="$value = 'Decimal'">
      <xsl:value-of select="'BigDecimal'"/>
    </xsl:when>
    <xsl:when test="$value = 'Boolean'">
      <xsl:value-of select="'boolean'"/>
    </xsl:when>
    <xsl:when test="$value = 'Long'">
      <xsl:value-of select="'long'"/>
    </xsl:when>
    <xsl:when test="$value = 'Int'">
      <xsl:value-of select="'int'"/>
    </xsl:when>
    <xsl:when test="$value = 'Integer'">
      <xsl:value-of select="'BigInteger'"/>
    </xsl:when>
    <xsl:when test="$value = 'Short'">
      <xsl:value-of select="'short'"/>
    </xsl:when>
    <xsl:when test="$value = 'Float'">
      <xsl:value-of select="'float'"/>
    </xsl:when>
    <xsl:when test="$value = 'Double'">
      <xsl:value-of select="'double'"/>
    </xsl:when>
    <xsl:when test="$value = 'Date'">
      <xsl:value-of select="'Date'"/>
    </xsl:when>
    <xsl:when test="$value = 'DateTime'">
      <xsl:value-of select="'Date'"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="'String'"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="mapTypeToMethodNameToken">
  <xsl:param name="value" />
  <xsl:choose>
    <xsl:when test="$value = 'String'">
      <xsl:value-of select="'String'"/>
    </xsl:when>
    <xsl:when test="$value = 'Character'">
      <xsl:value-of select="'Char'"/>
    </xsl:when>
    <xsl:when test="$value = 'Decimal'">
      <xsl:value-of select="'BigDecimal'"/>
    </xsl:when>
    <xsl:when test="$value = 'Boolean'">
      <xsl:value-of select="'Boolean'"/>
    </xsl:when>
    <xsl:when test="$value = 'Long'">
      <xsl:value-of select="'Long'"/>
    </xsl:when>
    <xsl:when test="$value = 'Int'">
      <xsl:value-of select="'Int'"/>
    </xsl:when>
    <xsl:when test="$value = 'Integer'">
      <xsl:value-of select="'BigInteger'"/>
    </xsl:when>
    <xsl:when test="$value = 'Short'">
      <xsl:value-of select="'Short'"/>
    </xsl:when>
    <xsl:when test="$value = 'Float'">
      <xsl:value-of select="'Float'"/>
    </xsl:when>
    <xsl:when test="$value = 'Double'">
      <xsl:value-of select="'Double'"/>
    </xsl:when>
    <xsl:when test="$value = 'Date'">
      <xsl:value-of select="'Date'"/>
    </xsl:when>
    <xsl:when test="$value = 'DateTime'">
      <xsl:value-of select="'Date'"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="'String'"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="addPropertyDataStoreMapping">
    <xsl:param name="constant-name"/>

    <xsl:text>&#13;    * &lt;p&gt;&lt;/p&gt;</xsl:text> 
    <xsl:text>&#13;    * &lt;b&gt;Datastore Mapping:&lt;/b&gt;</xsl:text>
    <xsl:text>&#13;    * The model property &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:text>&lt;/b&gt; maps </xsl:text>
    <xsl:text>&#13;    * to the Datastore physical entity/attribute &lt;b&gt;</xsl:text><xsl:value-of select="../@physicalName"/><xsl:text>.</xsl:text><xsl:value-of select="@physicalName"/><xsl:text>&lt;/b&gt;.</xsl:text>
    <xsl:text>&#13;    * &lt;b&gt;Constants File:&lt;/b&gt;</xsl:text>
    <xsl:text>&#13;    * This data property, &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:text>&lt;/b&gt;, can be found</xsl:text>
    <xsl:text>&#13;    * set into the Model constants file static member, &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.PTY_</xsl:text><xsl:value-of select="$constant-name" /><xsl:text>&lt;/b&gt;.</xsl:text>      
</xsl:template>

<xsl:template name="addReferencePropertyDataStoreMapping">
    <xsl:param name="constant-name"/>
    <xsl:text>&#13;    * &lt;p&gt;&lt;/p&gt;</xsl:text> 
    <xsl:text>&#13;    * &lt;b&gt;Datastore Mapping:&lt;/b&gt;</xsl:text>
    <xsl:text>&#13;    * The model property &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:text>&lt;/b&gt; maps </xsl:text>
    <xsl:text>&#13;    * to the Datastore physical entity/attribute &lt;b&gt;</xsl:text><xsl:value-of select="../@physicalName"/><xsl:text>.</xsl:text><xsl:value-of select="@physicalName"/><xsl:text>&lt;/b&gt;.</xsl:text>
    <xsl:text>&#13;    * &lt;b&gt;Constants File:&lt;/b&gt;</xsl:text>
    <xsl:text>&#13;    * This reference property, &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:text>&lt;/b&gt;, can be found</xsl:text>
    <xsl:text>&#13;    * set into the Model constants file static member, &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.PTY_</xsl:text><xsl:value-of select="$constant-name" /><xsl:text>&lt;/b&gt;.</xsl:text>      
</xsl:template>

<xsl:template name="addMultiReferencePropertyDataStoreMapping">
    <xsl:param name="constant-name"/>
    <xsl:text>&#13;    * &lt;p&gt;&lt;/p&gt;</xsl:text> 
    <xsl:text>&#13;    * &lt;b&gt;Datastore Mapping:&lt;/b&gt;</xsl:text>
    <xsl:text>&#13;    * The model property &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:text>&lt;/b&gt; does not map </xsl:text>
    <xsl:text>&#13;    * to a physical Datastore attribute, but synthetic or derived.</xsl:text>
    <xsl:text>&#13;    * &lt;b&gt;Constants File:&lt;/b&gt;</xsl:text>
    <xsl:text>&#13;    * This reference property, &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:text>&lt;/b&gt;, can be found</xsl:text>
    <xsl:text>&#13;    * set into the Model constants file static member, &lt;b&gt;</xsl:text><xsl:value-of select="../@name"/><xsl:text>.PTY_</xsl:text><xsl:value-of select="$constant-name" /><xsl:text>&lt;/b&gt;.</xsl:text>      
</xsl:template>

<xsl:template name="getPropertyConstName">
  <xsl:param name="property-name" />
  <xsl:param name="column-name" />
  <xsl:param name="table-name" />
  <xsl:param name="datatype" />
  <xsl:param name="target-entity" />

  <xsl:choose>
    <xsl:when test="$datatype = 'Reference'">
      <xsl:variable name="prefix">
        <xsl:call-template name="attributePrefixFor">
          <xsl:with-param name="value" select="$table-name"/>
          <xsl:with-param name="len" select="string-length($table-name)"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:variable name="syntheticName" select="concat(concat($prefix, '_'), $property-name)"/>
      <xsl:variable name="nonPrefixedConstName">
        <xsl:call-template name="rdbmsNameFor">
          <xsl:with-param name="value" select="$syntheticName"/>
          <xsl:with-param name="len" select="string-length($syntheticName)"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="$nonPrefixedConstName" />
    </xsl:when>

    <xsl:otherwise> <!-- just use column names as constant names -->
    <xsl:value-of select="$column-name" />
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
