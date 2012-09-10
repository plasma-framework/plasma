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