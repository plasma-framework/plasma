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

package org.plasma.text;

public class TextBuilder {
  private StringBuilder buf = new StringBuilder();
  private String lineSep;
  private String indent;

  @SuppressWarnings("unused")
  private TextBuilder() {
  }

  public TextBuilder(String lineSep, String indent) {
    super();
    this.lineSep = lineSep;
    this.indent = indent;
  }

  /**
   * Starts a new line in the buffer and prepends the given number of
   * indentation tokens, then appends the given text,
   * 
   * @param num
   *          the number of indentation characters / tokens to perpend
   * @param value
   *          the text value.
   */
  public void appendln(int num, String value) {
    appendln(num);
    append(value);
  }

  public void appendln() {
    buf.append(lineSep);
  }

  protected void appendln(int num) {
    buf.append(lineSep);
    for (int i = 0; i < num; i++)
      buf.append(indent);
  }

  public int length() {
    return buf.length();
  }

  public int capacity() {
    return buf.capacity();
  }

  public int hashCode() {
    return buf.hashCode();
  }

  public void ensureCapacity(int minimumCapacity) {
    buf.ensureCapacity(minimumCapacity);
  }

  public void trimToSize() {
    buf.trimToSize();
  }

  public boolean equals(Object obj) {
    return buf.equals(obj);
  }

  public void setLength(int newLength) {
    buf.setLength(newLength);
  }

  public StringBuilder append(Object obj) {
    return buf.append(obj);
  }

  public StringBuilder append(String str) {
    return buf.append(str);
  }

  public StringBuilder append(StringBuffer sb) {
    return buf.append(sb);
  }

  public char charAt(int index) {
    return buf.charAt(index);
  }

  private StringBuilder append(CharSequence s) {
    return buf.append(s);
  }

  public StringBuilder append(CharSequence s, int start, int end) {
    return buf.append(s, start, end);
  }

  public int codePointAt(int index) {
    return buf.codePointAt(index);
  }

  public StringBuilder append(char[] str) {
    return buf.append(str);
  }

  public StringBuilder append(char[] str, int offset, int len) {
    return buf.append(str, offset, len);
  }

  public StringBuilder append(boolean b) {
    return buf.append(b);
  }

  public StringBuilder append(char c) {
    return buf.append(c);
  }

  public StringBuilder append(int i) {
    return buf.append(i);
  }

  public StringBuilder append(long lng) {
    return buf.append(lng);
  }

  public StringBuilder append(float f) {
    return buf.append(f);
  }

  public StringBuilder append(double d) {
    return buf.append(d);
  }

  public StringBuilder appendCodePoint(int codePoint) {
    return buf.appendCodePoint(codePoint);
  }

  public StringBuilder delete(int start, int end) {
    return buf.delete(start, end);
  }

  public int codePointBefore(int index) {
    return buf.codePointBefore(index);
  }

  public StringBuilder deleteCharAt(int index) {
    return buf.deleteCharAt(index);
  }

  public StringBuilder replace(int start, int end, String str) {
    return buf.replace(start, end, str);
  }

  public StringBuilder insert(int index, char[] str, int offset, int len) {
    return buf.insert(index, str, offset, len);
  }

  public StringBuilder insert(int offset, Object obj) {
    return buf.insert(offset, obj);
  }

  public StringBuilder insert(int offset, String str) {
    return buf.insert(offset, str);
  }

  public StringBuilder insert(int offset, char[] str) {
    return buf.insert(offset, str);
  }

  public StringBuilder insert(int dstOffset, CharSequence s) {
    return buf.insert(dstOffset, s);
  }

  public int codePointCount(int beginIndex, int endIndex) {
    return buf.codePointCount(beginIndex, endIndex);
  }

  public StringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
    return buf.insert(dstOffset, s, start, end);
  }

  public StringBuilder insert(int offset, boolean b) {
    return buf.insert(offset, b);
  }

  public StringBuilder insert(int offset, char c) {
    return buf.insert(offset, c);
  }

  public StringBuilder insert(int offset, int i) {
    return buf.insert(offset, i);
  }

  public StringBuilder insert(int offset, long l) {
    return buf.insert(offset, l);
  }

  public int offsetByCodePoints(int index, int codePointOffset) {
    return buf.offsetByCodePoints(index, codePointOffset);
  }

  public StringBuilder insert(int offset, float f) {
    return buf.insert(offset, f);
  }

  public StringBuilder insert(int offset, double d) {
    return buf.insert(offset, d);
  }

  public int indexOf(String str) {
    return buf.indexOf(str);
  }

  public int indexOf(String str, int fromIndex) {
    return buf.indexOf(str, fromIndex);
  }

  public int lastIndexOf(String str) {
    return buf.lastIndexOf(str);
  }

  public int lastIndexOf(String str, int fromIndex) {
    return buf.lastIndexOf(str, fromIndex);
  }

  public StringBuilder reverse() {
    return buf.reverse();
  }

  public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
    buf.getChars(srcBegin, srcEnd, dst, dstBegin);
  }

  public String toString() {
    return buf.toString();
  }

  public void setCharAt(int index, char ch) {
    buf.setCharAt(index, ch);
  }

  public String substring(int start) {
    return buf.substring(start);
  }

  public CharSequence subSequence(int start, int end) {
    return buf.subSequence(start, end);
  }

  public String substring(int start, int end) {
    return buf.substring(start, end);
  }

}
