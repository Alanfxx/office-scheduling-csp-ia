package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

/**
 * 
 */
public class AssignedValue<VAR extends Variable, VAL> implements Constraint<VAR, List<Integer>> {

	private VAR var;
	private List<Integer> val;
	private List<VAR> scope;

	public AssignedValue(VAR var, List<Integer> val) {
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
	public boolean isSatisfiedWith(Assignment<VAR, List<Integer>> assignment) {
		List<Integer> value = assignment.getValue(var);
		return value.equals(val);
	}
}