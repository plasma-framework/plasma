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

public class Hash {
  static private final byte[] htable = { 33, 14, -68, -126, 50, 93, 118, -40, 108, -110, 27, -20,
      111, -65, -125, 52, -116, -96, -67, -58, -13, 103, -114, -50, 110, -123, -105, 71, 24, -70,
      76, 9, -83, -115, 94, -101, -30, 38, -27, 31, 29, 13, 51, -119, -34, 8, -3, 102, 61, 56, 127,
      55, 35, 37, -97, -25, 36, -64, -107, 96, 85, -71, 78, -79, -118, 63, -47, -100, -102, -18,
      11, -81, -88, -95, 7, -66, -17, 82, -35, -122, -113, 46, 72, 92, -59, -41, 10, 70, -52, 86,
      -49, -111, 69, 18, -103, -12, -80, 67, -44, -2, -7, -78, -74, -57, 114, -42, 19, -94, 4, 120,
      62, 21, -60, 104, 124, -36, -26, -55, -93, 3, -121, 100, -46, -21, -29, -90, -76, -45, -92,
      -53, -37, 49, 54, 64, 101, -89, 117, -28, -56, -85, 2, -128, 47, -51, 66, -38, 125, 90, 68,
      99, -91, 97, -77, 77, -1, 59, -84, 109, -32, 6, 81, -4, -82, 116, -75, -6, -72, 88, -39,
      -108, 84, -69, 105, 107, -16, 74, 58, 17, -117, 30, -104, 40, 39, 43, 22, 42, -87, 41, 12,
      28, -5, -124, -61, 112, -54, 15, -22, -99, 60, -112, 119, -43, -19, -11, 5, 126, 98, -33,
      -62, 83, -9, 91, 115, -15, 65, -86, 23, -106, -14, -31, -23, 45, -98, 26, 80, 53, -63, 16,
      87, 0, 48, 121, -8, 32, 95, 20, 75, 44, 106, 79, -10, 73, 113, -120, 25, -48, -73, 34, 89,
      -127, -24, 1, -109, 123, 57, 122 };

  static private byte[] int2bytes(int n, byte[] buf) {
    for (int i = 4; i > 0; n >>>= 8)
      buf[--i] = (byte) (n & 0xff);

    return buf;
  }

  static private byte[] long2bytes(long n, byte[] buf) {
    for (int i = 8; i > 0; n >>>= 8)
      buf[--i] = (byte) (n & 0xff);

    return buf;
  }

  static public int intHash(byte data[], int key) {
    byte[] hash = new byte[4];

    int2bytes(key, hash);
    int2bytes(intHash(hash), hash);

    return intHash(data, hash);
  }

  static public int intHash(byte data[]) {
    byte[] hash = new byte[] { /* 0xcafebabe */
    -54, -2, -70, -66 };

    return intHash(data, hash);
  }

  static public int intHash(byte[] data, byte[] hash) {
    byteHash(data, hash);

    return (((int) hash[0] << 24) | (((int) hash[1] << 24) >>> 8) | (((int) hash[2] << 24) >>> 16) | (((int) hash[3] << 24) >>> 24));
  }

  static public long longHash(byte data[], long key) {
    byte[] hash = new byte[8];

    long2bytes(key, hash);
    long2bytes(longHash(hash), hash);

    return longHash(data, hash);
  }

  static public long longHash(byte data[]) {
    byte[] hash = new byte[] { /* 0xdeadbeefcafebabe */
    -34, -82, -66, -17, -54, -2, -70, -66 };

    return longHash(data, hash);
  }

  static public long longHash(byte[] data, byte[] hash) {
    byteHash(data, hash);

    return (((long) hash[0] << 56) | (((long) hash[1] << 56) >>> 8)
        | (((long) hash[2] << 56) >>> 16) | (((long) hash[3] << 56) >>> 24)
        | (((long) hash[4] << 56) >>> 32) | (((long) hash[5] << 56) >>> 40)
        | (((long) hash[6] << 56) >>> 48) | (((long) hash[7] << 56) >>> 56));
  }

  static public byte[] byteHash(byte[] data, byte[] hash) {
    int i = -1;

    while (++i < data.length) {
      byte source = data[i];
      int j = hash.length;
      int s = 0;

      while (--j >= 0) {
        int index = hash[j] ^ (source + (byte) s++);
        hash[j] = htable[index < 0 ? index + 256 : index];
      }
    }

    return hash;
  }

  static public String toString(int hash) {
    String hex = Integer.toHexString(hash);
    String zeroes = "0x0000000";

    return zeroes.substring(0, 8 - hex.length() + 2) + hex;
  }

  static public String toString(long hash) {
    String hex = Long.toHexString(hash);
    String zeroes = "0x000000000000000";

    return zeroes.substring(0, 16 - hex.length() + 2) + hex;
  }

  static public void main(String args[]) throws Exception {
    System.out.println(intHash(args[0].getBytes("UTF-16BE")));
    System.out.println(toString(longHash(args[0].getBytes("UTF-16BE"))));
  }

  private int intKey;
  private long longKey;

  public Hash(byte[] bytes) {
    intKey = intHash(bytes);
    longKey = longHash(bytes);
  }

  public Hash(String string) {
    this(string.getBytes());
  }

  public String toString() {
    return toString(intKey);
  }

  public int hashCode() {
    return intKey;
  }

  public long longHashCode() {
    return longKey;
  }

  public boolean equals(Object o) {
    return ((o == this) || (o instanceof Hash) && ((Hash) o).longKey == longKey);
  }

  public void hash(byte[] bytes) {
    intKey = intHash(bytes, intKey);
    longKey = longHash(bytes, longKey);
  }

  public void hash(String string) {
    hash(string.getBytes());
  }
}
