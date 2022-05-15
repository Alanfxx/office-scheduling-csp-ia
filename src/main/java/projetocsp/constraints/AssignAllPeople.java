package projetocsp.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

/**
 * Representa uma restrição unária que obriga a alocacao de todos os funcionarios
 */
public class AssignAllPeople implements Constraint<TimeSlot, Person> {

  // private VAR var;
  private List<Person> values;
	private List<TimeSlot> scope;

	public AssignAllPeople(TimeSlot var, List<Person> values) {
		// this.var = var;
		this.values = values;
		scope = new ArrayList<>(1);
		scope.add(var);
	}

  @Override
  public List<TimeSlot> getScope() {
    return scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment<TimeSlot, Person> assignment) {
    Collection<Person> assignedValues = assignment.getVariableToValueMap().values();

    for (Person p : values) {
      if (p.getName() != "Empty" && !assignedValues.contains(p))
        return false;
    }
    return true;
  }
}
