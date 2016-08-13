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
package org.plasma.text.lang3gl.java;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;

public class TypeAssembler extends DefaultStreamAssembler {

    private static Log log =LogFactory.getLog(
    		TypeAssembler.class); 
    
	public TypeAssembler(ProvisioningModel provisioningModel, Lang3GLFactory factory,
			Lang3GLOperation operation, File dest) {
		super(provisioningModel, factory, operation, dest);
	}

	@Override
	public void createEnumerationClasses() throws IOException 
    {
		// noop
    }
	
	@Override
	public void createInterfaceClasses() throws IOException 
    {
		// noop
    }
    
	@Override
	public void createImplementationClasses() throws IOException 
    {
    }

	@Override
	public void createInterfacePackageDocs() throws IOException {
		// noop
	}   
	

}
