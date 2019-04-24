/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.sdo.access.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.plasma.sdo.access.AccessServiceContext;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.PlasmaDataAccessService;

public abstract class ClientSupport {

  protected PlasmaDataAccessService createProvider(String qualifiedName,
      AccessServiceContext context) {
    try {
      Class<?> providerClass = Class.forName(qualifiedName);
      Class<?>[] constructorArgClasses = {};
      Object[] constructorArgs = {};
      Constructor<?> constructor = providerClass.getConstructor(constructorArgClasses);
      PlasmaDataAccessService result = (PlasmaDataAccessService) constructor
          .newInstance(constructorArgs);
      result.initialize(context);
      return result;
    } catch (ClassNotFoundException e) {
      throw new DataAccessException(e);
    } catch (NoSuchMethodException e) {
      throw new DataAccessException(e);
    } catch (InstantiationException e) {
      throw new DataAccessException(e);
    } catch (IllegalAccessException e) {
      throw new DataAccessException(e);
    } catch (InvocationTargetException e) {
      throw new DataAccessException(e);
    }
  }

}
