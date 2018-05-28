package org.plasma.common;

import java.io.Serializable;
import java.util.Arrays;

public class Key<T> implements Serializable {
  private static final long serialVersionUID = -4400857484478081281L;
  private static final String DELIM = "|";
  private static final String DELIM_REGEX = "\\|";
  private T[] values;
  private int hashCode;

  @SuppressWarnings("unused")
  private Key() {
  }

  @SafeVarargs
  public Key(T... values) {
    if (values == null || values.length == 0)
      throw new IllegalArgumentException("expected non-null and non-zero length array");
    this.values = values;
    this.hashCode = Arrays.hashCode(this.values);
  }

  public static String[] parse(String source) {
    String[] tokens = source.split(DELIM_REGEX);
    return tokens;
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Key<?> other = (Key<?>) obj;
    if (hashCode != other.hashCode)
      return false;
    return true;
  }

  @Override
  public String toString() {
    if (this.values.length == 1) {
      return String.valueOf(this.values[0]);
    } else {
      StringBuilder buf = new StringBuilder();
      int i = 0;
      for (T value : this.values) {
        if (i > 0)
          buf.append(DELIM);
        buf.append(String.valueOf(value));
        i++;
      }
      return buf.toString();
    }
  }

}
