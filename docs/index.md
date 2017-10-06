<img src="images/media/plasma_32_transp.png" alt="icon2" width="32" height="32"/>**PLASMA** 
======================================================================================

**TerraMeta Software, Inc.**  
PlasmaSDO<sup>®</sup> and PlasmaQuery<sup>®</sup> are registered
Trademarks of TerraMeta Software, Inc.

Overview
========
<span id="_Toc135028939" class="anchor"><span id="_Toc495460092" class="anchor"><span id="_Toc498843305" class="anchor"><span id="_Toc24906349" class="anchor"></span></span></span></span>
Plasma is a data store agnostic, object mapping and object query framework written in a Java™ with Maven tools for metadata ingestion and conversion.

**Detailed Documentation**
==========================

| **Document** | **Description** |
|  --------------------- | --------------------- |
| [**Plasma Architecture Overview**](http://plasma-framework.github.io/plasma/arch_overview/Plasma-Architecture-Overview) | General technical background plus architecture components with examples |
| [**Plasma UML Profile Specification**](http://plasma-framework.github.io/plasma/uml-profile/Plasma-UML-Profile)         | Specification for the UML profile used for annotating UML models for use with Plasma |
| [**Plasma API DOCS**](http://plasma-framework.github.io/plasma/apidocs/index.html)                                      | Generated javadoc API documentation tree |


**How It Works**
================

At its core, the Plasma implementation contains a directed graph or digraph model and a set of metadata driven graph traversal algorithms. Data Objects under Plasma form a digraph transparently as a client manipulates the SDO API, graph edges or links being automatically created and used internally to manage associations between Data Object nodes. Clients can assemble a Data Graph ad-hoc or query for a graph. Several API implementing the Visitor pattern[1] are then available to facilitate custom traversal operations on a graph.

**Code Generation.** The build-time code generation (provisioning) tools are compatible-with but not dependent upon any Integrated Development Environment (IDE), but are rather are geared for larger scale enterprise projects needing support for [**Maven**](http://maven.apache.org/) and continuous integration build environments such as [**Hudson**](http://hudson-ci.org). Build files typically access a set of command-line provisioning tools using **the Plasma Maven Plugin** triggering a variety of operations. In general, application UML model artifacts in XMI format[2] annotated with the **Plasma UML Profile** are first loaded and merged using the [**FUML**](http://portal.modeldriven.org/project/foundationalUML) runtime, then projected as a whole or in part onto an intermediate provisioning model, typically in-memory. The provisioning model is then merged with configuration information and transformed into one of several available target outputs, comprised of source code or models specific to a particular context or technology platform. Where a particular technology target is not provided, the intermediate provisioning model can be marshaled as XML, custom transformations using XSLT for instance converting the provisioning XML document into almost any target format.

**Requirements**
================

**Java Runtime**

The latest [JDK or JRE version 1.7.xx or 1.8.xx](http://www.java.com/en/download/manual.jsp) for Linux, Windows, or Mac OS X must be installed in your environment; we recommend the Oracle JDK.
To check the Java version installed, run the command:

$ java -version

Plasma is tested with the Oracle JDKs; it may work with other JDKs such as Open JDK, but it has not been tested with them.
Once you have installed the JDK, you'll need to set the JAVA\_HOME environment variable.

**Getting Started**
===================

Add the following dependencies to any Apache Maven POM files (or your build system's equivalent configuration), in order to make use of Plasma classes.  
All versions of Plasma can be found on Maven Central.

**Plasma SDO**

For the Plasma library use the following artifact.

```xml
<dependency>
  <groupId>org.plasma</groupId>
  <artifactId>plasma-sdo</artifactId>
  <version>1.2.4</version>
</dependency>
```

**Detailed Documentation**
==========================

| **Document** | **Description** |
|  --------------------- | --------------------- |
| [**Plasma Architecture Overview**](http://plasma-framework.github.io/plasma/arch_overview/Plasma-Architecture-Overview) | General technical background plus architecture components with examples |
| [**Plasma UML Profile Specification**](http://plasma-framework.github.io/plasma/uml-profile/Plasma-UML-Profile)         | Specification for the UML profile used for annotating UML models for use with Plasma |
| [**Plasma API DOCS**](http://plasma-framework.github.io/plasma/apidocs/index.html)                                      | Generated javadoc API documentation tree |

