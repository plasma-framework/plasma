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
package org.plasma.platform.sdo;

import org.plasma.platform.sdo.personalization.RoleName;
import org.plasma.platform.sdo.personalization.query.QElement;
import org.plasma.platform.sdo.personalization.query.QProfile;
import org.plasma.query.Query;


public class ProfileQuery {

	public static Query createDefaultSettingQuery(RoleName role) {
        
		QElement query = QElement.newQuery();
		
		
		query.select(query.wildcard())
		     .select(query.defaultElementSetting().wildcard())
		     .select(query.defaultElementSetting().role().wildcard())
		     .select(query.defaultElementSetting().setting().wildcard())
		     .select(query.child().wildcard())
		     .select(query.child().defaultElementSetting().wildcard())
		     .select(query.child().defaultElementSetting().role().wildcard())
		     .select(query.child().defaultElementSetting().setting().wildcard())
		     .select(query.child().child().wildcard())
		     .select(query.child().child().defaultElementSetting().wildcard())
		     .select(query.child().child().defaultElementSetting().role().wildcard())
		     .select(query.child().child().defaultElementSetting().setting().wildcard());
		query.where(query.defaultElementSetting().role().name().eq(role.getInstanceName())
				.and(query.parent().isNotNull()));
		    
		/*
		// Note: do NOT traverse into profile-element-setting here
		// or we will get settings for (potentially) every user
		SelectModel select = new SelectModel(new String[] { 
                "*",
                "defaultElementSetting/*",
                "defaultElementSetting/role/*",
                "defaultElementSetting/setting/*",
                "child/*",
                "child/defaultElementSetting/*",
                "child/defaultElementSetting/role/*",
                "child/defaultElementSetting/setting/*",
                "child/child/*",
                "child/child/defaultElementSetting/*",
                "child/child/defaultElementSetting/role/*",
                "child/child/defaultElementSetting/setting/*",
                "child/child/child/*",
                "child/child/child/defaultElementSetting/*",
                "child/child/child/defaultElementSetting/role/*",
                "child/child/child/defaultElementSetting/setting/*",
            });         
        
    	FromModel from = new FromModel(Element.ETY_ELEMENT, Element.NAMESPACE_URI);        
        WhereModel where = new WhereModel();
        where.addExpression(PropertyModel.forName(Element.PTY_NAME,
        	new Path(Element.PTY_DEFAULT_ELEMENT_SETTING,
        			DefaultElementSetting.PTY_ROLE)).eq(
        			role.getInstanceName()));
        where.addExpression(ExpressionModel.and());
        where.addExpression(PropertyModel.forName(
        		Element.PTY_PARENT).isNull());
        
        
        QueryModel query = new QueryModel(select, from, where);
        */
        return query;		
	}
	
	public static Query createProfileQuery(String username) {
		
		QProfile query = QProfile.newQuery();
		query.select(query.user().username())
		     .select(query.user().person().firstName())
		     .select(query.user().person().lastName())
		     .select(query.user().person().ssn())
		     .select(query.user().userRole().role().name())
		     .select(query.profileElementSetting().setting().name())
		     .select(query.profileElementSetting().setting().value())
		     .select(query.profileElementSetting().role().name())
		     .select(query.profileElementSetting().element().name())
		     .select(query.profileElementSetting().element().elementType())
		     .select(query.profileElementSetting().element().child().name())
		     .select(query.profileElementSetting().element().child().elementType())
		     .select(query.profileElementSetting().element().child().child().name())
		     .select(query.profileElementSetting().element().child().child().elementType());
		query.where(query.user().username().eq(username));
		query.where(query.profileElementSetting().element().isNotNull());
		/*
        SelectModel select = new SelectModel(new String[] { 
        		//"seqId", //FIXME: don't want to include seq ids in any derived models - DAS to lookup by UUID external keys
        		//"user/seqId",
        		"user/username",
        		//"user/person/seqId",
        		"user/person/firstName",
        		"user/person/lastName",
        		"user/person/ssn",
                //"user/userRole/seqId",
                //"user/userRole/role/seqId",
                "user/userRole/role/name",
                //"profileElementSetting/seqId",
                //"profileElementSetting/setting/seqId",
                "profileElementSetting/setting/name",
                "profileElementSetting/setting/value",
                //"profileElementSetting/role/seqId",
                "profileElementSetting/role/name",
                //"profileElementSetting/element/seqId",
                "profileElementSetting/element/name",
                "profileElementSetting/element/elementType",
                //"profileElementSetting/element/child/seqId",
                "profileElementSetting/element/child/name",
                "profileElementSetting/element/child/elementType",
                //"profileElementSetting/element/child/child/seqId",
                "profileElementSetting/element/child/child/name",
                "profileElementSetting/element/child/child/elementType",
            });         
            
    	FromModel from = new FromModel(Profile.ETY_PROFILE, Profile.NAMESPACE_URI);        
        WhereModel where = new WhereModel("user[username='" + username + "']");
        //where.addExpression(Property.forName(User.PTY_USERNAME,
        //		new Path(Profile.PTY_USER)).eq(
        //						username));
        
        QueryModel query = new QueryModel(select, from, where);
        
        */
        return query;		
	}		
}
