<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xalan="http://xml.apache.org/xalan"
  xmlns:uml="http://schema.omg.org/spec/UML/2.1.2" 
  xmlns:xmi="http://schema.omg.org/spec/XMI/2.1"
  xmlns:redirect="http://xml.apache.org/xalan/redirect"
  extension-element-prefixes="redirect">

<xsl:output
    method="xml"
    indent="yes"
    standalone="yes"
    encoding="UTF-8"/>

<xsl:include href="StringUtils.xsl"/>

<xsl:strip-space elements="*"/>

<xsl:param name="author" />
<xsl:param name="version" />


<xsl:template match="xmi:XMI">

<xsl:apply-templates/>

</xsl:template>

<xsl:template match="uml:Model">
<xs:schema targetNamespace="http://www.w3.org/2001/XMLSchema"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" 
    elementFormDefault="qualified">
    <xsl:apply-templates/>
</xs:schema>
</xsl:template>

<!-- generate a Stereotype class -->
<xsl:template match="packagedElement[@xmi:type='uml:Profile']">
    <xsl:apply-templates/>
</xsl:template>

<!-- generate a Stereotype class -->
<xsl:template match="packagedElement[@xmi:type='uml:Stereotype']">

    <xsl:variable name="classId" select="@xmi:id"/>
    <xsl:variable name="className" select="@name"/>
    <xs:complexType name="{$className}">
        <xs:annotation><xs:documentation><xsl:call-template name="getOwnedCommentBody"/></xs:documentation></xs:annotation>
				
		<!--
	    <xsl:variable name="hasElements">
            <xsl:call-template name="hasXSElementForOwnedAttribute"/>
	    </xsl:variable>
	    <xsl:if test="$hasElements != ''">
		    <xsl:for-each select="ownedAttribute">
		        <xsl:if test="contains(@name, 'base_') = false">
	                <xsl:call-template name="emitXSElementForOwnedAttribute">
	                </xsl:call-template>
	            </xsl:if>
		    </xsl:for-each>    
	    </xsl:if>
	    -->
				
	    <xsl:for-each select="ownedAttribute">
	        <xsl:if test="contains(@name, 'base_') = false">
                <xsl:call-template name="emitXSAttributeForOwnedAttribute">
                </xsl:call-template>
            </xsl:if>
	    </xsl:for-each>    
	</xs:complexType>	
    <xs:element name="{$className}" type="prov:{$className}"/>

</xsl:template>

<!-- generate an enumeration class -->
<xsl:template match="packagedElement[@xmi:type='uml:Enumeration']">

    <xsl:variable name="classId" select="@xmi:id"/>
    <xsl:variable name="className" select="@name"/>

    <xs:simpleType name="{$className}">
        <xs:annotation><xs:documentation><xsl:call-template name="getOwnedCommentBody"/></xs:documentation></xs:annotation>
        <xs:restriction base="xs:string">
		    <xsl:for-each select="ownedLiteral">
	            <xs:enumeration value="{@name}"><xs:annotation><xs:documentation><xsl:call-template name="getOwnedCommentBody"/></xs:documentation></xs:annotation></xs:enumeration> 
		    </xsl:for-each>    
        </xs:restriction>
		
	</xs:simpleType>	
   

</xsl:template>



<!--
 - Creates an XML Schema attribute for each ownedAttribute
 -->
<xsl:template name="emitXSAttributeForOwnedAttribute">
    <xsl:param name="classId" />                                                   

    <xsl:variable name="attributeId" select="@xmi:id"/>
    <xsl:variable name="attributeName" select="@name"/>
    <xsl:variable name="typeId" select="@type"/>
    <xsl:variable name="primitiveTypeHref" select="type[@xmi:type='uml:PrimitiveType']/@href"/>
    <xsl:variable name="umlTypeHref" select="type[substring-before(@xmi:type, ':') = 'uml']/@href"/>
    
    <xsl:variable name="use">
        <xsl:choose>
	        <xsl:when test="lowerValue/@value = '1'">
	            <xsl:value-of select="'required'"/>
	        </xsl:when>
	        <xsl:otherwise>
	            <xsl:value-of select="'optional'"/>
	        </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:choose>
    <xsl:when test="$primitiveTypeHref != ''">
        <xsl:variable name="typeName" select="substring-after($primitiveTypeHref, '#')"/>
        <xsl:variable name="xsTypeName"> 
            <xsl:call-template name="getXSTypeForPrimitiveType">
                <xsl:with-param name="type" select="$typeName" />
            </xsl:call-template>
        </xsl:variable>
        <xs:attribute name="{$attributeName}" type="xs:{$xsTypeName}" use="{$use}">
            <xs:annotation><xs:documentation><xsl:call-template name="getOwnedCommentBody"/></xs:documentation></xs:annotation>
        </xs:attribute>
    </xsl:when>
    <xsl:when test="$typeId != ''">
        <xsl:variable name="classType" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/@xmi:type"/>
        <xsl:variable name="className" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/@name"/>
        <xsl:if test="$className = ''">
            <xsl:message terminate="yes">could not find referent class for ownedAttribbute '<xsl:value-of select="concat(concat(../@name, '.'), @name)"/>'</xsl:message>
        </xsl:if>
        <xs:attribute name="{$attributeName}" type="prov:{$className}" use="{$use}">
            <xs:annotation><xs:documentation><xsl:call-template name="getOwnedCommentBody"/></xs:documentation></xs:annotation>
        </xs:attribute>
    </xsl:when>
    <xsl:when test="$umlTypeHref != ''">
        <xsl:variable name="typeName" select="substring-after($umlTypeHref, '#')"/>
        <xsl:variable name="xsTypeName">
            <xsl:call-template name="getXSTypeForUMLType">
                <xsl:with-param name="type" select="$typeName" />
            </xsl:call-template>
        </xsl:variable>
        <xs:attribute name="{$attributeName}" type="prov:{$xsTypeName}" use="{$use}">
            <xs:annotation><xs:documentation><xsl:call-template name="getOwnedCommentBody"/></xs:documentation></xs:annotation>
        </xs:attribute>
    </xsl:when>
    </xsl:choose>

</xsl:template>


<!--
 - Returns 'true' if an XML Schema Element exists for any ownedAttribute
 -->
<xsl:template name="hasXSElementForOwnedAttribute">

    <xsl:for-each select="ownedAttribute">
	    <xsl:variable name="typeId" select="@type"/>
	    <xsl:variable name="umlTypeHref" select="type[substring-before(@xmi:type, ':') = 'uml']/@href"/>
	    
	    <xsl:choose>
	    <xsl:when test="$umlTypeHref != ''">
	        <xsl:value-of select="'true'"/>
	    </xsl:when>
	    </xsl:choose>
    </xsl:for-each>    

</xsl:template>

<!--
 - Creates an XML Schema Element for each ownedAttribute where applicable
 -->
<xsl:template name="emitXSElementForOwnedAttribute">
    <xsl:param name="classId" />                                                   

    <xsl:variable name="attributeId" select="@xmi:id"/>
    <xsl:variable name="attributeName" select="@name"/>
    <xsl:variable name="typeId" select="@type"/>
    <xsl:variable name="primitiveTypeHref" select="type[@xmi:type='uml:PrimitiveType']/@href"/>
    <xsl:variable name="umlTypeHref" select="type[substring-before(@xmi:type, ':') = 'uml']/@href"/>
    
    <xsl:variable name="minOccurs">
        <xsl:choose>
	        <xsl:when test="lowerValue/@value = '1'">
	            <xsl:value-of select="'1'"/>
	        </xsl:when>
	        <xsl:otherwise>
	            <xsl:value-of select="'0'"/>
	        </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="maxOccurs">
        <xsl:choose>
	        <xsl:when test="upperValue/@value = '1'">
	            <xsl:value-of select="'1'"/>
	        </xsl:when>
	        <xsl:otherwise>
	            <xsl:value-of select="'unbounded'"/>
	        </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:choose>
    <xsl:when test="$umlTypeHref != ''">
        <xsl:variable name="typeName" select="substring-after($umlTypeHref, '#')"/>
        <xsl:variable name="xsTypeName">
            <xsl:call-template name="getXSTypeForUMLType">
                <xsl:with-param name="type" select="$typeName" />
            </xsl:call-template>
        </xsl:variable>
        <xs:element name="{$attributeName}" type="prov:{$xsTypeName}" minOccurs="{$minOccurs}" maxOccurs="{$maxOccurs}">
            <xs:annotation><xs:documentation><xsl:call-template name="getOwnedCommentBody"/></xs:documentation></xs:annotation>
        </xs:element>
    </xsl:when>
    </xsl:choose>

</xsl:template>



<!--
 - Returns the Java primitive type for the given UML primitive type
 -->
<xsl:template name="getXSTypeForPrimitiveType">
    <xsl:param name="type" />                                                   
    <xsl:choose>
        <xsl:when test="$type = 'String'">
            <xsl:value-of select="'string'"/>
        </xsl:when>
        <xsl:when test="$type = 'Boolean'">
            <xsl:value-of select="'boolean'"/>
        </xsl:when>
        <xsl:when test="$type = 'Float'">
            <xsl:value-of select="'float'"/>
        </xsl:when>
        <xsl:when test="$type = 'Long'">
            <xsl:value-of select="'long'"/>
        </xsl:when>
        <xsl:when test="$type = 'Double'">
            <xsl:value-of select="'double'"/>
        </xsl:when>
        <xsl:when test="$type = 'Integer'">
            <xsl:value-of select="'int'"/>
        </xsl:when>
        <xsl:otherwise>
            <xsl:message terminate="yes">unknown UML primitive type '<xsl:value-of select="$type"/>'</xsl:message>
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!--
 - Returns the Java primitive type for the given UML type
 -->
<xsl:template name="getXSTypeForUMLType">
    <xsl:param name="type" />                                                   
    <xsl:choose>
        <xsl:when test="$type = 'Class'">
            <xsl:value-of select="'class'"/>
        </xsl:when>
        <xsl:when test="$type = 'Package'">
            <xsl:value-of select="'package'"/>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$type"/>
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>



<!--
 - Returns the owned comment body attribute or element text for
 - the current context node.   
 -->
<xsl:template name="getOwnedCommentBody">
    <xsl:choose>
    <xsl:when test="normalize-space(ownedComment/@body) != ''">
        <xsl:value-of select="normalize-space(ownedComment/@body)"/>
    </xsl:when>
    <xsl:when test="normalize-space(ownedComment/body/text()) != ''">
        <xsl:value-of select="normalize-space(ownedComment/body/text())"/>
    </xsl:when>
    <xsl:otherwise>
    </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!--
 - Walks up the package hierarchy from the given element assembling 
 - a package name.  
 -->
<xsl:template name="findPackageNameLocal">                                        
  <xsl:param name="pkg" />                                                   
  <xsl:param name="clss" />                                                  
                                                                             
  <xsl:if test="$clss/../@xmi:type = 'uml:Package' or $clss/../@xmi:type = 'uml:Profile' or name($clss/..) = 'uml:Model'">                         
      <xsl:variable name="pkgvar">                                           
          <xsl:value-of select="concat($clss/../@name, $pkg)" />             
      </xsl:variable>                                                        
      <xsl:choose>                                                           
          <xsl:when test="$clss/../../@xmi:type = 'uml:Package' or $clss/../@xmi:type = 'uml:Profile' or name($clss/../..) = 'uml:Model'">            
              <xsl:call-template name="findPackageNameLocal">                     
                <xsl:with-param name="pkg" select="concat('.', $pkgvar)"/>   
                <xsl:with-param name="clss" select="$clss/.."/>              
              </xsl:call-template>                                                
          </xsl:when>                                                        
          <xsl:otherwise>                                                    
              <xsl:value-of select="translate($pkgvar, ' ', '_')"/>                               
          </xsl:otherwise>                                                   
      </xsl:choose>                                                          
  </xsl:if>                                                                  
</xsl:template>                                                              


<xsl:template match="xmi:Extension">
<!-- ignore -->
</xsl:template>



</xsl:stylesheet>

