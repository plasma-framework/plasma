package org.plasma.provisioning.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.plasma.metamodel.Model;
import org.plasma.metamodel.Package;

public interface ProvisioningModel {

	Model getModel();

	List<Package> getPackages();

	List<Package> getLeafPackages();

	Collection<TypeAdapter> getTypes();

	TypeAdapter[] getTypesArray();

	TypeAdapter findType(String key);
	
	Map<String, TypeAdapter> getTypeMap();
	
	Package getPackage(TypeAdapter type);

}