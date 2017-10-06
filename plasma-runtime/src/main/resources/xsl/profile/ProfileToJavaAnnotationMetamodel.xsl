<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xalan="http://xml.apache.org/xalan"
  xmlns:uml="http://schema.omg.org/spec/UML/2.1.2" 
  xmlns:xmi="http://schema.omg.org/spec/XMI/2.1"
  xmlns:redirect="http://xml.apache.org/xalan/redirect"
  extension-element-prefixes="redirect">

<xsl:output
    method="text"
    indent="no"
    standalone="yes"
    encoding="UTF-8"/>

<xsl:include href="PlasmaUtils.xsl"/>
<xsl:include href="StringUtils.xsl"/>

<xsl:strip-space elements="*"/>

<xsl:param name="basedir" />
<xsl:param name="author" />
<xsl:param name="version" />
<xsl:param name="pkg" />
<xsl:param name="implpkg" />
<xsl:param name="cfg" />
<xsl:param name="separator" />


<xsl:template match="xmi:XMI">

<xsl:apply-templates/>

</xsl:template>

<xsl:template match="uml:Model">
    <xsl:apply-templates/>
</xsl:template>

<!-- generate a Stereotype class -->
<xsl:template match="packagedElement[@xmi:type='uml:Profile']">
    <xsl:variable name="packageName">                 
	    <xsl:choose>	                       
	        <xsl:when test="$pkg != ''">
	            <xsl:value-of select="$pkg"/>
	        </xsl:when>
	        <xsl:otherwise>
		        <xsl:call-template name="findPackageNameLocal">                    
		            <xsl:with-param name="pkg" select="''"/>       
		            <xsl:with-param name="clss" select="."/>       
		        </xsl:call-template>                                                    
	        </xsl:otherwise>
	    </xsl:choose>	                       
    </xsl:variable>                                                  
    <xsl:variable name="ownedComment">
        <xsl:call-template name="getOwnedCommentBody"/>
    </xsl:variable>
    <xsl:if test="normalize-space($ownedComment) != ''">
	    <xsl:variable name="packageDir" select="translate($packageName,'.',$separator)"/>                 
	    <xsl:variable name="dir" select="concat(concat($basedir,$separator), $packageDir)"/>                         
	    <xsl:variable name="file" select="concat(concat($dir,$separator),'package.html')"/>
	    <redirect:write select="$file"> 
	        <xsl:text>&#13;&lt;html&gt;</xsl:text>
	        <xsl:text>&#13;&lt;head&gt;</xsl:text>
	        <xsl:text>&#13;&lt;/head&gt;</xsl:text>
	        <xsl:text>&#13;&lt;body&gt;</xsl:text>
            <xsl:value-of select="$ownedComment" disable-output-escaping="yes"/>
	        <xsl:text>&#13;&lt;/body&gt;</xsl:text>
	        <xsl:text>&#13;&lt;/html&gt;</xsl:text>
	    </redirect:write>   
    </xsl:if> 
    <xsl:apply-templates/>
</xsl:template>

<!-- generate a Stereotype class -->
<xsl:template match="packagedElement[@xmi:type='uml:Stereotype']">

    <xsl:variable name="classId" select="@xmi:id"/>
    <xsl:variable name="className" select="substring-after(@name, 'SDO')"/>
    <xsl:variable name="packageName">                 
	    <xsl:choose>	                       
	        <xsl:when test="$pkg != ''">
	            <xsl:value-of select="$pkg"/>
	        </xsl:when>
	        <xsl:otherwise>
		        <xsl:call-template name="findPackageNameLocal">                    
		            <xsl:with-param name="pkg" select="''"/>       
		            <xsl:with-param name="clss" select="."/>       
		        </xsl:call-template>                                                    
	        </xsl:otherwise>
	    </xsl:choose>	                       
    </xsl:variable>                                                  
    <xsl:variable name="packageDir" select="translate($packageName,'.',$separator)"/>                 
    <xsl:variable name="dir" select="concat(concat($basedir,$separator), $packageDir)"/>                         
    <xsl:variable name="file" select="concat(concat($dir,$separator),concat($className, '.java'))"/>

    <redirect:write select="$file"> 
        <!--
        <xsl:message terminate="no"><xsl:value-of select="$file"/></xsl:message>
	    -->
	    <xsl:call-template name="emitHeader"/>  
        <xsl:text>&#13;</xsl:text>
        <xsl:text>package </xsl:text><xsl:value-of select="$packageName"/><xsl:text>;&#13;</xsl:text>
        <xsl:text>&#13;</xsl:text>
        <xsl:text>&#13;</xsl:text>
	    <xsl:for-each select="ownedAttribute">
            <xsl:call-template name="emitImportsForOwnedAttribute">
                <xsl:with-param name="classId" select="$classId" />
            </xsl:call-template>
            <xsl:text>&#13;</xsl:text>
	    </xsl:for-each>    
        <xsl:text>&#13;</xsl:text>
        <!--  
        <xsl:text>import fUML.Syntax.Classes.Kernel.*;&#13;</xsl:text>
        <xsl:text>&#13;</xsl:text>   
        -->     
        <xsl:text>import java.lang.annotation.Retention;&#13;</xsl:text>
        <xsl:text>import java.lang.annotation.RetentionPolicy;&#13;</xsl:text>
        <xsl:text>import org.atteo.classindex.IndexAnnotated;&#13;</xsl:text>       
        <xsl:text>&#13;</xsl:text>   
		<xsl:text>/**&#13;</xsl:text>
		<xsl:text>* &#13;</xsl:text>
        <xsl:text>* </xsl:text><xsl:call-template name="getOwnedCommentBody"/><xsl:text>&#13;</xsl:text>
		<xsl:text>* &#13;</xsl:text>
		<xsl:text>* @author </xsl:text><xsl:value-of select="$author" /><xsl:text>&#13;</xsl:text>
		<xsl:text>* @version </xsl:text><xsl:value-of select="$version" /><xsl:text>&#13;</xsl:text>
		<xsl:text>*/&#13;</xsl:text>
		
	    <xsl:variable name="extends">  
		    <xsl:choose>	                       
		        <xsl:when test="count(generalization) = 1">
                    <xsl:variable name="general" select="generalization/@general"/>
                    <xsl:variable name="generalizationClassName" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$general]/@name"/>
		            <xsl:value-of select="$generalizationClassName"/>
		        </xsl:when>
		        <xsl:when test="count(generalization) &gt; 1">
                     <xsl:message terminate="yes">Stereotype <xsl:value-of select="$className"/> has multiple generalizations - multiple generalizations for Stereotypes not supported</xsl:message>
		        </xsl:when>
		    </xsl:choose>	                       
	    </xsl:variable>                                                  
				
        <xsl:text>@Retention(RetentionPolicy.RUNTIME)</xsl:text>
        <xsl:text>&#13;</xsl:text>
        <xsl:text>@IndexAnnotated</xsl:text>
        <xsl:text>&#13;</xsl:text>
        <xsl:text>public @interface </xsl:text><xsl:value-of select="$className"/>
        <xsl:if test="normalize-space($extends) != ''">
            <xsl:text> extends </xsl:text><xsl:value-of select="$extends"/><xsl:text>&#13;</xsl:text>
        </xsl:if>
        <xsl:text>{&#13;</xsl:text>
        
	    <xsl:for-each select="ownedAttribute">
            <xsl:call-template name="emitOperationsForOwnedAttribute">
                <xsl:with-param name="classId" select="$classId" />
            </xsl:call-template>
            <xsl:text>&#13;</xsl:text>
	    </xsl:for-each>    
    
        <xsl:text>}&#13;</xsl:text>
    
        <!--
        <xsl:message terminate="no">writing file: <xsl:value-of select="$file"/></xsl:message>
        -->
    </redirect:write>

</xsl:template>


<!--
 -
 -->
<xsl:template name="emitImportsForOwnedAttribute">
    <xsl:param name="classId" />                                                   

    <xsl:variable name="attributeId" select="@xmi:id"/>
    <xsl:variable name="attributeName" select="@name"/>
    <xsl:variable name="typeId" select="@type"/>
     <xsl:variable name="primitiveTypeHref" select="type[@xmi:type='uml:PrimitiveType']/@href"/>
    <xsl:variable name="umlTypeHref" select="type[substring-before(@xmi:type, ':') = 'uml']/@href"/>

    <xsl:choose>
    <xsl:when test="$primitiveTypeHref != ''">
        <xsl:variable name="typeName" select="substring-after($primitiveTypeHref, '#')"/>
        <xsl:variable name="javaTypeName"> 
            <xsl:call-template name="getJavaTypeForPrimitiveType">
                <xsl:with-param name="type" select="$typeName" />
            </xsl:call-template>
        </xsl:variable>
        <!--
        <xsl:text>&#13;import </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text>;</xsl:text> 
        -->
    </xsl:when>
    <xsl:when test="$umlTypeHref != ''">
        <xsl:variable name="typeName" select="substring-after($umlTypeHref, '#')"/>
        <xsl:variable name="javaTypeName">
            <xsl:call-template name="getJavaTypeForUMLType">
                <xsl:with-param name="type" select="$typeName" />
            </xsl:call-template>
        </xsl:variable>
        <!--
        <xsl:text>&#13;import </xsl:text><xsl:value-of select="$umlTypeHref"/><xsl:text;</xsl:text> 
        -->
    </xsl:when>
    <xsl:when test="$typeId != ''">
        <xsl:variable name="classType" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/@xmi:type"/>
        <xsl:choose>
            <!-- In a profile model when the datatype of a profile property is itself a Stereotype, 
                 in models where the profile is used, MagicDraw creates an internal reference to the UML element
                 to which the Stereotype is applied, not to the Stereotype itself. Consequently
                 when the internal reference is resolved, the target Java type must be
                 the UML type, not the Stereotype -->                          
            <xsl:when test="$classType = 'uml:Stereotype'">
                <xsl:variable name="umlTypeHref" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/ownedAttribute/type[@xmi:type='uml:Class']/@href"/>
                <xsl:variable name="typeName" select="substring-after($umlTypeHref, '#')"/>
                <xsl:variable name="javaTypeName">
                    <xsl:call-template name="getJavaTypeForUMLType">
                        <xsl:with-param name="type" select="$typeName" />
                    </xsl:call-template>
                </xsl:variable>
                <xsl:text>&#13;import </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text>;</xsl:text> 
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="className" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/@name"/>
                <xsl:if test="$className = ''">
                    <xsl:message terminate="yes">could not find referent class for ownedAttribbute '<xsl:value-of select="concat(concat(../@name, '.'), @name)"/>'</xsl:message>
                </xsl:if>
                <xsl:text>&#13;import </xsl:text><xsl:value-of select="$implpkg"/><xsl:text>.</xsl:text><xsl:value-of select="$className"/><xsl:text>;</xsl:text> 
            </xsl:otherwise>
        </xsl:choose>                          
    </xsl:when>
    <xsl:otherwise>
        <xsl:message terminate="yes">expected type attribute or element for ownedAttribbute '<xsl:value-of select="concat(concat(../@name, '.'), @name)"/>'</xsl:message>
    </xsl:otherwise>
    </xsl:choose>

</xsl:template>

<!--
 -
 -->
<xsl:template name="emitOperationsForOwnedAttribute">
    <xsl:param name="classId" />                                                   

    <xsl:variable name="attributeId" select="@xmi:id"/>
    <xsl:variable name="attributeName" select="@name"/>
    <xsl:variable name="nameSuffix">
        <xsl:variable name="first-ch">                                            
          <xsl:call-template name="toLower">                                  
            <xsl:with-param name="value" select="substring($attributeName, 1, 1)"/>                       
          </xsl:call-template>                                                
        </xsl:variable>  
        <xsl:value-of select="concat($first-ch, substring($attributeName, 2))"/>                                                     
    </xsl:variable>
    <xsl:variable name="typeId" select="@type"/>
    <xsl:variable name="primitiveTypeHref" select="type[@xmi:type='uml:PrimitiveType']/@href"/>
    <xsl:variable name="umlTypeHref" select="type[substring-before(@xmi:type, ':') = 'uml']/@href"/>

    
    <xsl:variable name="minOccurs">
        <xsl:call-template name="getMinOccurs">
        </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="maxOccurs">
        <xsl:call-template name="getMaxOccurs">
        </xsl:call-template>
    </xsl:variable>    

    <xsl:variable name="ownedComment">
        <xsl:call-template name="getOwnedCommentBody"/>
    </xsl:variable>

    <xsl:if test="normalize-space($ownedComment) != ''">
        <xsl:text>&#13;</xsl:text>
	<xsl:text>    /**&#13;</xsl:text>
	<xsl:text>    * </xsl:text><xsl:value-of select="normalize-space($ownedComment)"/><xsl:text>&#13;</xsl:text>
	<xsl:text>    */</xsl:text>
    </xsl:if>
     
    
    <xsl:choose>
    <xsl:when test="$primitiveTypeHref != ''">
        <xsl:variable name="typeName" select="substring-after($primitiveTypeHref, '#')"/>
        <xsl:variable name="javaTypeName"> 
            <xsl:call-template name="getJavaTypeForPrimitiveType">
                <xsl:with-param name="type" select="$typeName" />
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="javaTypeDefault"> 
            <xsl:call-template name="getJavaTypeDefaultForPrimitiveType">
                <xsl:with-param name="type" select="$typeName" />
                <xsl:with-param name="maxOccurs" select="$maxOccurs" />
            </xsl:call-template>
        </xsl:variable>
        <xsl:choose>
        <xsl:when test="$maxOccurs = '1'">    
            <!--               
            <xsl:message terminate="no">min1 ownedAttribbute '<xsl:value-of select="concat(concat(../@name, '.'), @name)"/> min:<xsl:value-of select="$minOccurs"/>'</xsl:message>
	        -->
	        <xsl:choose>
	        <xsl:when test="$minOccurs = '0'">        
	            <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text> </xsl:text> <xsl:value-of select="$nameSuffix"/><xsl:text>() default </xsl:text><xsl:value-of select="$javaTypeDefault"/><xsl:text>;</xsl:text> 
	        </xsl:when>
	        <xsl:otherwise>
	            <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text> </xsl:text> <xsl:value-of select="$nameSuffix"/><xsl:text>();</xsl:text> 
	        </xsl:otherwise>
            </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
	        <xsl:choose>
	        <xsl:when test="$minOccurs = '0'">        
	            <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text>[] </xsl:text> <xsl:value-of select="$nameSuffix"/><xsl:text>() default </xsl:text><xsl:value-of select="$javaTypeDefault"/><xsl:text>;</xsl:text> 
	        </xsl:when>
	        <xsl:otherwise>
	            <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text>[] </xsl:text> <xsl:value-of select="$nameSuffix"/><xsl:text>();</xsl:text> 
	        </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
        </xsl:choose>
        
        
    </xsl:when>
    <xsl:when test="$umlTypeHref != ''">
        <!-- only primitives and enums supported for java annotations  -->
        <!-- 
        <xsl:variable name="typeName" select="substring-after($umlTypeHref, '#')"/>
        <xsl:variable name="javaTypeName">
            <xsl:call-template name="getJavaTypeForUMLType">
                <xsl:with-param name="type" select="$typeName" />
            </xsl:call-template>
        </xsl:variable>
        <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text> get</xsl:text><xsl:value-of select="$nameSuffix"/><xsl:text>();</xsl:text> 
         -->
    </xsl:when>
    <xsl:when test="$typeId != ''">
        <xsl:variable name="classType" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/@xmi:type"/>
        <xsl:choose>
            <!-- In a profile model when the datatype of a profile property is itself a Stereotype, 
                 in models where the profile is used, MagicDraw creates an internal reference to the UML element
                 to which the Stereotype is applied, not to the Stereotype itself. Consequently
                 when the internal reference is resolved, the target Java type must be
                 the UML type, not the Stereotype -->                          
            <xsl:when test="$classType = 'uml:Stereotype'">
                <xsl:variable name="umlTypeHref" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/ownedAttribute/type[@xmi:type='uml:Class']/@href"/>
                <xsl:variable name="typeName" select="substring-after($umlTypeHref, '#')"/>
                <xsl:variable name="javaTypeName">
                    <xsl:call-template name="getJavaTypeForUMLType">
                        <xsl:with-param name="type" select="$typeName" />
                    </xsl:call-template>
                </xsl:variable>
		        <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$javaTypeName"/><xsl:text> </xsl:text><xsl:value-of select="$nameSuffix"/><xsl:text>();</xsl:text> 
            </xsl:when>
            <xsl:otherwise> 
                <xsl:variable name="className" select="/xmi:XMI/uml:Model//packagedElement[@xmi:id=$typeId]/@name"/>
                <xsl:if test="$className = ''">
                    <xsl:message terminate="yes">could not find referent class for ownedAttribbute '<xsl:value-of select="concat(concat(../@name, '.'), @name)"/>'</xsl:message>
                </xsl:if>
	            <xsl:choose>
	            <xsl:when test="$minOccurs = '0'">
	                <!-- HACK: where we assume 'general' is the default -->        
		            <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$className"/><xsl:text> </xsl:text><xsl:value-of select="$nameSuffix"/><xsl:text>() default </xsl:text><xsl:value-of select="$className"/><xsl:text>.general;</xsl:text> 
	            </xsl:when>
	            <xsl:otherwise>
		            <xsl:text>&#13;    public </xsl:text><xsl:value-of select="$className"/><xsl:text> </xsl:text><xsl:value-of select="$nameSuffix"/><xsl:text>();</xsl:text> 
	            </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>                          
    </xsl:when>
    <xsl:otherwise>
        <xsl:message terminate="yes">expected type attribute or element for ownedAttribbute '<xsl:value-of select="concat(concat(../@name, '.'), @name)"/>'</xsl:message>
    </xsl:otherwise>
    </xsl:choose>

</xsl:template>

<xsl:template name="getMinOccurs">
     <xsl:choose>
         <xsl:when test="lowerValue/@value='1'">
             <xsl:value-of select="'1'"/>
         </xsl:when>
         <xsl:otherwise>
             <xsl:value-of select="'0'"/>
         </xsl:otherwise>
     </xsl:choose>
</xsl:template>

<xsl:template name="getMaxOccurs">
    <xsl:choose>
        <xsl:when test="upperValue/@value='*'">
            <xsl:value-of select="'unbounded'"/>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="'1'"/>
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>


<!--
 - Returns the Java primitive type for the given UML primitive type
 -->
<xsl:template name="getJavaTypeForPrimitiveType">
    <xsl:param name="type" />                                                   
    <xsl:choose>
        <xsl:when test="$type = 'String'">
            <xsl:value-of select="'String'"/>
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
 - Returns the Java primitive type for the given UML primitive type
 -->
<xsl:template name="getJavaTypeDefaultForPrimitiveType">
    <xsl:param name="type" /> 
    <xsl:param name="maxOccurs" />                                                  
    <xsl:choose>
        <xsl:when test="$maxOccurs = '1'">
		    <xsl:choose>
		        <xsl:when test="$type = 'String'">
		            <xsl:value-of select="'&quot;&quot;'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Boolean'">
		            <xsl:value-of select="'false'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Float'">
		            <xsl:value-of select="'0'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Long'">
		            <xsl:value-of select="'0'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Double'">
		            <xsl:value-of select="'0'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Integer'">
		            <xsl:value-of select="'0'"/>
		        </xsl:when>
		        <xsl:otherwise>
		            <xsl:message terminate="yes">unknown UML primitive type '<xsl:value-of select="$type"/>'</xsl:message>
		        </xsl:otherwise>
		    </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
		    <xsl:choose>
		        <xsl:when test="$type = 'String'">
		            <xsl:value-of select="'{}'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Boolean'">
		            <xsl:value-of select="'{}'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Float'">
		            <xsl:value-of select="'{}'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Long'">
		            <xsl:value-of select="'{}'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Double'">
		            <xsl:value-of select="'{}'"/>
		        </xsl:when>
		        <xsl:when test="$type = 'Integer'">
		            <xsl:value-of select="'{}'"/>
		        </xsl:when>
		        <xsl:otherwise>
		            <xsl:message terminate="yes">unknown UML primitive type '<xsl:value-of select="$type"/>'</xsl:message>
		        </xsl:otherwise>
		    </xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
 - Returns the Java primitive type for the given UML type
 -->
<xsl:template name="getJavaTypeForUMLType">
    <xsl:param name="type" />                                                   
    <xsl:choose>
        <xsl:when test="$type = 'Class'">
            <xsl:value-of select="'Class_'"/>
        </xsl:when>
        <xsl:when test="$type = 'Package'">
            <xsl:value-of select="'fUML.Syntax.Classes.Kernel.Package'"/>
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

