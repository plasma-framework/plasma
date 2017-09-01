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
package org.plasma.query.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.DataProperty;
import org.plasma.query.IntegralDataProperty;
import org.plasma.query.RealDataProperty;
import org.plasma.query.StringDataProperty;
import org.plasma.query.TemporalDataProperty;
import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property", propOrder = {
    "query",
    "as",
    "functions"
})
@XmlRootElement(name = "Property")
public class Property extends AbstractProperty 
    implements StringDataProperty, IntegralDataProperty, RealDataProperty, TemporalDataProperty    
{

    @XmlElement(name = "Query", namespace = "")
    protected Query query;
    @XmlElement(name = "As", namespace = "")
    protected Projection as;
    @XmlElement(name = "Function", namespace = "")
    protected List<Function> functions;
    @XmlAttribute(required = true)
    protected String name;    
    @XmlAttribute
    protected Boolean values;
    @XmlAttribute
    protected Boolean distinct;
    @XmlAttribute
    protected SortDirection direction;
    @XmlAttribute(name = "alias")
    protected String alias;

    /** 
     * Stores the physical name associated with this 
     * property. Can be used by service providers
     * for query post processing. This field is not
     * processed during XML or other serialization
     * operations.  
     */
    protected transient String physicalName;
    /** 
     * Stores the physical name bytes associated with this 
     * property. Can be used by service providers
     * for query post processing. This field is not
     * processed during XML or other serialization
     * operations.  
     */
    protected transient byte[] physicalNameBytes;

    
      //----------------/
     //- Constructors -/
    //----------------/

    public Property() {
        super();
    } 

    public Property(String name) {
        this();
        this.setName(name);
    } 

    public Property(String name, Path path) {
        this();
        this.setName(name);
        this.setPath(path);
    } 

    public Property(String name, org.plasma.query.model.SortDirection direction) {
        this();
        this.setName(name);
        this.setDirection(direction);
    } 

    public Property(String name, Path path, org.plasma.query.model.SortDirection direction) {
        this();
        this.setName(name);
        this.setPath(path);
        this.setDirection(direction);
    } 

    public Property(String name, org.plasma.query.model.FunctionName function) {
        this();
        this.setName(name);
		Function func = new Function(function);
		this.getFunctions().add(func);
    } 

    public Property(String name, Query query) {
        this();
        this.setName(name);
        this.setQuery(query);
    } 

    public Property(String name, Path path, Query query) {
        this();
        this.setName(name);
        this.setPath(path);
        this.setQuery(query);
    } 

    /**
     * Factory method returning a property for the given name. 
     */
    public static Property forName(String name)
    {
    	return new Property(name);
    }

    /**
     * Factory method returning a property for the given name and path. 
     */
    public static Property forName(String name, Path path)
    {
	    return new Property(name, path);
    }
    
    public static WildcardProperty wildcard()
    {
        return new WildcardProperty();
    }

    public static WildcardProperty wildcard(Path path)
    {
        return new WildcardProperty(path);
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#eq(java.lang.Object)
	 */
    public Expression eq(Object value)
    {
    	return Expression.eq(this, Literal.valueOf(value));
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#ne(java.lang.Object)
	 */
    public Expression ne(Object value)
    {
    	return Expression.ne(this, Literal.valueOf(value));
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#gt(java.lang.Object)
	 */
    public Expression gt(Object value)
    {
    	return Expression.gt(this, Literal.valueOf(value));
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#ge(java.lang.Object)
	 */
    public Expression ge(Object value)
    {
    	return Expression.ge(this, Literal.valueOf(value));
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#lt(java.lang.Object)
	 */
    public Expression lt(Object value)
    {
    	return Expression.lt(this, Literal.valueOf(value));
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#le(java.lang.Object)
	 */
    public Expression le(Object value)
    {
    	return Expression.le(this, Literal.valueOf(value));
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#between(java.lang.Object, java.lang.Object)
	 */
    public Expression between(Object min, Object max)
    {
    	return Expression.between(this, Literal.valueOf(min), Literal.valueOf(max));
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#like(java.lang.String)
	 */
    public Expression like(String value)
    {
    	return Expression.like(this, Literal.valueOf(value));
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#isNull()
	 */
    public Expression isNull()
    {
    	return Expression.eq(this, new NullLiteral());
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#isNotNull()
	 */
    public org.plasma.query.Expression isNotNull()
    {
    	return Expression.ne(this, new NullLiteral());
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#in(org.plasma.query.model.Query)
	 */
    public org.plasma.query.Expression in(org.plasma.query.Query subquery)
    {
    	return Expression.in(this, subquery.getModel());
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#notIn(org.plasma.query.model.Query)
	 */
    public org.plasma.query.Expression notIn(org.plasma.query.Query subquery)
    {
    	return Expression.notIn(this, subquery.getModel());
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#exists(org.plasma.query.model.Query)
	 */
    public org.plasma.query.Expression exists(org.plasma.query.Query subquery)
    {
    	return Expression.exists(this, subquery.getModel());
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#getQuery()
	 */
    public Query getQuery() {
        return query;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#setQuery(org.plasma.query.model.Query)
	 */
    public void setQuery(Query value) {
        this.query = (Query)value;
    }
    
    @Override
	public DataProperty min() {
		Function func = new Function(FunctionName.MIN);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public DataProperty max() {
		Function func = new Function(FunctionName.MAX);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public DataProperty sum() {
		Function func = new Function(FunctionName.SUM);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public DataProperty avg() {
		Function func = new Function(FunctionName.AVG);
		this.getFunctions().add(func);
		return this;
	}

    @Override
	public IntegralDataProperty abs() {
		Function func = new Function(FunctionName.ABS);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public RealDataProperty ceiling() {
		Function func = new Function(FunctionName.CEILING);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public RealDataProperty floor() {
		Function func = new Function(FunctionName.FLOOR);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public RealDataProperty round() {
		Function func = new Function(FunctionName.ROUND);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public StringDataProperty substringBefore(String value) {
		Function func = new Function(FunctionName.SUBSTRING_BEFORE);
		FunctionArg arg1 = new FunctionArg();
		func.getFunctionArgs().add(arg1);
		arg1.setName("arg1");
		arg1.setValue(value);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public StringDataProperty substringAfter(String value) {
		Function func = new Function(FunctionName.SUBSTRING_AFTER);
		FunctionArg arg1 = new FunctionArg();
		func.getFunctionArgs().add(arg1);
		arg1.setName("arg1");
		arg1.setValue(value);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public StringDataProperty normalizeSpace() {
		Function func = new Function(FunctionName.NORMALIZE_SPACE);
		this.getFunctions().add(func);
		return this;
	}

    @Override
	public StringDataProperty upperCase() {
		Function func = new Function(FunctionName.UPPER_CASE);
		this.getFunctions().add(func);
		return this;
	}
	
    @Override
	public StringDataProperty lowerCase() {
		Function func = new Function(FunctionName.LOWER_CASE);
		this.getFunctions().add(func);
		return this;
	}
		
	public DataProperty asc() {
		this.setDirection(SortDirection.ASC);
		return this;
	}
	
	public DataProperty desc() {
		this.setDirection(SortDirection.DESC);
		return this;
	}
   
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#getAs()
	 */
    public Projection getAs() {
        return as;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#setAs(org.plasma.query.model.Projection)
	 */
    public void setAs(Projection value) {
        this.as = value;
    }
    
    public List<Function> getFunctions() {
        if (functions == null) {
            functions = new ArrayList<Function>();
        }
        return this.functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String value) {
        this.physicalName = value;
    }
    
    public byte[] getPhysicalNameBytes() {
        return physicalNameBytes;
    }

    public void setPhysicalNameBytes(byte[] value) {
        this.physicalNameBytes = value;
    }

    public boolean isValues() {
        if (values == null) {
            return false;
        } else {
            return values;
        }
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#setValues(java.lang.Boolean)
	 */
    public void setValues(Boolean value) {
        this.values = value;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#isDistinct()
	 */
    public boolean isDistinct() {
        if (distinct == null) {
            return false;
        } else {
            return distinct;
        }
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#setDistinct(java.lang.Boolean)
	 */
    public void setDistinct(Boolean value) {
        this.distinct = value;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#getDirection()
	 */
    public SortDirection getDirection() {
        if (direction == null) {
            return SortDirection.ASC;
        } else {
            return direction;
        }
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Property2#setDirection(org.plasma.query.model.SortDirectionValues)
	 */
    public void setDirection(SortDirection value) {
        this.direction = value;
    }
    
    /**
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }
    
    /**
     * Returns the path qualified name for this property, i.e. 
     * as the final element within its associated path, if a path 
     * exists for this property. If no
     * path exists for this property, simply the name of the 
     * property is returned. 
     * @return the path qualified name for this property
     */
    public String asPathString() {
    	StringBuilder buf = new StringBuilder();
    	if (this.getPath() != null) {
	    	for (PathNode node : this.getPath().getPathNodes()) {
	    		buf.append(node.getPathElement().getValue());
    			buf.append("/");
	    	}
    	}
    	buf.append(this.getName());
    	return buf.toString();
    }    

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
	    visitor.end(this);
    }

    public String getQualifiedName() {
    	if (qualifiedName == null) {
    	    StringBuilder buf = new StringBuilder();
    	    if (this.getFunctions().size() > 0) {
    	    	for (Function func : this.getFunctions()) {
    	    		buf.append(func.getName());
    	    		buf.append("(");
    	    		int i = 0;
    	    		for (FunctionArg arg : func.getFunctionArgs()) {
    	    			if (i > 0)
    	    				buf.append(",");
    	    			buf.append("[");
    	    			buf.append(arg.getName());
    	    			buf.append("=");
    	    			buf.append(arg.getValue());
    	    			buf.append("]");
    	    			i++;
    	    		}
    	    	}
    	    }
    	    if (this.alias != null) {
    	    	buf.append("(");
    	    	buf.append(this.alias);
    	    	buf.append(")");
    	    }
    	    if (this.path != null) {
    	    	for (PathNode node : this.path.getPathNodes()) {
    	    		buf.append(node.getPathElement().getValue());
	    			buf.append(".");
    	    	}
    	    }
    	    buf.append(this.name);
    	    if (this.getFunctions().size() > 0) {
    	    	for (Function func : this.getFunctions()) {
    	    		buf.append(")");
    	    	}
    	    }
    	    if (this.getAs() != null) {
    	    	buf.append(" ");
    	    	buf.append(this.getAs().getName());
    	    }
    	    qualifiedName = buf.toString();
    	}
    	return qualifiedName;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getQualifiedName() == null) ? 0 : getQualifiedName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (getQualifiedName() == null) {
			if (other.getQualifiedName() != null)
				return false;
		} else if (!getQualifiedName().equals(other.getQualifiedName()))
			return false;
		return true;
	}

	@Override
	public int compareTo(AbstractProperty o) {
		return getQualifiedName().compareTo(o.getQualifiedName());
	}
	
	public String toString() {
		return this.getQualifiedName();
	}
}
