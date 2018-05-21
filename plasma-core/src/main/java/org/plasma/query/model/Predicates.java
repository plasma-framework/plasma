package org.plasma.query.model;

import java.util.List;

import org.plasma.query.visitor.QueryVisitor;

public interface Predicates {
  public List<Expression> getExpressions();

  public void accept(QueryVisitor visitor);
}
