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
package org.plasma.text.lang3gl;

import org.plasma.metamodel.Model;


public abstract class DefaultLang3GLAssembler {
	protected Model packages;
	protected Lang3GLFactory factory;
	protected Lang3GLOperation operation;
	
	@SuppressWarnings("unused")
	private DefaultLang3GLAssembler() {}
	
	protected DefaultLang3GLAssembler(Model packages,
			Lang3GLFactory factory, 
			Lang3GLOperation operation) {
		this.packages = packages;
		this.operation = operation;
    	this.factory = factory;
	}

}
