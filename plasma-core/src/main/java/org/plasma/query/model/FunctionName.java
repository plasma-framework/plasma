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

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for FunctionName.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="FunctionName"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="avg"/&gt;
 *     &lt;enumeration value="count"/&gt;
 *     &lt;enumeration value="max"/&gt;
 *     &lt;enumeration value="min"/&gt;
 *     &lt;enumeration value="sdtdev"/&gt;
 *     &lt;enumeration value="sum"/&gt;
 *     &lt;enumeration value="var"/&gt;
 *     &lt;enumeration value="cast"/&gt;
 *     &lt;enumeration value="convert"/&gt;
 *     &lt;enumeration value="abs"/&gt;
 *     &lt;enumeration value="acos"/&gt;
 *     &lt;enumeration value="asin"/&gt;
 *     &lt;enumeration value="atan"/&gt;
 *     &lt;enumeration value="ceiling"/&gt;
 *     &lt;enumeration value="cos"/&gt;
 *     &lt;enumeration value="cot"/&gt;
 *     &lt;enumeration value="exp"/&gt;
 *     &lt;enumeration value="floor"/&gt;
 *     &lt;enumeration value="log"/&gt;
 *     &lt;enumeration value="log10"/&gt;
 *     &lt;enumeration value="pi"/&gt;
 *     &lt;enumeration value="pow"/&gt;
 *     &lt;enumeration value="round"/&gt;
 *     &lt;enumeration value="sin"/&gt;
 *     &lt;enumeration value="sqrt"/&gt;
 *     &lt;enumeration value="square"/&gt;
 *     &lt;enumeration value="tan"/&gt;
 *     &lt;enumeration value="meta_col_precision"/&gt;
 *     &lt;enumeration value="meta_col_scale"/&gt;
 *     &lt;enumeration value="meta_name"/&gt;
 *     &lt;enumeration value="meta_physical_name"/&gt;
 *     &lt;enumeration value="meta_business_name"/&gt;
 *     &lt;enumeration value="meta_local_name"/&gt;
 *     &lt;enumeration value="rank"/&gt;
 *     &lt;enumeration value="row_number"/&gt;
 *     &lt;enumeration value="user"/&gt;
 *     &lt;enumeration value="substringBefore"/&gt;
 *     &lt;enumeration value="substringAfter"/&gt;
 *     &lt;enumeration value="normalizeSpace"/&gt;
 *     &lt;enumeration value="upperCase"/&gt;
 *     &lt;enumeration value="lowerCase"/&gt;
 *     &lt;enumeration value="yearFromDate"/&gt;
 *     &lt;enumeration value="monthFromDate"/&gt;
 *     &lt;enumeration value="dayFromDate"/&gt;
 *     &lt;enumeration value="timezoneFromDate"/&gt;
 *     &lt;enumeration value="offsets"/&gt;
 *     &lt;enumeration value="relevance"/&gt;
 *     &lt;enumeration value="score"/&gt;
 *     &lt;enumeration value="terms"/&gt;
 *     &lt;enumeration value="ngrams"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "FunctionName")
@XmlEnum
public enum FunctionName {

  /**
   * returns the average of the values in a group. Null values are ignored
   * 
   */
  @XmlEnumValue("avg")
  AVG("avg"),

  /**
   * returns the number of items in a group
   * 
   */
  @XmlEnumValue("count")
  COUNT("count"),

  /**
   * returns the maximum value in the expression
   * 
   */
  @XmlEnumValue("max")
  MAX("max"),

  /**
   * returns the minimum value in the expression
   * 
   */
  @XmlEnumValue("min")
  MIN("min"),

  /**
   * returns the statistical standard deviation of all values in the specified
   * expression
   * 
   */
  @XmlEnumValue("sdtdev")
  SDTDEV("sdtdev"),

  /**
   * returns the sum of all the values
   * 
   */
  @XmlEnumValue("sum")
  SUM("sum"),

  /**
   * returns the statistical variance of all values in the specified expression
   * 
   */
  @XmlEnumValue("var")
  VAR("var"),

  /**
   * returns an expression of one data type to another
   * 
   */
  @XmlEnumValue("cast")
  CAST("cast"),

  /**
   * returns an expression of one data type to another
   * 
   */
  @XmlEnumValue("convert")
  CONVERT("convert"),

  /**
   * returns mathematical function that returns the absolute (positive) value of
   * the specified numeric expression
   * 
   */
  @XmlEnumValue("abs")
  ABS("abs"),

  /**
   * returns mathematical function that returns the angle, in radians, whose
   * cosine is the specified float expression
   * 
   */
  @XmlEnumValue("acos")
  ACOS("acos"),

  /**
   * returns the angle, in radians, whose sine is the specified float
   * expression. This is also called arcsine
   * 
   */
  @XmlEnumValue("asin")
  ASIN("asin"),

  /**
   * returns the angle in radians whose tangent is a specified float expression.
   * This is also called arctangent
   * 
   */
  @XmlEnumValue("atan")
  ATAN("atan"),

  /**
   * returns the smallest integer greater than, or equal to, the specified
   * numeric expression
   * 
   */
  @XmlEnumValue("ceiling")
  CEILING("ceiling"),

  /**
   * returns a mathematical function that returns the trigonometric cosine of
   * the specified angle, in radians, in the specified expression
   * 
   */
  @XmlEnumValue("cos")
  COS("cos"),

  /**
   * returns mathematical function that returns the trigonometric cotangent of
   * the specified angle, in radians, in the specified float expression
   * 
   */
  @XmlEnumValue("cot")
  COT("cot"),

  /**
   * returns the exponential value of the specified float expression
   * 
   */
  @XmlEnumValue("exp")
  EXP("exp"),

  /**
   * returns the largest integer less than or equal to the specified numeric
   * expression
   * 
   */
  @XmlEnumValue("floor")
  FLOOR("floor"),

  /**
   * returns the natural logarithm of the specified float expression
   * 
   */
  @XmlEnumValue("log")
  LOG("log"),

  /**
   * returns the base-10 logarithm of the specified float expression
   * 
   */
  @XmlEnumValue("log10")
  LOG_10("log10"),

  /**
   * returns the constant value of PI
   * 
   */
  @XmlEnumValue("pi")
  PI("pi"),

  /**
   * returns the value of the specified expression to the specified power
   * 
   */
  @XmlEnumValue("pow")
  POW("pow"),

  /**
   * returns a numeric value, rounded to the specified length or precision
   * 
   */
  @XmlEnumValue("round")
  ROUND("round"),

  /**
   * returns the trigonometric sine of the specified angle, in radians, and in
   * an approximate numeric, float, expression
   * 
   */
  @XmlEnumValue("sin")
  SIN("sin"),

  /**
   * returns the square root of the specified float value
   * 
   */
  @XmlEnumValue("sqrt")
  SQRT("sqrt"),

  /**
   * returns the square of the specified float value
   * 
   */
  @XmlEnumValue("square")
  SQUARE("square"),

  /**
   * returns the tangent of the input expression
   * 
   */
  @XmlEnumValue("tan")
  TAN("tan"),

  /**
   * returns the column precision
   * 
   */
  @XmlEnumValue("meta_col_precision")
  META_COL_PRECISION("meta_col_precision"),

  /**
   * returns the column scale
   * 
   */
  @XmlEnumValue("meta_col_scale")
  META_COL_SCALE("meta_col_scale"),

  /**
   * returns the column, table, schema or database name
   * 
   */
  @XmlEnumValue("meta_name")
  META_NAME("meta_name"),

  /**
   * returns the column, table, schema or database physical name alias
   * 
   */
  @XmlEnumValue("meta_physical_name")
  META_PHYSICAL_NAME("meta_physical_name"),

  /**
   * returns the column, table, schema or database business name alias
   * 
   */
  @XmlEnumValue("meta_business_name")
  META_BUSINESS_NAME("meta_business_name"),

  /**
   * returns the column, table, schema or database business name alias
   * 
   */
  @XmlEnumValue("meta_local_name")
  META_LOCAL_NAME("meta_local_name"),

  /**
   * returns the rank of each row within the partition of a result set
   * 
   */
  @XmlEnumValue("rank")
  RANK("rank"),

  /**
   * numbers the output of a result set. More specifically, returns the
   * sequential number of a row within a partition of a result set, starting at
   * 1 for the first row in each partition
   * 
   */
  @XmlEnumValue("row_number")
  ROW_NUMBER("row_number"),

  /**
   * returns the system-supplied value for the database user name of the current
   * user
   * 
   */
  @XmlEnumValue("user")
  USER("user"), @XmlEnumValue("substringBefore")
  SUBSTRING_BEFORE("substringBefore"), @XmlEnumValue("substringAfter")
  SUBSTRING_AFTER("substringAfter"), @XmlEnumValue("normalizeSpace")
  NORMALIZE_SPACE("normalizeSpace"), @XmlEnumValue("upperCase")
  UPPER_CASE("upperCase"), @XmlEnumValue("lowerCase")
  LOWER_CASE("lowerCase"), @XmlEnumValue("yearFromDate")
  YEAR_FROM_DATE("yearFromDate"), @XmlEnumValue("monthFromDate")
  MONTH_FROM_DATE("monthFromDate"), @XmlEnumValue("dayFromDate")
  DAY_FROM_DATE("dayFromDate"), @XmlEnumValue("timezoneFromDate")
  TIMEZONE_FROM_DATE("timezoneFromDate"),

  /**
   * returns search term word or character offsets into the given body of text
   * or document
   * 
   */
  @XmlEnumValue("offsets")
  OFFSETS("offsets"),

  /**
   * returns the search relevance value for a given document
   * 
   */
  @XmlEnumValue("relevance")
  RELEVANCE("relevance"),

  /**
   * returns the final composite score for a given document or body of text
   * 
   */
  @XmlEnumValue("score")
  SCORE("score"),

  /**
   * returns the current search terms for a given search
   * 
   */
  @XmlEnumValue("terms")
  TERMS("terms"),

  /**
   * returns the current ngrams for a given search
   * 
   */
  @XmlEnumValue("ngrams")
  NGRAMS("ngrams"),

  /**
   * returns the current query term proximity weight for a given search
   * 
   */
  @XmlEnumValue("proximity")
  PROXIMITY("proximity"),

  /**
   * returns the vector space weight for a given search
   * 
   */
  @XmlEnumValue("weight")
  WEIGHT("weight");

  private final String value;

  FunctionName(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static FunctionName fromValue(String v) {
    for (FunctionName c : FunctionName.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
