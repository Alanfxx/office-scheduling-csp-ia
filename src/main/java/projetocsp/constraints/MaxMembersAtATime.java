package projetocsp.constraints;

import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

/**
 * Representa uma restrição binária que proíbe duas pessoas de estar
 * no escritório no mesmo bloco de tempo.
 */
public class MaxMembersAtATime<VAR extends Variable, VAL> implements Constraint<VAR, List<String>> {

  @Override
  public List<VAR> getScope() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isSatisfiedWith(Assignment<VAR, List<String>> assignment) {
    // TODO Auto-generated method stub
    return false;
  }
}
