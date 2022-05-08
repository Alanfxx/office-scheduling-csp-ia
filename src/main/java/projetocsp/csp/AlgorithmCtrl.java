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
import aima.core.search.csp.Domain;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import aima.core.search.csp.Variable;
import projetocsp.constraints.AssignedValue;

public class AlgorithmCtrl {

	private List<String> constraintNames;
	private List<Variable> members;
  private Domain<List<Integer>> domain;

	public AlgorithmCtrl(
    List<Variable> members,
    List<Integer> timeSlots,
    List<String> constraintNames
  ) {
    this.members = members;
		this.constraintNames = constraintNames;

    this.domain = createDomain(timeSlots);
	}

	private Domain<List<Integer>> createDomain(List<Integer> timeSlots) {
    //TODO
    return null;
  }

  public Set<Optional<Assignment<Variable, List<Integer>>>> useAlgorithm(
    String algorit,
		StepCounter<Variable, List<Integer>> stepCounter
  ) {
		CspSolver<Variable, List<Integer>> solver;
		switch(algorit) {
			case "MinConflictsSolver":
				solver = new MinConflictsSolver<>(500);
				solver.addCspListener(stepCounter);
				stepCounter.reset();
				return getSolutions(solver);
			case "Backtracking + MRV & DEG + LCV + AC3":
				solver = new FlexibleBacktrackingSolver<Variable, List<Integer>>().setAll();
				solver.addCspListener(stepCounter);
				stepCounter.reset();
				return getSolutions(solver);
			case "Backtracking + MRV & DEG":
				solver = new FlexibleBacktrackingSolver<Variable, List<Integer>>().set(CspHeuristics.mrvDeg());
				solver.addCspListener(stepCounter);
				stepCounter.reset();
				return getSolutions(solver);
			case "Backtracking":
				solver = new FlexibleBacktrackingSolver<>();
				solver.addCspListener(stepCounter);
				stepCounter.reset();
				return getSolutions(solver);
			default:
				return new HashSet<>();
		}
	}

	private Set<Optional<Assignment<Variable, List<Integer>>>> getSolutions(
    CspSolver<Variable, List<Integer>> solver
  ) {
		Optional<Assignment<Variable, List<Integer>>> solution;
		Set<Optional<Assignment<Variable, List<Integer>>>> set = new HashSet<>();

		for (Variable var : members) {
			for (List<Integer> val : domain) {
				CSP<Variable, List<Integer>> csp = new OfficeSchedulingCSP(
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
