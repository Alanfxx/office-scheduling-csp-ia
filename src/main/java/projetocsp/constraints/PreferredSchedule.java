package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

/**
 * Representa uma restrição unária que proíbe um funcionário de estar
 * no escritório num bloco de tempo que nao esteja em sua lista de preferidos.
 */
public class PreferredSchedule implements Constraint<TimeSlot, Person> {

  private TimeSlot var;
	private List<TimeSlot> scope;

  public PreferredSchedule(TimeSlot var) {
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
    boolean check = value.free(var);
    return check;
  }
}
