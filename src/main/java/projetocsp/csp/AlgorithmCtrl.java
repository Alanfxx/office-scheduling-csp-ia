package projetocsp.csp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspHeuristics;
import aima.core.search.csp.CspListener.StepCounter;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.Domain;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import projetocsp.entities.Person;
import projetocsp.entities.PersonSchedule;
import projetocsp.entities.TimeSlot;

public class AlgorithmCtrl {

	private List<String> constraintNames;
	private List<Person> members;
  private Domain<PersonSchedule> domain;

	public AlgorithmCtrl(
    List<Person> members,
    List<TimeSlot> timeSlots,
    List<String> constraintNames
  ) {
    this.members = members;
		this.constraintNames = constraintNames;

    this.domain = createDomain(timeSlots);
	}

	private Domain<PersonSchedule> createDomain(List<TimeSlot> timeSlots) {
    System.out.println("Criando um dominio..");
    List<PersonSchedule> initDomain = new ArrayList<>();
    List<Integer> hours = timeSlotsToInt(timeSlots);

    createCombinations(initDomain, new ArrayList<>(), hours);
    System.out.println("Tamanho do dominio: "+initDomain.size());

    return new Domain<PersonSchedule>(initDomain);
  }

  private void createCombinations(
    List<PersonSchedule> initDomain,
    List<Integer> result,
    List<Integer> hours
  ) {
    if (hours.isEmpty()) {
      if (!result.isEmpty()) {
        initDomain.add(new PersonSchedule(result));
      } 
    } else {
      List<Integer> newResult = new ArrayList<>(result);
      newResult.add(hours.get(0));
      List<Integer> newHours = new ArrayList<>(hours);
      newHours.remove(hours.get(0));

      createCombinations(initDomain, newResult, newHours);

      List<Integer> newResult2 = new ArrayList<>(result);
      createCombinations(initDomain, newResult2, newHours);
    }
  }

  private List<Integer> timeSlotsToInt(List<TimeSlot> timeSlots) {
    List<Integer> list = new ArrayList<>();
    for (TimeSlot tl : timeSlots) {
      list.add(tl.getHour());
    }
    return list;
  }

  public Set<Optional<Assignment<Person, PersonSchedule>>> useAlgorithm(
    String algorit,
		StepCounter<Person, PersonSchedule> stepCounter
  ) {
		CspSolver<Person, PersonSchedule> solver;
		switch(algorit) {
			case "MinConflictsSolver":
				solver = new MinConflictsSolver<>(100);
				return getSolutions(solver, stepCounter);
			case "Backtracking + MRV & DEG + LCV + AC3":
				solver = new FlexibleBacktrackingSolver<Person, PersonSchedule>().setAll();
				return getSolutions(solver, stepCounter);
			case "Backtracking + MRV & DEG":
				solver = new FlexibleBacktrackingSolver<Person, PersonSchedule>().set(CspHeuristics.mrvDeg());
				return getSolutions(solver, stepCounter);
			case "Backtracking":
				solver = new FlexibleBacktrackingSolver<>();
				return getSolutions(solver, stepCounter);
			default:
				return new HashSet<>();
		}
	}

	private Set<Optional<Assignment<Person, PersonSchedule>>> getSolutions(
    CspSolver<Person, PersonSchedule> solver,
    StepCounter<Person, PersonSchedule> stepCounter
  ) {
    solver.addCspListener(stepCounter);
		stepCounter.reset();
		Optional<Assignment<Person, PersonSchedule>> solution;
		Set<Optional<Assignment<Person, PersonSchedule>>> set = new HashSet<>();

		// for (Person var : members) {
		// 	for (PersonSchedule val : domain) {
				CSP<Person, PersonSchedule> csp = new OfficeSchedulingCSP(
          members, domain, constraintNames
          // new AssignedValue<>(var, val)
        );
				solution = solver.solve(csp);
				if(!solution.isEmpty())
					set.add(solution);
		// 	}
		// }
		return set;
	}
}
