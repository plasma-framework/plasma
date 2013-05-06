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

import org.plasma.platform.sdo.categorization.Category;
import org.plasma.platform.sdo.categorization.Taxonomy;
import org.plasma.query.model.From;
import org.plasma.query.model.Path;
import org.plasma.query.model.Property;
import org.plasma.query.model.Query;
import org.plasma.query.model.Select;
import org.plasma.query.model.Where;


public class TaxonomyQuery {
	
    public static Query createQuery(String name) {
        
        String[] paths = new String[] { 
                "version",
                "category/id",
                //"category/child/child[id >= 21 and id <= 50 and definition = 'foo']/name",
                "category/definition",
                "category/typeName",
                "category/child/id",
                "category/child/name",
                "category/child/definition",
                "category/child/typeName",
                "category/child/child/id",
                "category/child/child/name",
                "category/child/child/definition",
                "category/child/child/typeName",
                "category/child/child/child/id",
                "category/child/child/child/name",
                "category/child/child/child/definition",
                "category/child/child/child/typeName",
                "category/child/child/child/child/id",
                "category/child/child/child/child/name",
                "category/child/child/child/child/definition",
                "category/child/child/child/child/typeName",
                "category/child/child/child/child/child/id",
                "category/child/child/child/child/child/name",
                "category/child/child/child/child/child/definition",
                "category/child/child/child/child/child/typeName",
                "category/child/child/child/child/child/child/id",
                "category/child/child/child/child/child/child/name",
                "category/child/child/child/child/child/child/definition",
                "category/child/child/child/child/child/child/typeName",
                "category/child/child/child/child/child/child/child/id",
                "category/child/child/child/child/child/child/child/name",
                "category/child/child/child/child/child/child/child/definition",
                "category/child/child/child/child/child/child/child/typeName", 
            };     

        Select select = new Select(paths);
        Where where = new Where();
        where.addExpression(Property.forName(Category.PROPERTY.name.name(),
                new Path(Taxonomy.PROPERTY.category.name())).eq(
                        name));
        From from = new From(Taxonomy.TYPE_NAME_TAXONOMY,
        		Taxonomy.NAMESPACE_URI);        
        
        Query query = new Query(select, from, where);
        return query;
    }

}
