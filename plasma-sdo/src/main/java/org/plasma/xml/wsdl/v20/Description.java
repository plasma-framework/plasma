/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.xml.wsdl.v20;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * 
 *         Although correct, this type declaration does not capture
 *         all the constraints on the contents of the wsdl:description
 *         element as defined by the WSDL 2.0 specification.
 * 
 *         In particular, the ordering constraints wrt elements preceding
 *         and following the wsdl:types child element are not captured, as
 *         attempts to incorporate such restrictions in the schema
 *         ran afoul of the UPA (Unique Particle Attribution) rule
 *         in the XML Schema language.
 * 
 *         Please refer to the WSDL 2.0 specification for
 * 	additional information on the contents of this type.
 *       
 * 
 * <p>Java class for DescriptionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescriptionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/ns/wsdl}ExtensibleDocumentedType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/ns/wsdl}import"/>
 *         &lt;element ref="{http://www.w3.org/ns/wsdl}include"/>
 *         &lt;element ref="{http://www.w3.org/ns/wsdl}types"/>
 *         &lt;element ref="{http://www.w3.org/ns/wsdl}interface"/>
 *         &lt;element ref="{http://www.w3.org/ns/wsdl}binding"/>
 *         &lt;element ref="{http://www.w3.org/ns/wsdl}service"/>
 *         &lt;any/>
 *       &lt;/choice>
 *       &lt;attribute name="targetNamespace" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescriptionType", propOrder = {
    "importsAndIncludesAndTypes"
})
@XmlRootElement(name = "description")
public class Description
    extends ExtensibleDocumentedType
{

    @XmlElementRefs({
        @XmlElementRef(name = "interface", namespace = "http://www.w3.org/ns/wsdl", type = Interface.class),
        @XmlElementRef(name = "types", namespace = "http://www.w3.org/ns/wsdl", type = Types.class),
        @XmlElementRef(name = "service", namespace = "http://www.w3.org/ns/wsdl", type = Service.class),
        @XmlElementRef(name = "binding", namespace = "http://www.w3.org/ns/wsdl", type = Binding.class),
        @XmlElementRef(name = "import", namespace = "http://www.w3.org/ns/wsdl", type = Import.class),
        @XmlElementRef(name = "include", namespace = "http://www.w3.org/ns/wsdl", type = Include.class)
    })
    @XmlAnyElement
    protected List<Object> importsAndIncludesAndTypes;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String targetNamespace;

    /**
     * Gets the value of the importsAndIncludesAndTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importsAndIncludesAndTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportsAndIncludesAndTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Interface }
     * {@link Types }
     * {@link Service }
     * {@link Element }
     * {@link Binding }
     * {@link Import }
     * {@link Include }
     * 
     * 
     */
    public List<Object> getImportsAndIncludesAndTypes() {
        if (importsAndIncludesAndTypes == null) {
            importsAndIncludesAndTypes = new ArrayList<Object>();
        }
        return this.importsAndIncludesAndTypes;
    }

    /**
     * Gets the value of the targetNamespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetNamespace() {
        return targetNamespace;
    }

    /**
     * Sets the value of the targetNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetNamespace(String value) {
        this.targetNamespace = value;
    }

}
