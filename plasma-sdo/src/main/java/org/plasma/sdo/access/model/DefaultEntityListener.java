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

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultEntityListener {

    private static Log log = LogFactory.getFactory().getInstance(
    		DefaultEntityListener.class); 
	/*
    @OneToMany(targetEntity=InternationalRateArea.class, mappedBy="_country", fetch=FetchType.EAGER)
    public String foo;
    
    @Basic(optional=false)
    public String bar;
    */
    
	@PostPersist
	public void logAddition(Object pc) {
		log.debug("added: " + pc.getClass().getSimpleName());
	}

	@PreRemove
	public void logDeletion(Object pc) {
		log.debug("removed: " + pc.getClass().getSimpleName());
	}
}