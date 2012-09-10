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
