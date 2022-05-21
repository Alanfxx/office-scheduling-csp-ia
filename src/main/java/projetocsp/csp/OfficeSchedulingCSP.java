package projetocsp.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import projetocsp.constraints.*;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

public class OfficeSchedulingCSP extends CSP<TimeSlot, Person> {

	public OfficeSchedulingCSP(
    List<Person> members,
    List<TimeSlot> timeSlots,
    List<String> contraintNames,
    Constraint<TimeSlot, Person> dynamicConstraint
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
		addConstraint(dynamicConstraint);
	}

  private void addConstraints(List<String> contraintNames) {
    if (contraintNames.contains("MaxWorkingHours")) {
      for (TimeSlot tl : getVariables()) {
        addConstraint(new MaxWorkingHours(tl));
      }
    }

    if (contraintNames.contains("PreferredSchedule")) {
      for (TimeSlot tl : getVariables()) {
        addConstraint(new PreferredSchedule(tl));
      }
    }

    if (contraintNames.contains("AssignAllPeople")) {
      List<Person> members = getDomain(getVariables().get(0)).asList();
      for (TimeSlot tl : getVariables()) {
        addConstraint(new AssignAllPeople(tl, members));
      }
    }
  }
}
