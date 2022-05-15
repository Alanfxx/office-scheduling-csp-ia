package projetocsp.csp;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspHeuristics;
import aima.core.search.csp.CspListener.StepCounter;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import projetocsp.constraints.AssignedValue;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;

public class AlgorithmCtrl {

	private List<String> constraintNames;
	private List<TimeSlot> timeSlots;
  private List<Person> members;

	public AlgorithmCtrl(
    List<Person> members,
    List<TimeSlot> timeSlots,
    List<String> constraintNames
  ) {
    this.timeSlots = timeSlots;
		this.constraintNames = constraintNames;
    this.members = members;
	}

  public Set<Optional<Assignment<TimeSlot, Person>>> useAlgorithm(
    String algorit,
		StepCounter<TimeSlot, Person> stepCounter
  ) {
		CspSolver<TimeSlot, Person> solver;
		switch(algorit) {
			case "MinConflictsSolver":
				solver = new MinConflictsSolver<>(500);
				return getSolutions(solver, stepCounter);
			case "Backtracking + MRV & DEG + LCV + AC3":
				solver = new FlexibleBacktrackingSolver<TimeSlot, Person>().setAll();
				return getSolutions(solver, stepCounter);
			case "Backtracking + MRV & DEG":
				solver = new FlexibleBacktrackingSolver<TimeSlot, Person>().set(CspHeuristics.mrvDeg());
				return getSolutions(solver, stepCounter);
			case "Backtracking":
				solver = new FlexibleBacktrackingSolver<>();
				return getSolutions(solver, stepCounter);
			default:
				return new HashSet<>();
		}
	}

	private Set<Optional<Assignment<TimeSlot, Person>>> getSolutions(
    CspSolver<TimeSlot, Person> solver,
    StepCounter<TimeSlot, Person> stepCounter
  ) {
    solver.addCspListener(stepCounter);
		stepCounter.reset();
		Optional<Assignment<TimeSlot, Person>> solution;
		Set<Optional<Assignment<TimeSlot, Person>>> set = new HashSet<>();

		// for (TimeSlot var : timeSlots) {
			// for (Person val : members) {
				CSP<TimeSlot, Person> csp = new OfficeSchedulingCSP(
          members, timeSlots, constraintNames
          // new AssignedValue<>(var, val)
        );
				solution = solver.solve(csp);
				if(!solution.isEmpty())
					set.add(solution);
			// }
		// }
		return set;
	}
}
