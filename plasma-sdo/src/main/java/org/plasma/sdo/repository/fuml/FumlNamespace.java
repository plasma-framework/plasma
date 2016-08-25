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
package org.plasma.sdo.repository.fuml;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.sdo.repository.Namespace;

class FumlNamespace extends FumlElement<org.modeldriven.fuml.repository.Package> implements Namespace{

	private String packageQualifiedName; 
		
	public FumlNamespace(org.modeldriven.fuml.repository.Package namespace) {
		super(namespace);
	}
		
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Namespace#getUri()
	 */
	@Override
	public String getUri() {
        List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(this.element);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDONamespace) {
                	SDONamespace sdoNamespaceStereotype = (SDONamespace)stereotype.getDelegate();
                	return sdoNamespaceStereotype.getUri();
                }
        }
        return null;
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Namespace#getQualifiedPackageName()
	 */
	@Override
	public String getQualifiedPackageName() {
		if (packageQualifiedName == null) {
			List<String> names = new ArrayList<String>();
			names.add(this.element.getName());
			org.modeldriven.fuml.repository.Package parent = this.element.getNestingPackage();
			while (parent != null) {
				names.add(parent.getName());
				parent = parent.getNestingPackage();
			}
			StringBuilder buf = new StringBuilder();
			int len = names.size();
			for (int i = len-1; i >= 0; i--) {
				if (i < len-1)
					buf.append(".");
				String name = names.get(i);
				buf.append(name);
			}
			this.packageQualifiedName = buf.toString();
		}
		return this.packageQualifiedName;
	}	 	

}
