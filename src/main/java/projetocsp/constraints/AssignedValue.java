package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import projetocsp.entities.TimeSlot;

/**
 * Representa a restriçao unária que fixa um funcionário num bloco de tempo
 */
public class AssignedValue<VAR extends Variable, VAL> implements Constraint<VAR, TimeSlot> {

	private VAR var;
	private TimeSlot val;
	private List<VAR> scope;

	public AssignedValue(VAR var, TimeSlot val) {
		this.var = var;
		this.val = val;
		scope = new ArrayList<>(1);
		scope.add(var);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, TimeSlot> assignment) {
		TimeSlot value = assignment.getValue(var);
		return value.equals(val);
	}
}