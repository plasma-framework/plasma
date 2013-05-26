package org.plasma.provisioning.rdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.rdb.oracle.any.sys.Version;
import org.plasma.provisioning.rdb.oracle.any.sys.query.QVersion;
import org.plasma.sdo.access.client.JDBCPojoDataAccessClient;

import commonj.sdo.DataGraph;

public class OracleVersionFinder {
	private static Log log = LogFactory.getLog(
			OracleVersionFinder.class); 
    protected JDBCPojoDataAccessClient client = new JDBCPojoDataAccessClient();
	public OracleVersion findVersion() {
    	QVersion version = QVersion.newQuery();
    	version.select(version.wildcard());
    	version.where(version.banner().like("Oracle Database*"));
    	DataGraph[] results = client.find(version);
    	if (results == null || results.length == 0)
    		throw new ProvisioningException("no Oracle version information found");
		if (results.length > 1)
			log.warn("found multiple version rows - ignoring");
    	Version vers = (Version)results[0].getRootObject();
    	if (vers.getBanner().contains("11g")) {
    		return OracleVersion._11g;
    	}
    	else if (vers.getBanner().contains("10g")) {
    		return OracleVersion._10g;
    	}
    	else if (vers.getBanner().contains("91")) {
    		return OracleVersion._9i;
    	}
    	else
    		return OracleVersion._unknown;
	}
}
