package org.plasma.sdo.repository;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.OpaqueBehavior;

public class Class_ extends Classifier {
	private org.modeldriven.fuml.repository.Class_ class_;

	public Class_(org.modeldriven.fuml.repository.Class_ class_) {
		super(class_);
		this.class_ = class_;
	}
	
	public String findOpaqueBehaviorBody(String name, String language) {
		return getOpaqueBehaviorBody(name, language, true);
	}
	
	public String getOpaqueBehaviorBody(String name, String language) {
		return getOpaqueBehaviorBody(name, language, false);
	}
	
	private String getOpaqueBehaviorBody(String name, String language, 
			boolean supressError) {
		
		String result = null;
		for (OpaqueBehavior behavior : this.class_.getOpaqueBehaviors()) {
			if (behavior.getName().equals(name) && behavior.getLanguage().equals(language)) {
				result = behavior.getBody();
			}				
		}
		if (result == null && !supressError)
			throw new RepositoryException("could not find opaque behavior for name: " +
					name + ", language: " + language);
		
		return result;
	}
	
	public List<OpaqueBehavior> getOpaqueBehaviors(String language) {
		
		List<OpaqueBehavior> result = new ArrayList<OpaqueBehavior>();
		for (OpaqueBehavior behavior : this.class_.getOpaqueBehaviors()) {
			if (behavior.getLanguage().equalsIgnoreCase(language)) {
				result.add(behavior);
			}				
		}
		return result;
	}
	
}
