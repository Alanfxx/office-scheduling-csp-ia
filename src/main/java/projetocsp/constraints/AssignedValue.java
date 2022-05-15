package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

/**
 * Representa a restriçao unária que fixa um funcionário num bloco de tempo
 */
public class AssignedValue implements Constraint<TimeSlot, Person> {

	private TimeSlot var;
	private Person val;
	private List<TimeSlot> scope;

	public AssignedValue(TimeSlot var, Person val) {
		this.var = var;
		this.val = val;
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
		return value.equals(val);
	}
}