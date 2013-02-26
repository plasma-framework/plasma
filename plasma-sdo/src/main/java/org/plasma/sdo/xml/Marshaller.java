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
package org.plasma.sdo.xml;


import org.plasma.sdo.helper.DataConverter;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.XMLDocument;

/**
 * An data-graph traversal based assembler/builder 
 * abstract superclass.
 */
public abstract class Marshaller extends XMLProcessor {

    protected XMLDocument document;
    protected MarshallerFlavor flavor;
    
    @SuppressWarnings("unused")
	private Marshaller() {}
	
    protected Marshaller(MarshallerFlavor flavor, XMLDocument document) {
    	super();
    	this.flavor = flavor;
    	this.document = document;
    	if (this.document == null)
    		throw new IllegalArgumentException("expected 'document' argument");
    }

    protected Marshaller(XMLDocument document, XMLOptions options) {
    	super(options);
    	this.document = document;
    	if (this.document == null)
    		throw new IllegalArgumentException("expected 'document' argument");
    }
    
    protected String fromObject(Type sourceType, Object value) {
		return DataConverter.INSTANCE.toString(sourceType, value);
	}	
		
    protected class PathNode {
        
        private DataObject target;
        private DataObject source;
        private Property sourceProperty;
        private Object userObject;
        private boolean terminated;
        
        
        @SuppressWarnings("unused")
        private PathNode() {}
        
        public PathNode(DataObject target, Object userObject, 
                DataObject source, Property sourceProperty) {
            this.target = target;
            this.userObject = userObject;
            this.source = source;
            this.sourceProperty = sourceProperty;
        }
        
        public PathNode(DataObject target,  
                DataObject source, Property sourceProperty) {
            this.target = target;
            this.source = source;
            this.sourceProperty = sourceProperty;
        }

        public DataObject getTarget() {
            return target;
        }

        public DataObject getSource() {
            return source;
        }

        public Property getSourceProperty() {
            return sourceProperty;
        }

        public Object getUserObject() {
            return userObject;
        }

		public boolean isTerminated() {
			return terminated;
		}

		public void setTerminated(boolean terminated) {
			this.terminated = terminated;
		}
        
        
    }
	
}
