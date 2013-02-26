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

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Path", propOrder = {
    "pathNodes"
})
@XmlRootElement(name = "Path")
public class Path {

    @XmlElement(name = "PathNode", required = true)
    protected List<PathNode> pathNodes;


    public Path() {
        super();
    } 

    public Path(String e1) {
        this();
        addPathNode(createPathNode(e1));    
    }

    public Path(String e1, String e2) {
        this();
        addPathNode(createPathNode(e1));    
        addPathNode(createPathNode(e2));    
    }

    public Path(String e1, String e2, String e3) {
        this();
        addPathNode(createPathNode(e1));    
        addPathNode(createPathNode(e2));    
        addPathNode(createPathNode(e3));    
    }

    public Path(String e1, String e2, String e3, String e4) {
        this();
        addPathNode(createPathNode(e1));    
        addPathNode(createPathNode(e2));    
        addPathNode(createPathNode(e3));    
        addPathNode(createPathNode(e4));    
    }

    public Path(String e1, String e2, String e3, String e4, String e5) {
        this();
        addPathNode(createPathNode(e1));    
        addPathNode(createPathNode(e2));    
        addPathNode(createPathNode(e3));    
        addPathNode(createPathNode(e4));    
        addPathNode(createPathNode(e5));    
    }

    public Path(String e1, String e2, String e3, String e4, String e5, String e6) {
        this();
        addPathNode(createPathNode(e1));    
        addPathNode(createPathNode(e2));    
        addPathNode(createPathNode(e3));    
        addPathNode(createPathNode(e4));    
        addPathNode(createPathNode(e5));    
        addPathNode(createPathNode(e6));    
   }

    public Path(String e1, String e2, String e3, String e4, String e5, String e6, String e7) {
        this();
        addPathNode(createPathNode(e1));    
        addPathNode(createPathNode(e2));    
        addPathNode(createPathNode(e3));    
        addPathNode(createPathNode(e4));    
        addPathNode(createPathNode(e5));    
        addPathNode(createPathNode(e6));    
        addPathNode(createPathNode(e7));    
   }

    public Path(String e1, String e2, String e3, String e4, String e5, String e6, String e7,String e8) {
        this();
        addPathNode(createPathNode(e1));    
        addPathNode(createPathNode(e2));    
        addPathNode(createPathNode(e3));    
        addPathNode(createPathNode(e4));    
        addPathNode(createPathNode(e5));    
        addPathNode(createPathNode(e6));    
        addPathNode(createPathNode(e7));    
        addPathNode(createPathNode(e8));    
   }

    public Path(String[] elements) {
        this();
        for (int i = 0; i < elements.length; i++)
            addPathNode(createPathNode(elements[i]));    
    }

    public static Path forName(String name) {
    	return new Path(name);
    }

    public static Path forNames(String[] names) {
    	return new Path(names);
    }
    
    public void addPathNode(PathNode node) {
    	getPathNodes().add(node);
    }
    
    private PathNode createPathNode(String name) {
        return new PathNode(name);
    }
    
    public List<PathNode> getPathNodes() {
        if (pathNodes == null) {
            pathNodes = new ArrayList<PathNode>();
        }
        return this.pathNodes;
    }
    

}
