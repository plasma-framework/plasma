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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for BindingOperationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BindingOperationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/ns/wsdl}ExtensibleDocumentedType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="input" type="{http://www.w3.org/ns/wsdl}BindingOperationMessageType"/>
 *         &lt;element name="output" type="{http://www.w3.org/ns/wsdl}BindingOperationMessageType"/>
 *         &lt;element name="infault" type="{http://www.w3.org/ns/wsdl}BindingOperationFaultType"/>
 *         &lt;element name="outfault" type="{http://www.w3.org/ns/wsdl}BindingOperationFaultType"/>
 *         &lt;any/>
 *       &lt;/choice>
 *       &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BindingOperationType", propOrder = {
    "inputsAndOutputsAndInfaults"
})
public class BindingOperationType
    extends ExtensibleDocumentedType
{

    @XmlElementRefs({
        @XmlElementRef(name = "outfault", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class),
        @XmlElementRef(name = "input", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class),
        @XmlElementRef(name = "infault", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class),
        @XmlElementRef(name = "output", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class)
    })
    @XmlAnyElement
    protected List<Object> inputsAndOutputsAndInfaults;
    @XmlAttribute(required = true)
    protected QName ref;

    /**
     * Gets the value of the inputsAndOutputsAndInfaults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputsAndOutputsAndInfaults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputsAndOutputsAndInfaults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link BindingOperationFaultType }{@code >}
     * {@link JAXBElement }{@code <}{@link BindingOperationMessageType }{@code >}
     * {@link JAXBElement }{@code <}{@link BindingOperationMessageType }{@code >}
     * {@link JAXBElement }{@code <}{@link BindingOperationFaultType }{@code >}
     * {@link Element }
     * 
     * 
     */
    public List<Object> getInputsAndOutputsAndInfaults() {
        if (inputsAndOutputsAndInfaults == null) {
            inputsAndOutputsAndInfaults = new ArrayList<Object>();
        }
        return this.inputsAndOutputsAndInfaults;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setRef(QName value) {
        this.ref = value;
    }

}
