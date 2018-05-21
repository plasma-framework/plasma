package org.plasma.query.collector;

import org.plasma.query.model.Function;
import org.plasma.query.model.FunctionName;
import org.plasma.query.model.Path;
import org.plasma.sdo.DataType;

import commonj.sdo.Property;

public class FunctionPath {
  private Function func;
  private Path path;
  private Property property;

  @SuppressWarnings("unused")
  private FunctionPath() {
  }

  public FunctionPath(Function func, Path path, Property property) {
    super();
    this.func = func;
    this.path = path;
    this.property = property;
  }

  public Function getFunc() {
    return func;
  }

  public Path getPath() {
    return path;
  }

  public Property getProperty() {
    return property;
  }

  public DataType getDataType() {
    return DataType.valueOf(this.property.getType().getName());
  }

}
