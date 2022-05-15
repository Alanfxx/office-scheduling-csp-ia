package projetocsp.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import projetocsp.entities.Person;

/**
 * Representa a restriçao unária que fixa um funcionário num bloco de tempo
 */
public class AssignedValue<VAR extends Variable, VAL> implements Constraint<VAR, Person> {

	private VAR var;
	private Person val;
	private List<VAR> scope;

	public AssignedValue(VAR var, Person val) {
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
	public boolean isSatisfiedWith(Assignment<VAR, Person> assignment) {
		Person value = assignment.getValue(var);
		return value.equals(val);
	}
}