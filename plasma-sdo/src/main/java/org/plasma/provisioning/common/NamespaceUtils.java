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

package org.plasma.provisioning.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NamespaceUtils {

  /**
   * Tokenizes the given URI as an array of names suitable to be used as a UML
   * package hierarchy.
   * 
   * @param uri
   * @return the name array
   */
  public static String[] toPackageTokens(String uri) {
    List<String> list = new ArrayList<String>();

    try {
      URL url = new URL(uri);
      String[] authority = url.getAuthority().split("\\.");
      for (int i = authority.length - 1; i >= 0; i--)
        if (authority[i] != null && authority[i].trim().length() > 0)
          list.add(authority[i]);
      if (url.getPath() != null) {
        String[] path = url.getPath().split("\\/");
        for (int i = 0; i < path.length; i++)
          if (path[i] != null && path[i].trim().length() > 0)
            list.add(path[i]);
      }
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }

    String[] result = new String[list.size()];
    list.toArray(result);

    return result;
  }

  /**
   * Tokenizes the given URI and assembles a qualified java package name.
   * 
   * @param uri
   * @return the qualified package name
   */
  public static String toPackageName(String uri) {
    String[] tokens = toPackageTokens(uri);
    StringBuilder buf = new StringBuilder();
    for (int i = tokens.length - 1; i >= 0; i--) {
      if (i < tokens.length - 1)
        buf.append(".");
      buf.append(tokens[i]);
    }
    return buf.toString();
  }

}
