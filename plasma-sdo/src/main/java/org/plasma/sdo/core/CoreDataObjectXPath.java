package org.plasma.sdo.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathHandler;

public class CoreDataObjectXPath extends BaseXPath {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getFactory().getInstance(
			CoreDataObjectXPath.class);

	protected CoreDataObjectXPath(String xpathExpr) throws JaxenException {
		super(xpathExpr);
	}



}
