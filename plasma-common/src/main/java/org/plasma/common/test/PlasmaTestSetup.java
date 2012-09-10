package org.plasma.common.test;

import junit.extensions.TestSetup;
import junit.framework.TestSuite;

/**
 */
public class PlasmaTestSetup extends TestSetup
{
   public static PlasmaTestSetup newTestSetup(Class testClass)
   {
      return new PlasmaTestSetup(testClass);
   }
   
   protected PlasmaTestSetup(Class testClass)
   {
      super(new TestSuite(testClass));
   }   
}
