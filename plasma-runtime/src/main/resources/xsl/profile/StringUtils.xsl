<?xml version="1.0" encoding="UTF-8"?>
<!--
-->
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:lxslt="http://xml.apache.org/xslt">


<xsl:template name="objectNameFor">
  <xsl:param name="value" />  
  <xsl:param name="len" />  
  <xsl:param name="index">1</xsl:param>  
  <xsl:call-template name="modelNameFor">
    <xsl:with-param name="value" select="$value"/>
    <xsl:with-param name="len" select="$len"/>
    <xsl:with-param name="type" select="'object'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="attributeNameFor">
  <xsl:param name="value" />  
  <xsl:param name="len" />  
  <xsl:param name="index">1</xsl:param>  
  <xsl:call-template name="modelNameFor">
    <xsl:with-param name="value" select="$value"/>
    <xsl:with-param name="len" select="$len"/>
    <xsl:with-param name="type" select="'attribute'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="attributePrefixFor">
  <xsl:param name="value" />  
  <xsl:param name="len" />  
  <xsl:param name="index">1</xsl:param>  
  <xsl:call-template name="propertyPrefixFor">
    <xsl:with-param name="value" select="$value"/>
    <xsl:with-param name="len" select="$len"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="modelNameFor">
  <xsl:param name="value"/>  
  <xsl:param name="len"/>  
  <xsl:param name="index">1</xsl:param>  
  <xsl:param name="type"/>  
  
  <xsl:if test="$index &lt; $len + 1">
    <xsl:variable name="ch" select="substring($value, $index, 1)"/>
    <xsl:choose>
      <!-- If an object name and first char, uppercase the first char -->
      <xsl:when test="$index = 1 and $type = 'object'"> 
        <xsl:variable name="new-ch">                                            
          <xsl:call-template name="toUpper">                                  
            <xsl:with-param name="value" select="$ch"/>                       
          </xsl:call-template>                                                
        </xsl:variable>                                                       
        <xsl:value-of select="$new-ch"/> 
        <xsl:call-template name="modelNameFor">                                   
          <xsl:with-param name="value" select="$value"/>                        
          <xsl:with-param name="len" select="$len"/>                  
          <xsl:with-param name="index" select="$index + 1"/>                    
        </xsl:call-template>                                                                             
      </xsl:when>      
      <!-- If an underscore, hyphen or space, igmore it and uppercase the next char -->
      <xsl:when test="$ch = '_' or $ch = '-' or $ch = ' '"> 
        <xsl:if test="$index + 1 &lt; $len + 1">
          <xsl:variable name="next-ch" select="substring($value, $index + 1, 1)"/>
          <xsl:variable name="new-ch">                                            
            <xsl:call-template name="toUpper">                                
              <xsl:with-param name="value" select="$next-ch"/>                     
            </xsl:call-template>                                              
          </xsl:variable>                                                     
          <xsl:value-of select="$new-ch"/>                       
          <xsl:call-template name="modelNameFor">                                 
            <xsl:with-param name="value" select="$value"/>                      
            <xsl:with-param name="len" select="$len"/>                  
            <xsl:with-param name="index" select="$index + 2"/>                  
          </xsl:call-template>                                                                           
        </xsl:if>  
      </xsl:when>                               
      <xsl:otherwise>                               
        <xsl:variable name="new-ch">                                            
          <xsl:call-template name="toLower">                                  
            <xsl:with-param name="value" select="$ch"/>                       
          </xsl:call-template>                                                
        </xsl:variable>                                                       
        <xsl:value-of select="$new-ch"/> 
        <xsl:call-template name="modelNameFor">                                   
          <xsl:with-param name="value" select="$value"/>                        
          <xsl:with-param name="len" select="$len"/>                  
          <xsl:with-param name="index" select="$index + 1"/>                    
        </xsl:call-template>                                                                             
      </xsl:otherwise>                               
    </xsl:choose>                                                                                    
  </xsl:if>
</xsl:template>

<xsl:template name="propertyPrefixFor">
  <xsl:param name="value"/>  
  <xsl:param name="len"/>  
  <xsl:param name="index">1</xsl:param>  
  
  <xsl:if test="$index &lt; $len + 1">
    <xsl:variable name="ch" select="substring($value, $index, 1)"/>
    <xsl:choose>
      <!-- If first char, lowercase the first char, continue -->
      <xsl:when test="$index = 1">       
        <xsl:variable name="new-ch">                                            
          <xsl:call-template name="toLower">                                  
            <xsl:with-param name="value" select="$ch"/>                       
          </xsl:call-template>                                                
        </xsl:variable>                                                       
        <xsl:value-of select="$new-ch"/> 
        <xsl:call-template name="propertyPrefixFor">                                   
          <xsl:with-param name="value" select="$value"/>                        
          <xsl:with-param name="len" select="$len"/>                  
          <xsl:with-param name="index" select="$index + 1"/>                    
        </xsl:call-template>                                                                             
      </xsl:when>     
 
      <!-- If an underscore, ignore it and lowercase the next char -->
      <xsl:when test="$ch = '_'"> 
        <xsl:if test="$index + 1 &lt; $len + 1"> <!-- if underscore not last char -->
          <xsl:variable name="next-ch" select="substring($value, $index + 1, 1)"/>
          <xsl:variable name="new-ch">                                            
            <xsl:call-template name="toLower">                                
              <xsl:with-param name="value" select="$next-ch"/>                     
            </xsl:call-template>                                              
          </xsl:variable>                                                     
          <xsl:value-of select="$new-ch"/>                       
          <xsl:call-template name="propertyPrefixFor">                                 
            <xsl:with-param name="value" select="$value"/>                      
            <xsl:with-param name="len" select="$len"/>                  
            <xsl:with-param name="index" select="$index + 2"/>                  
          </xsl:call-template>                                                                           
        </xsl:if>  
      </xsl:when>                               
      <xsl:otherwise>
        <!-- ignore, continue -->                               
        <xsl:call-template name="propertyPrefixFor">                                   
          <xsl:with-param name="value" select="$value"/>                        
          <xsl:with-param name="len" select="$len"/>                  
          <xsl:with-param name="index" select="$index + 1"/>                    
        </xsl:call-template>                                                                             
      </xsl:otherwise>                               
    </xsl:choose>                                                                                    
  </xsl:if>
</xsl:template>


<xsl:template name="physicalNameFor">
  <xsl:param name="value" />  
  <xsl:param name="len" />  
  <xsl:param name="index">1</xsl:param>  
  <xsl:call-template name="rdbmsNameFor">
    <xsl:with-param name="value" select="$value"/>
    <xsl:with-param name="len" select="$len"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="rdbmsNameFor">
  <xsl:param name="value"/>  
  <xsl:param name="len"/>  
  <xsl:param name="index">1</xsl:param>  
  
  <xsl:if test="$index &lt; $len + 1">
    <xsl:variable name="ch" select="substring($value, $index, 1)"/>
    <xsl:variable name="ch-upper">                                            
      <xsl:call-template name="toUpper">                                  
        <xsl:with-param name="value" select="$ch"/>                       
      </xsl:call-template>                                                
    </xsl:variable>                                                       
    <xsl:variable name="is-upper">                                            
      <xsl:call-template name="isUpper">                                  
        <xsl:with-param name="value" select="$ch"/>                       
      </xsl:call-template>                                                
    </xsl:variable>                                                       
    <xsl:if test="$is-upper = 'true'">
      <xsl:if test="$index &gt; 1">
          <xsl:value-of select="'_'"/>                       
      </xsl:if>    
    </xsl:if>    
    <xsl:value-of select="$ch-upper"/>                       
    <xsl:call-template name="rdbmsNameFor">                                   
      <xsl:with-param name="value" select="$value"/>                        
      <xsl:with-param name="len" select="$len"/>                  
      <xsl:with-param name="index" select="$index + 1"/>                    
    </xsl:call-template>                                                                             
  </xsl:if>
</xsl:template>

<xsl:template name="string-replace-all">
    <xsl:param name="text" />
    <xsl:param name="replace" />
    <xsl:param name="by" />
    <xsl:choose>
      <xsl:when test="contains($text, $replace)">
        <xsl:value-of select="substring-before($text,$replace)" />
        <xsl:value-of select="$by" />
        <xsl:call-template name="string-replace-all">
          <xsl:with-param name="text"
          select="substring-after($text,$replace)" />
          <xsl:with-param name="replace" select="$replace" />
          <xsl:with-param name="by" select="$by" />
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
	      <xsl:value-of select="$text"/>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>
  
<xsl:template name="toLower">
  <xsl:param name="value" />
  <xsl:value-of select="translate($value,
      'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
      'abcdefghijklmnopqrstuvwxyz')" />
</xsl:template>

<xsl:template name="toUpper">
  <xsl:param name="value" />
  <xsl:value-of select="translate($value,
      'abcdefghijklmnopqrstuvwxyz',
      'ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />
</xsl:template>

<xsl:template name="isLower">
  <xsl:param name="value" />
  <xsl:value-of select="contains('abcdefghijklmnopqrstuvwxyz',$value)" />
</xsl:template>

<xsl:template name="isUpper">
  <xsl:param name="value" />
  <xsl:value-of select="contains('ABCDEFGHIJKLMNOPQRSTUVWXYZ',$value)" />
</xsl:template>



</xsl:stylesheet>
