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
package org.plasma.mojo;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.plexus.component.configurator.AbstractComponentConfigurator;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.composite.ObjectWithFieldsConverter;
import org.codehaus.plexus.component.configurator.converters.special.ClassRealmConverter;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;

/**
 * A custom ComponentConfigurator which adds the project's runtime classpath elements
 * to the mojo classpath. Use the configurator javadoc annotation with a value of
 * 'include-project-dependencies' on your mojo class. 
 *
 * @author Brian Jackson
 * @author Scott Cinnamond
 * @since 1.1.3
 *
 * @plexus.component role="org.codehaus.plexus.component.configurator.ComponentConfigurator"
 *                   role-hint="include-project-dependencies"
 * @plexus.requirement role="org.codehaus.plexus.component.configurator.converters.lookup.ConverterLookup"
 *                   role-hint="default"
 */
public class IncludeProjectDependenciesComponentConfigurator extends AbstractComponentConfigurator { 

    private static Log log =LogFactory.getLog(
    		IncludeProjectDependenciesComponentConfigurator.class); 

    public void configureComponent( Object component, PlexusConfiguration configuration,
                                    ExpressionEvaluator expressionEvaluator, ClassRealm containerRealm,
                                    ConfigurationListener listener )
        throws ComponentConfigurationException {

        addProjectDependenciesToClassRealm(expressionEvaluator, containerRealm);

        converterLookup.registerConverter( new ClassRealmConverter( containerRealm ) );

        ObjectWithFieldsConverter converter = new ObjectWithFieldsConverter();

        converter.processConfiguration( converterLookup, component, containerRealm.getClassLoader(), configuration,
                                        expressionEvaluator, listener );
    }

    private void addProjectDependenciesToClassRealm(ExpressionEvaluator expressionEvaluator, ClassRealm containerRealm) throws ComponentConfigurationException {
        List<String> runtimeClasspathElements;
        try {
            //noinspection unchecked
            runtimeClasspathElements = (List<String>) expressionEvaluator.evaluate("${project.runtimeClasspathElements}");
        } catch (ExpressionEvaluationException e) {
            throw new ComponentConfigurationException("There was a problem evaluating: ${project.runtimeClasspathElements}", e);
        }

        // Add the project dependencies to the ClassRealm
        final URL[] urls = buildURLs(runtimeClasspathElements);
        for (URL url : urls) {
            containerRealm.addConstituent(url);
        }
    }

    private URL[] buildURLs(List<String> runtimeClasspathElements) throws ComponentConfigurationException {
        // Add the projects classes and dependencies
        List<URL> urls = new ArrayList<URL>(runtimeClasspathElements.size());
        for (String element : runtimeClasspathElements) {
            try {
                final URL url = new File(element).toURI().toURL();
                urls.add(url);
 
                if (log.isDebugEnabled()) {
                    log.debug("added: " + url);
                }
            } catch (MalformedURLException e) {
                throw new ComponentConfigurationException("Unable to access project dependency: " + element, e);
            }
        }

        // Add the plugin's dependencies (so Trove stuff works if Trove isn't on
        return urls.toArray(new URL[urls.size()]);
    }

}

