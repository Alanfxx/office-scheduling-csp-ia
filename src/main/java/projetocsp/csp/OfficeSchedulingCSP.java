package projetocsp.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import projetocsp.constraints.MaxWorkingHours;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

public class OfficeSchedulingCSP extends CSP<TimeSlot, Person> {

	public OfficeSchedulingCSP(
    List<Person> members,
    List<TimeSlot> timeSlots,
    List<String> contraintNames
    // Constraint<TimeSlot, Person> dynamicConstraint
  ) {
    super(timeSlots);

    for (TimeSlot var : getVariables()) {
      setDomain(var, new Domain<>(members));
    }

    addConstraints(contraintNames);

    /*
		 * Restricao para atribuir valores a cada variavel
		 * dinamicamente e gerar os melhores resultados
		 */
		// addConstraint(dynamicConstraint);
	}

  private void addConstraints(List<String> contraintNames) {
    if (contraintNames.contains("MaxWorkingHours")) {
      for (TimeSlot tl : getVariables()) {
        addConstraint(new MaxWorkingHours<>(tl));
      }
    }
    // if(contraintNames.contains("xxxxxx")) {
    // }
  }

  // private void addMaxMembersAtATime(int j) {
  //   List<TimeSlot> vars = getVariables();
	// 	for (int i = j+1; i < vars.size(); i++) {
	// 		addConstraint(new MaxMembersAtATime<>(vars.get(j), vars.get(i)));
	// 	}
	// 	if (j+1 < vars.size()) addMaxMembersAtATime(j+1);
	// }
}
