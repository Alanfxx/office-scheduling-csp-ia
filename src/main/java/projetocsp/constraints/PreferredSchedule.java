package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import projetocsp.entities.Person;

/**
 * Representa uma restrição unária que proíbe um funcionário de estar
 * no escritório num bloco de tempo que nao esteja em sua lista de preferidos.
 */
public class PreferredSchedule<VAR extends Variable, VAL> implements Constraint<VAR, Person> {

  private VAR var;
	private List<VAR> scope;

  public PreferredSchedule(VAR var) {
    this.var = var;
    scope = new ArrayList<>(1);
		scope.add(var);
  }

  @Override
  public List<VAR> getScope() {
    return scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment<VAR, Person> assignment) {
    Person value = assignment.getValue(var);
    return value.free(var);
  }
}
