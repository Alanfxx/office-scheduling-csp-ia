package projetocsp.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;

public class OfficeSchedulingCSP extends CSP<Variable, List<Integer>> {

	public OfficeSchedulingCSP(
    List<Variable> members,
    Domain<List<Integer>> domain,
    List<String> contraintNames,
    Constraint<Variable, List<Integer>> dynamicConstraint
  ) {
    super();
    //TODO - iniciar props do CSP
	}
}
