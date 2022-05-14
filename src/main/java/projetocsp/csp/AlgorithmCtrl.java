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
import projetocsp.entities.TimeSlot;

public class AlgorithmCtrl {

	private List<String> constraintNames;
	private List<Person> members;
  private Domain<TimeSlot> domain;

	public AlgorithmCtrl(
    List<Person> members,
    List<TimeSlot> timeSlots,
    List<String> constraintNames
  ) {
    this.members = members;
		this.constraintNames = constraintNames;

    this.domain = createDomain(timeSlots);
	}

	private Domain<TimeSlot> createDomain(List<TimeSlot> timeSlots) {
    List<TimeSlot> initDomain = new ArrayList<>();
    for (TimeSlot timeSlot : timeSlots) {
      for (Person person : members) {
        TimeSlot newTimeSlot = new TimeSlot(timeSlot.getHour());
        newTimeSlot.setPerson(person);
        initDomain.add(newTimeSlot);
      }
    }

    return new Domain<>(initDomain);
  }

  public Set<Optional<Assignment<Person, TimeSlot>>> useAlgorithm(
    String algorit,
		StepCounter<Person, TimeSlot> stepCounter
  ) {
		CspSolver<Person, TimeSlot> solver;
		switch(algorit) {
			case "MinConflictsSolver":
				solver = new MinConflictsSolver<>(500);
				return getSolutions(solver, stepCounter);
			case "Backtracking + MRV & DEG + LCV + AC3":
				solver = new FlexibleBacktrackingSolver<Person, TimeSlot>().setAll();
				return getSolutions(solver, stepCounter);
			case "Backtracking + MRV & DEG":
				solver = new FlexibleBacktrackingSolver<Person, TimeSlot>().set(CspHeuristics.mrvDeg());
				return getSolutions(solver, stepCounter);
			case "Backtracking":
				solver = new FlexibleBacktrackingSolver<>();
				return getSolutions(solver, stepCounter);
			default:
				return new HashSet<>();
		}
	}

	private Set<Optional<Assignment<Person, TimeSlot>>> getSolutions(
    CspSolver<Person, TimeSlot> solver,
    StepCounter<Person, TimeSlot> stepCounter
  ) {
    solver.addCspListener(stepCounter);
		stepCounter.reset();
		Optional<Assignment<Person, TimeSlot>> solution;
		Set<Optional<Assignment<Person, TimeSlot>>> set = new HashSet<>();

		for (Person var : members) {
			for (TimeSlot val : domain) {
				CSP<Person, TimeSlot> csp = new OfficeSchedulingCSP(
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
