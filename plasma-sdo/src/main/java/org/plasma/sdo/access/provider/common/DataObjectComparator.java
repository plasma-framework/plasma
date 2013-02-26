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
package org.plasma.sdo.access.provider.common;

// java imports
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.core.CoreObject;


public class DataObjectComparator implements Comparator
{
    private static int ASCENDING = 1;
    private static int DESCENDING = 2;
    
    private static Log log = LogFactory.getLog(DataObjectComparator.class);
            
    private List paths = new ArrayList();


    public DataObjectComparator()
    {
    }

    public void addAscending(String[] path)
    {
        paths.add(new PathInfo(path, ASCENDING));
    }

    public void addDescending(String[] path)
    {
        paths.add(new PathInfo(path, DESCENDING));
    }

    public int compare(Object o1, Object o2)
    {
        CoreObject vo1 = (CoreObject)o1;
        CoreObject vo2 = (CoreObject)o2;
        Iterator iterator = paths.iterator();
        while (iterator.hasNext())
        {
            PathInfo info = (PathInfo)iterator.next();
            Object ep1 = findEndpoint(vo1, info.path);
            Object ep2 = findEndpoint(vo2, info.path);
            int result;
            if (info.direction == ASCENDING)
                result = comp(ep1, ep2);
            else
                result = comp(ep2, ep1);
            if (result != 0)
                return result;
            else
                continue;
        }
        return 0; // equal                    
    } 

    public int comp(Object ep1, Object ep2)
    {
        int result = 0;
        if (ep1 != null && ep2 != null)
        {
            if (ep1 instanceof Comparable)
                result = ((Comparable)ep1).compareTo(ep2);
            else
                log.warn("endpoint class not comparable, " + ep1.getClass().getName());
        }
        else if (ep1 == null && ep2 != null)
            result = -1; // assume a null object is "less than" a non-null object
        else if (ep2 == null && ep1 != null)
            result = 1;
            
        return result;            
    } 
    
    private Object findEndpoint(CoreObject vo, String[] path)
    {
        CoreObject contextVo = vo;
        for (int i = 0; i < path.length-1; i++)
        {
            Object obj = contextVo.get(path[i]);
            if (obj == null)
                return null;
            if (!(obj instanceof CoreObject))
            {
                log.warn("traversal expected value object, found " + obj.getClass().getName());
                return null;
            }
            contextVo = (CoreObject)obj;
        }    
        return contextVo.get(path[path.length-1]);
    }
    
    public class PathInfo 
    {
        public String[] path;
        public int direction;
        
        private PathInfo() {}
        public PathInfo(String[] path, int direction)
        {
            this.path = path;
            this.direction = direction;
        }
    }
}