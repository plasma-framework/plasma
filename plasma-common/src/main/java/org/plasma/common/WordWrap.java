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
package org.plasma.common;

import java.text.BreakIterator;
import java.util.Locale;

public class WordWrap {

  // Constants used by escapeHTMLTags
  private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
  private static final char[] AMP_ENCODE = "&amp;".toCharArray();
  private static final char[] LT_ENCODE = "&lt;".toCharArray();
  private static final char[] GT_ENCODE = "&gt;".toCharArray();

  private WordWrap() {
    // Not instantiable.
  }

  /**
   * Reformats a string where lines that are longer than <tt>width</tt> are
   * split apart at the earliest wordbreak or at maxLength, whichever is sooner.
   * If the width specified is less than 5 or greater than the input Strings
   * length the string will be returned as is.
   * <p/>
   * Please note that this method can be lossy - trailing spaces on wrapped
   * lines may be trimmed.
   *
   * @param input
   *          the String to reformat.
   * @param width
   *          the maximum length of any one line.
   * @return a new String with reformatted as needed.
   */
  public static String wordWrap(String input, int width, Locale locale) {
    // protect ourselves
    if (input == null) {
      return "";
    } else if (width < 5) {
      return input;
    } else if (width >= input.length()) {
      return input;
    }

    StringBuilder buf = new StringBuilder(input);
    boolean endOfLine = false;
    int lineStart = 0;

    for (int i = 0; i < buf.length(); i++) {
      if (buf.charAt(i) == '\n') {
        lineStart = i + 1;
        endOfLine = true;
      }

      // handle splitting at width character
      if (i > lineStart + width - 1) {
        if (!endOfLine) {
          int limit = i - lineStart - 1;
          BreakIterator breaks = BreakIterator.getLineInstance(locale);
          breaks.setText(buf.substring(lineStart, i));
          int end = breaks.last();

          // if the last character in the search string isn't a space,
          // we can't split on it (looks bad). Search for a previous
          // break character
          if (end == limit + 1) {
            if (!Character.isWhitespace(buf.charAt(lineStart + end))) {
              end = breaks.preceding(end - 1);
            }
          }

          // if the last character is a space, replace it with a \n
          if (end != BreakIterator.DONE && end == limit + 1) {
            buf.replace(lineStart + end, lineStart + end + 1, "\n");
            lineStart = lineStart + end;
          }
          // otherwise, just insert a \n
          else if (end != BreakIterator.DONE && end != 0) {
            buf.insert(lineStart + end, '\n');
            lineStart = lineStart + end + 1;
          } else {
            buf.insert(i, '\n');
            lineStart = i + 1;
          }
        } else {
          buf.insert(i, '\n');
          lineStart = i + 1;
          endOfLine = false;
        }
      }
    }

    return buf.toString();
  }

}
