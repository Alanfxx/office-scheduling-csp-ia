package projetocsp.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

public class OfficeSchedulingCSP extends CSP<Person, TimeSlot> {

	public OfficeSchedulingCSP(
    List<Person> members,
    Domain<TimeSlot> domain,
    List<String> contraintNames,
    Constraint<Person, TimeSlot> dynamicConstraint
  ) {
    super(members);

    for (Person var : getVariables()) {
      setDomain(var, domain);
    }

    addConstraints(contraintNames);
	}

  private void addConstraints(List<String> contraintNames) {
    if(contraintNames.contains("MaxMembersAtATime")) {
      addMaxMembersAtATime();
    }
    // if(contraintNames.contains("xxxxxx")) {
    // }
  }

  private void addMaxMembersAtATime() {
  }
}
