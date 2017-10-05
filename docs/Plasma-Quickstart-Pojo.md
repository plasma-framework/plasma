<img src="media/media/image1.png" alt="icon" width="31" height="31" />Plasma

TerraMeta Software, Inc.

<span id="_Toc494955158" class="anchor"></span>Plasma Quick Start (POJO)

PlasmaSDO® and PlasmaQuery® are registered

Trademarks of TerraMeta Software

<span id="_Toc135028939" class="anchor"><span id="_Toc495460092" class="anchor"><span id="_Toc498843305" class="anchor"><span id="_Toc24906349" class="anchor"></span></span></span></span>

**Introduction**
================

This step-by-step guide shows how to build a Maven project which generates a simple MySql data model with 2 tables, inserts data and reads data using only Java objects (POJO’s) as the source of metadata. It requires basic knowledge of the Java programing language and Apache Maven and assumes the following prerequisites.

-   Java JDK 1.7 or Above

-   Maven 3.x or Above

**Plasma Quick Start (POJO)**
=============================

**Add Build Time Plasma Dependencies**
--------------------------------------

Add the following dependencies to your Maven project, including plasma, an RDBMS provider, MySql client and a connection pooling library, DBCP.

```xml
&lt;dependency&gt;
&lt;groupId&gt;org.terrameta&lt;/groupId&gt;
&lt;artifactId&gt;plasma-core&lt;/artifactId&gt;
&lt;version&gt;2.0.0&lt;/version&gt;
&lt;/dependency&gt;
```

**Create POJOs **
-----------------

Create 3 java enumeration classes annotated with Plasma annotations. Enumerations rather than Java classes are annotated to facilitate reuse across multiple code generation and metadata integration contexts. The annotations capture structural metadata elements, as below.

-   Entity Type and Property Names and Aliases. See [Type](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/Type.html), [Alias](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/Alias.html).
-   Data Types. See [DataProperty](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/DataProperty.html), [ReferenceProperty](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/ReferenceProperty.html).
-   Cardinalities, Nullability and Visibility. See [DataProperty](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/DataProperty.html), [ReferenceProperty](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/ReferenceProperty.html).
-   Constraints. See [ValueConstraint](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/ValueConstraint.html), [EnumerationConstraint](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/EnumerationConstraint.html).
-   Inheritance Relationships (multiple inheritance is supported). See [Type](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/Type.html).
-   Associations Between Entities. See [ReferenceProperty](http://plasma-framework.github.io/plasma/apidocs/org/plasma/sdo/annotation/ReferenceProperty.html).

The below example enumerations which create a classic data model “Person-Organization” with a common superclass entity “Party”. It is intended to illustrate several of the available Plasma annotations, but the model may be easily simplified, for example use just a single entity.

```java
@Type(name = "Party", isAbstract = true)
public enum Party {
@Alias(physicalName = "CRTD_DT")
@DataProperty(dataType = DataType.Date, isNullable = false)
createdDate
}
```

```java
@Alias(physicalName = "PERSON")
@Type(superTypes = { Party.class })
public enum Person {
@Key(type = KeyType.primary)
@ValueConstraint(maxLength = "36")
@Alias(physicalName = "FN")
@DataProperty(dataType = DataType.String, isNullable = false)
firstName,
@Key(type = KeyType.primary)
@ValueConstraint(maxLength = "36")
@Alias(physicalName = "LN")
@DataProperty(dataType = DataType.String, isNullable = false)
lastName,
@ValueConstraint(totalDigits = "3")
@Alias(physicalName = "AGE")
@DataProperty(dataType = DataType.Int)
age,
@Alias(physicalName = "DOB")
@DataProperty(dataType = DataType.Date)
dateOfBirth,
@Alias(physicalName = "EMP")
@ReferenceProperty(targetClass = Organization.class, targetProperty = "employee")
employer;
}
```

```java
@Alias(physicalName = "ORG")
@Type(superTypes = { Party.class })
public enum Organization {
@Key(type = KeyType.primary)
@ValueConstraint(maxLength = "36")
@Alias(physicalName = "NAME")
@DataProperty(dataType = DataType.String, isNullable = false)
name,
@EnumConstraint(targetEnum = OrgCat.class)
@Alias(physicalName = "ORG\_CAT")
@DataProperty(dataType = DataType.String, isNullable = false)
category,
@Alias(physicalName = "PARENT")
@ReferenceProperty(isNullable = true, isMany = false, targetClass = Organization.class, targetProperty = "child")
parent,
@Alias(physicalName = "CHILD")
@ReferenceProperty(isNullable = true, isMany = true, targetClass = Organization.class, targetProperty = "parent")
child,
@Alias(physicalName = "EMPLOYEE")
@ReferenceProperty(isNullable = true, isMany = true, targetClass = Person.class, targetProperty = "employer")
employee;
}
```

**Create Namespace **
---------------------

In the same package as the above POJOs, create a file called package\_info.java with the below annotated package.

```java
@Alias(physicalName = "HR")
@Namespace(uri = "http://plasma-quickstart-pojo/humanresources")
@NamespaceProvisioning(rootPackageName = "quickstart.pojo.model")
@NamespaceService(storeType = DataStoreType.RDBMS,
providerName = DataAccessProviderName.JDBC,
properties = {
"org.plasma.sdo.access.provider.jdbc.ConnectionURL=jdbc:mysql://localhost:3306/hr?autoReconnect=true",
"org.plasma.sdo.access.provider.jdbc.ConnectionUserName=root",
"org.plasma.sdo.access.provider.jdbc.ConnectionPassword=yourpassword",
"org.plasma.sdo.access.provider.jdbc.ConnectionDriverName=com.mysql.jdbc.Driver",
"org.plasma.sdo.access.provider.jdbc.ConnectionProviderName=examples.quickstart.connect.DBCPConnectionPoolProvider",
"org.plasma.sdo.access.provider.jdbc.ConnectionPoolMinSize=1",
"org.plasma.sdo.access.provider.jdbc.ConnectionPoolMaxSize=10",
"org.apache.commons.dbcp.validationQuery=SELECT COUNT(\) FROM person",
"org.apache.commons.dbcp.testOnBorrow=false",
"org.apache.commons.dbcp.testOnReturn=false",
"org.apache.commons.dbcp.maxWait=30000",
"org.apache.commons.dbcp.testWhileIdle=false",
"org.apache.commons.dbcp.timeBetweenEvictionRunsMillis=30000",
"org.apache.commons.dbcp.minEvictableIdleTimeMillis=40000"
})
package examples.quickstart.pojo;
import org.plasma.config.annotation.NamespaceService;
import org.plasma.config.annotation.NamespaceProvisioning;
import org.plasma.sdo.annotation.Namespace;
import org.plasma.sdo.annotation.Alias;
import org.plasma.config.DataAccessProviderName;
import org.plasma.config.DataStoreType;
```


**Add Plasma Maven Plugin**
---------------------------

Add the Plasma Maven Plugin with 3 executions which generate data access and query (DSL) classes as well as a schema for MySql. See below Plasma Maven Plugin Configuration for complete listing. After adding the plugin and executions type:

maven generate-sources

The source code and MySql DDL should appear under target/generated-sources.

**Add Run Time Dependencies**
-----------------------------

Add the following dependencies to your Maven project, including an RDBMS service provider Cloudgraph RDB, MySql client and a connection pooling library, DBCP.

```xml
&lt;dependency&gt;
&lt;groupId&gt;org.cloudgraph&lt;/groupId&gt;
&lt;artifactId&gt;*cloudgraph*-*rdb*&lt;/artifactId&gt;
&lt;version&gt;1.0.7&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
&lt;groupId&gt;*mysql*&lt;/groupId&gt;
&lt;artifactId&gt;*mysql*-connector-java&lt;/artifactId&gt;
&lt;version&gt;5.1.23&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
&lt;groupId&gt;commons-*dbcp*&lt;/groupId&gt;
&lt;artifactId&gt;commons-*dbcp*&lt;/artifactId&gt;
&lt;version&gt;1.4&lt;/version&gt;
&lt;/dependency&gt;
```

Plasma Maven Plugin Configuration
=================================

&lt;plugin&gt;

&lt;groupId&gt;org.terrameta&lt;/groupId&gt;

&lt;artifactId&gt;plasma-*maven*-*plugin*&lt;/artifactId&gt;

&lt;version&gt;2.0.0&lt;/version&gt;

&lt;dependencies&gt;

&lt;dependency&gt;

&lt;groupId&gt;org.terrameta&lt;/groupId&gt;

&lt;artifactId&gt;plasma-core&lt;/artifactId&gt;

&lt;version&gt;2.0.0&lt;/version&gt;

&lt;/dependency&gt;

&lt;dependency&gt;

&lt;groupId&gt;org.cloudgraph&lt;/groupId&gt;

&lt;artifactId&gt;*cloudgraph*-*rdb*&lt;/artifactId&gt;

&lt;version&gt;1.0.7&lt;/version&gt;

&lt;/dependency&gt;

&lt;/dependencies&gt;

&lt;executions&gt;

&lt;execution&gt;

&lt;id&gt;*sdo*-create&lt;/id&gt;

&lt;configuration&gt;

&lt;action&gt;create&lt;/action&gt;

&lt;dialect&gt;java&lt;/dialect&gt;

&lt;additionalClasspathElements&gt;

&lt;param&gt;${basedir}/target/classes&lt;/param&gt;

&lt;/additionalClasspathElements&gt;

&lt;outputDirectory&gt;${basedir}/target/generated-sources/java&lt;/outputDirectory&gt;

&lt;/configuration&gt;

&lt;goals&gt;

&lt;goal&gt;*sdo*&lt;/goal&gt;

&lt;/goals&gt;

&lt;/execution&gt;

&lt;execution&gt;

&lt;id&gt;*dsl*-create&lt;/id&gt;

&lt;configuration&gt;

&lt;action&gt;create&lt;/action&gt;

&lt;dialect&gt;java&lt;/dialect&gt;

&lt;additionalClasspathElements&gt;

&lt;param&gt;${basedir}/target/classes&lt;/param&gt;

&lt;/additionalClasspathElements&gt;

&lt;outputDirectory&gt;${basedir}/target/generated-sources/java&lt;/outputDirectory&gt;

&lt;/configuration&gt;

&lt;goals&gt;

&lt;goal&gt;*dsl*&lt;/goal&gt;

&lt;/goals&gt;

&lt;/execution&gt;

&lt;execution&gt;

&lt;id&gt;*ddl*-create-*mysql*&lt;/id&gt;

&lt;configuration&gt;

&lt;action&gt;create&lt;/action&gt;

&lt;dialect&gt;mysql&lt;/dialect&gt;

&lt;additionalClasspathElements&gt;

&lt;param&gt;${basedir}/target/classes&lt;/param&gt;

&lt;/additionalClasspathElements&gt;

&lt;outputDirectory&gt;${basedir}/target/ddl&lt;/outputDirectory&gt;

&lt;outputFile&gt;mysql-create.sql&lt;/outputFile&gt;

&lt;/configuration&gt;

&lt;goals&gt;

&lt;goal&gt;*rdb*&lt;/goal&gt;

&lt;/goals&gt;

&lt;/execution&gt;

&lt;/executions&gt;

&lt;/plugin&gt;

.
