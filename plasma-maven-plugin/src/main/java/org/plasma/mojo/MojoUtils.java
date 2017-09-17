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

package org.plasma.mojo;

import java.util.ArrayList;
import java.util.List;

import org.plasma.provisioning.cli.OptionPair;

public class MojoUtils {
  public static String[] toArgs(List<OptionPair> pairs) {
    List<String> list = new ArrayList<String>();
    for (int i = 0; i < pairs.size(); i++) {
      OptionPair pair = pairs.get(i);
      list.add("--" + pair.getOption().name());
      list.add(pair.getValue());
    }
    String[] result = new String[list.size()];
    list.toArray(result);
    return result;
  }
}
