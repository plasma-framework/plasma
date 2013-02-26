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
package org.plasma.xml.xslt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.plasma.xml.xslt package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TopLevelElementAndCharInstruction_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "top-level-element-and-char-instruction");
    private final static QName _WithParam_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "with-param");
    private final static QName _Include_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "include");
    private final static QName _CallTemplate_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "call-template");
    private final static QName _PreserveSpace_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "preserve-space");
    private final static QName _Number_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "number");
    private final static QName _Template_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "template");
    private final static QName _Element_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "element");
    private final static QName _Text_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "text");
    private final static QName _Comment_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "comment");
    private final static QName _DecimalFormat_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "decimal-format");
    private final static QName _Output_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "output");
    private final static QName _Variable_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "variable");
    private final static QName _ApplyTemplates_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "apply-templates");
    private final static QName _StripSpace_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "strip-space");
    private final static QName _ForEach_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "for-each");
    private final static QName _Message_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "message");
    private final static QName _Fallback_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "fallback");
    private final static QName _ProcessingInstruction_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "processing-instruction");
    private final static QName _Attribute_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "attribute");
    private final static QName _ResultElement_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "result-element");
    private final static QName _Param_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "param");
    private final static QName _CharInstruction_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "char-instruction");
    private final static QName _CopyOf_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "copy-of");
    private final static QName _Choose_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "choose");
    private final static QName _Key_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "key");
    private final static QName _ValueOf_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "value-of");
    private final static QName _Copy_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "copy");
    private final static QName _TopLevelElement_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "top-level-element");
    private final static QName _AttributeSet_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "attribute-set");
    private final static QName _Stylesheet_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "stylesheet");
    private final static QName _Instruction_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "instruction");
    private final static QName _Import_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "import");
    private final static QName _NamespaceAlias_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "namespace-alias");
    private final static QName _If_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "if");
    private final static QName _Transform_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "transform");
    private final static QName _ApplyImports_QNAME = new QName("http://www.w3.org/1999/XSL/Transform", "apply-imports");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.plasma.xml.xslt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Comment }
     * 
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link Template }
     * 
     */
    public Template createTemplate() {
        return new Template();
    }

    /**
     * Create an instance of {@link AttributeSet }
     * 
     */
    public AttributeSet createAttributeSet() {
        return new AttributeSet();
    }

    /**
     * Create an instance of {@link ProcessingInstruction }
     * 
     */
    public ProcessingInstruction createProcessingInstruction() {
        return new ProcessingInstruction();
    }

    /**
     * Create an instance of {@link AnyType }
     * 
     */
    public AnyType createAnyType() {
        return new AnyType();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link ValueOf }
     * 
     */
    public ValueOf createValueOf() {
        return new ValueOf();
    }

    /**
     * Create an instance of {@link DecimalFormat }
     * 
     */
    public DecimalFormat createDecimalFormat() {
        return new DecimalFormat();
    }

    /**
     * Create an instance of {@link Sort }
     * 
     */
    public Sort createSort() {
        return new Sort();
    }

    /**
     * Create an instance of {@link CopyOf }
     * 
     */
    public CopyOf createCopyOf() {
        return new CopyOf();
    }

    /**
     * Create an instance of {@link Otherwise }
     * 
     */
    public Otherwise createOtherwise() {
        return new Otherwise();
    }

    /**
     * Create an instance of {@link ForEach }
     * 
     */
    public ForEach createForEach() {
        return new ForEach();
    }

    /**
     * Create an instance of {@link Variable }
     * 
     */
    public Variable createVariable() {
        return new Variable();
    }

    /**
     * Create an instance of {@link PreserveSpace }
     * 
     */
    public PreserveSpace createPreserveSpace() {
        return new PreserveSpace();
    }

    /**
     * Create an instance of {@link NamespaceAlias }
     * 
     */
    public NamespaceAlias createNamespaceAlias() {
        return new NamespaceAlias();
    }

    /**
     * Create an instance of {@link If }
     * 
     */
    public If createIf() {
        return new If();
    }

    /**
     * Create an instance of {@link Element }
     * 
     */
    public Element createElement() {
        return new Element();
    }

    /**
     * Create an instance of {@link Copy }
     * 
     */
    public Copy createCopy() {
        return new Copy();
    }

    /**
     * Create an instance of {@link Fallback }
     * 
     */
    public Fallback createFallback() {
        return new Fallback();
    }

    /**
     * Create an instance of {@link Choose }
     * 
     */
    public Choose createChoose() {
        return new Choose();
    }

    /**
     * Create an instance of {@link Key }
     * 
     */
    public Key createKey() {
        return new Key();
    }

    /**
     * Create an instance of {@link Number }
     * 
     */
    public Number createNumber() {
        return new Number();
    }

    /**
     * Create an instance of {@link AttributeType }
     * 
     */
    public AttributeType createAttributeType() {
        return new AttributeType();
    }

    /**
     * Create an instance of {@link CallTemplate }
     * 
     */
    public CallTemplate createCallTemplate() {
        return new CallTemplate();
    }

    /**
     * Create an instance of {@link ApplyTemplates }
     * 
     */
    public ApplyTemplates createApplyTemplates() {
        return new ApplyTemplates();
    }

    /**
     * Create an instance of {@link When }
     * 
     */
    public When createWhen() {
        return new When();
    }

    /**
     * Create an instance of {@link Output }
     * 
     */
    public Output createOutput() {
        return new Output();
    }

    /**
     * Create an instance of {@link Text }
     * 
     */
    public Text createText() {
        return new Text();
    }

    /**
     * Create an instance of {@link CombineStylesheets }
     * 
     */
    public CombineStylesheets createCombineStylesheets() {
        return new CombineStylesheets();
    }

    /**
     * Create an instance of {@link Wrapper }
     * 
     */
    public Wrapper createWrapper() {
        return new Wrapper();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "top-level-element-and-char-instruction")
    public JAXBElement<AnyType> createTopLevelElementAndCharInstruction(AnyType value) {
        return new JAXBElement<AnyType>(_TopLevelElementAndCharInstruction_QNAME, AnyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Variable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "with-param")
    public JAXBElement<Variable> createWithParam(Variable value) {
        return new JAXBElement<Variable>(_WithParam_QNAME, Variable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CombineStylesheets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "include", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<CombineStylesheets> createInclude(CombineStylesheets value) {
        return new JAXBElement<CombineStylesheets>(_Include_QNAME, CombineStylesheets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "call-template", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<CallTemplate> createCallTemplate(CallTemplate value) {
        return new JAXBElement<CallTemplate>(_CallTemplate_QNAME, CallTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreserveSpace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "preserve-space", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<PreserveSpace> createPreserveSpace(PreserveSpace value) {
        return new JAXBElement<PreserveSpace>(_PreserveSpace_QNAME, PreserveSpace.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Number }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "number", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<Number> createNumber(Number value) {
        return new JAXBElement<Number>(_Number_QNAME, Number.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Template }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "template", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<Template> createTemplate(Template value) {
        return new JAXBElement<Template>(_Template_QNAME, Template.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Element }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "element", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "instruction")
    public JAXBElement<Element> createElement(Element value) {
        return new JAXBElement<Element>(_Element_QNAME, Element.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Text }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "text", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<Text> createText(Text value) {
        return new JAXBElement<Text>(_Text_QNAME, Text.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Comment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "comment", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "instruction")
    public JAXBElement<Comment> createComment(Comment value) {
        return new JAXBElement<Comment>(_Comment_QNAME, Comment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DecimalFormat }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "decimal-format", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<DecimalFormat> createDecimalFormat(DecimalFormat value) {
        return new JAXBElement<DecimalFormat>(_DecimalFormat_QNAME, DecimalFormat.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "output", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<Output> createOutput(Output value) {
        return new JAXBElement<Output>(_Output_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Variable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "variable", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<Variable> createVariable(Variable value) {
        return new JAXBElement<Variable>(_Variable_QNAME, Variable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplyTemplates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "apply-templates", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<ApplyTemplates> createApplyTemplates(ApplyTemplates value) {
        return new JAXBElement<ApplyTemplates>(_ApplyTemplates_QNAME, ApplyTemplates.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreserveSpace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "strip-space", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "preserve-space")
    public JAXBElement<PreserveSpace> createStripSpace(PreserveSpace value) {
        return new JAXBElement<PreserveSpace>(_StripSpace_QNAME, PreserveSpace.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForEach }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "for-each", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<ForEach> createForEach(ForEach value) {
        return new JAXBElement<ForEach>(_ForEach_QNAME, ForEach.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Message }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "message", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<Message> createMessage(Message value) {
        return new JAXBElement<Message>(_Message_QNAME, Message.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fallback }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "fallback", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<Fallback> createFallback(Fallback value) {
        return new JAXBElement<Fallback>(_Fallback_QNAME, Fallback.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessingInstruction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "processing-instruction", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "instruction")
    public JAXBElement<ProcessingInstruction> createProcessingInstruction(ProcessingInstruction value) {
        return new JAXBElement<ProcessingInstruction>(_ProcessingInstruction_QNAME, ProcessingInstruction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "attribute", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "instruction")
    public JAXBElement<AttributeType> createAttribute(AttributeType value) {
        return new JAXBElement<AttributeType>(_Attribute_QNAME, AttributeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "result-element")
    public JAXBElement<Object> createResultElement(Object value) {
        return new JAXBElement<Object>(_ResultElement_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Variable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "param", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<Variable> createParam(Variable value) {
        return new JAXBElement<Variable>(_Param_QNAME, Variable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "char-instruction", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "instruction")
    public JAXBElement<AnyType> createCharInstruction(AnyType value) {
        return new JAXBElement<AnyType>(_CharInstruction_QNAME, AnyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyOf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "copy-of", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<CopyOf> createCopyOf(CopyOf value) {
        return new JAXBElement<CopyOf>(_CopyOf_QNAME, CopyOf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Choose }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "choose", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<Choose> createChoose(Choose value) {
        return new JAXBElement<Choose>(_Choose_QNAME, Choose.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Key }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "key", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<Key> createKey(Key value) {
        return new JAXBElement<Key>(_Key_QNAME, Key.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValueOf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "value-of", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<ValueOf> createValueOf(ValueOf value) {
        return new JAXBElement<ValueOf>(_ValueOf_QNAME, ValueOf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Copy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "copy", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<Copy> createCopy(Copy value) {
        return new JAXBElement<Copy>(_Copy_QNAME, Copy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "top-level-element")
    public JAXBElement<AnyType> createTopLevelElement(AnyType value) {
        return new JAXBElement<AnyType>(_TopLevelElement_QNAME, AnyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributeSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "attribute-set", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<AttributeSet> createAttributeSet(AttributeSet value) {
        return new JAXBElement<AttributeSet>(_AttributeSet_QNAME, AttributeSet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Wrapper }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "stylesheet")
    public JAXBElement<Wrapper> createStylesheet(Wrapper value) {
        return new JAXBElement<Wrapper>(_Stylesheet_QNAME, Wrapper.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "instruction")
    public JAXBElement<AnyType> createInstruction(AnyType value) {
        return new JAXBElement<AnyType>(_Instruction_QNAME, AnyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CombineStylesheets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "import")
    public JAXBElement<CombineStylesheets> createImport(CombineStylesheets value) {
        return new JAXBElement<CombineStylesheets>(_Import_QNAME, CombineStylesheets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NamespaceAlias }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "namespace-alias", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "top-level-element")
    public JAXBElement<NamespaceAlias> createNamespaceAlias(NamespaceAlias value) {
        return new JAXBElement<NamespaceAlias>(_NamespaceAlias_QNAME, NamespaceAlias.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link If }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "if", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<If> createIf(If value) {
        return new JAXBElement<If>(_If_QNAME, If.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Wrapper }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "transform")
    public JAXBElement<Wrapper> createTransform(Wrapper value) {
        return new JAXBElement<Wrapper>(_Transform_QNAME, Wrapper.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/1999/XSL/Transform", name = "apply-imports", substitutionHeadNamespace = "http://www.w3.org/1999/XSL/Transform", substitutionHeadName = "char-instruction")
    public JAXBElement<AnyType> createApplyImports(AnyType value) {
        return new JAXBElement<AnyType>(_ApplyImports_QNAME, AnyType.class, null, value);
    }

}
