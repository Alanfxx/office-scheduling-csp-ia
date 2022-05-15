package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

/**
 * Representa uma restrição unária que fixa a quantidade de horas que um
 * funcionário deve estar no escritorio de acordo com sua carga horaria
 */
public class MaxWorkingHours implements Constraint<TimeSlot, Person> {

  private TimeSlot var;
	private List<TimeSlot> scope;

	public MaxWorkingHours(TimeSlot var) {
		this.var = var;
		scope = new ArrayList<>(1);
		scope.add(var);
	}

  @Override
  public List<TimeSlot> getScope() {
    return scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment<TimeSlot, Person> assignment) {
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
