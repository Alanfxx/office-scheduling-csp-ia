package projetocsp.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import projetocsp.constraints.MaxMembersAtATime;
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

    /*
		 * Restricao para atribuir valores a cada variavel
		 * dinamicamente e gerar os melhores resultados
		 */
		addConstraint(dynamicConstraint); 
	}

  private void addConstraints(List<String> contraintNames) {
    if(contraintNames.contains("MaxMembersAtATime")) {
      addMaxMembersAtATime(getVariables(), 0);
    }
    // if(contraintNames.contains("xxxxxx")) {
    // }
  }

  private void addMaxMembersAtATime(List<Person> vars, int j) {
		for(int i = j+1; i < getVariables().size(); i++){
			addConstraint(new MaxMembersAtATime<>(vars.get(j), vars.get(i)));
		}
		if(j+1 < vars.size()) addMaxMembersAtATime(vars, j+1);
	}
}
