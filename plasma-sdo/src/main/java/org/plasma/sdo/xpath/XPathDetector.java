package org.plasma.sdo.xpath;

public class XPathDetector {
	public static final String PREDICATE_START = "[";
	public static final String PREDICATE_END = "]";
	
	public static final String[] TOKENS = {
		"/", "[", ".."
	};
	
	public static boolean isXPath(String path) {
		for (String token : XPathDetector.TOKENS) {
		    if (path.contains(token))
		    	return true;
		}
		return false;
	}
}
