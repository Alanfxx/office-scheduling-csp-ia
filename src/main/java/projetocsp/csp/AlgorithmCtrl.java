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
import projetocsp.constraints.AssignedValue;
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
    List<PersonSchedule> initDomain = new ArrayList<>();
    for (TimeSlot timeSlot : timeSlots) {
      for (Person person : members) {
        // TimeSlot newTimeSlot = timeSlot.clone();
        // newTimeSlot.setPerson(person);
        // initDomain.add(newTimeSlot);
      }
    }

    return new Domain<PersonSchedule>(initDomain);
  }

  public Set<Optional<Assignment<Person, PersonSchedule>>> useAlgorithm(
    String algorit,
		StepCounter<Person, PersonSchedule> stepCounter
  ) {
		CspSolver<Person, PersonSchedule> solver;
		switch(algorit) {
			case "MinConflictsSolver":
				solver = new MinConflictsSolver<>(500);
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

		for (Person var : members) {
			for (PersonSchedule val : domain) {
				CSP<Person, PersonSchedule> csp = new OfficeSchedulingCSP(
          members, domain, constraintNames, new AssignedValue<>(var, val)
        );
				solution = solver.solve(csp);
				if(!solution.isEmpty())
					set.add(solution);
			}
		}
		return set;
	}
}
