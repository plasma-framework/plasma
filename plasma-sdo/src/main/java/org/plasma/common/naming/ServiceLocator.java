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
package org.plasma.common.naming;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiceLocator {
	private static ServiceLocator me;
    protected static Log log = LogFactory.getLog(ServiceLocator.class);

	private ServiceLocator() throws NamingException {
	}

	// Returns the instance of ServiceLocator class
	public static ServiceLocator getInstance() throws NamingException {
		if (me == null) {
			initInstance(); // double-checked locking pattern
		}
		return me;
	}

    private static synchronized void initInstance() throws NamingException {
        if (me == null) {
            me = new ServiceLocator();
    	}
    }

    /**
     * @deprecated use getStub(Class ejbInterfaceClass) or getStub(String ejbName) instead
     * @param ejbName
     * @param ejbInterfaceClass
     * @return
     * @throws NamingException
     */
    public Object getStub(String ejbName, Class ejbInterfaceClass) throws NamingException {
    	Context ctx = InitialContextFactory.getInstance().getInitialContext();
        //Object stub = ctx.lookup(ejbInterfaceClass.getName());
        Object stub = ctx.lookup(ejbName);
        return stub;
    }

    /**
     * Get an ejb stub found with the supplied interface class in the local JNDI tree
     * 
     * @param ejbInterfaceClass
     * @return An ejb stub implementing the supplied interface class.
     * @throws NamingException
     */
    public Object getStub(Class ejbInterfaceClass) throws NamingException {
    	Context ctx = InitialContextFactory.getInstance().getInitialContext();
        return ctx.lookup("java:/comp/env/ejb/" + ejbInterfaceClass.getSimpleName());
    }
    /**
     * Get an ejb stub found with the supplied name
     * @param ejbName
     * @return An ejb stub found at the supplied name
     * @throws NamingException
     */
    public Object getStub(String ejbName) throws NamingException {
    	Context ctx = InitialContextFactory.getInstance().getInitialContext();
        Object stub = ctx.lookup(ejbName);
        return stub;
    }
}
