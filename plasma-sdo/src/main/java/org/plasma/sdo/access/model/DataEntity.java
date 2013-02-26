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
package org.plasma.sdo.access.model;

import java.lang.reflect.InvocationTargetException;

import commonj.sdo.Property;
import commonj.sdo.Type;


/**
 *
 **/
public interface DataEntity
{
    public Object get(String name)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException;

    public Object set(String name, Object o)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException;

    public void add(String name, Object o)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException;

    public Object getEntityId()
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException;

    public Object setEntityId(Object id)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException;

    public void accept(EntityVisitor visitor); 

    public void accept(EntityVisitor visitor, boolean depthFirst); 

    public Type getType();

    public Property getIdProperty();

}
