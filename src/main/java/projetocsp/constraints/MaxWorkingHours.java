package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import projetocsp.entities.Person;

/**
 * Representa uma restrição unária que fixa a quantidade de horas que um
 * funcionário deve estar no escritorio de acordo com sua carga horaria
 */
public class MaxWorkingHours<VAR extends Variable, VAL> implements Constraint<VAR, Person> {

  private VAR var;
	private List<VAR> scope;

	public MaxWorkingHours(VAR var) {
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
		if (value == null) return true;
    if (value.getName() == "Empty") return true;
    int count = 0;
    for (Person p : assignment.getVariableToValueMap().values()) {
      if (value.equals(p)) count++;
    }

		return value.getWorkingHours() == count;
  }
}
