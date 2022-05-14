package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import projetocsp.entities.PersonSchedule;

/**
 * Representa uma restrição binária que proíbe duas pessoas de estar
 * no escritório no mesmo bloco de tempo.
 */
public class MaxMembersAtATime<VAR extends Variable, VAL> implements Constraint<VAR, PersonSchedule> {

  private VAR var1;
  private VAR var2;
	private List<VAR> scope;

	public MaxMembersAtATime(VAR var1, VAR var2) {
		this.var1 = var1;
		this.var2 = var2;
		scope = new ArrayList<>(2);
		scope.add(var1);
		scope.add(var2);
	}

  @Override
  public List<VAR> getScope() {
    return scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment<VAR, PersonSchedule> assignment) {
    PersonSchedule value1 = assignment.getValue(var1);
		PersonSchedule value2 = assignment.getValue(var2);

		if(value1 == null || value2 == null) return true;

    boolean check = true;
    for (Integer hour : value1.getSchedule()) {
      if (value2.getSchedule().contains(hour)) check = false;
    }

		return check;
  }
}
