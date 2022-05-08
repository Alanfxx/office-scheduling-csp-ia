package projetocsp.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import projetocsp.entities.TimeSlot;

public class OfficeSchedulingCSP extends CSP<Variable, TimeSlot> {

	public OfficeSchedulingCSP(
    List<Variable> members,
    Domain<TimeSlot> domain,
    List<String> contraintNames,
    Constraint<Variable, TimeSlot> dynamicConstraint
  ) {
    super();
    //TODO - iniciar props do CSP
	}
}
