package org.plasma.sdo.repository;

import java.util.List;

import org.modeldriven.fuml.repository.Classifier;

public interface Repository extends org.modeldriven.fuml.repository.Repository {

    /**
     * Returns all SDO namespace URIs within the repository.
     * @return all SDO namespace URIs within the repository.
     */
	public List<String> getAllNamespaceUris();
	
	public List<Namespace> getAllNamespaces();
	
    /**
     * Return the Classifier instances specified by the given uri.
     * @param uri The namespace uri;
     * @return the Classifier instances specified by the given uri.
     */
	public List<Classifier> getClassifiers(String uri);
	
	public RelationCache getRelationCache();
}
