package projetocsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.Variable;
import projetocsp.csp.AlgorithmCtrl;
import projetocsp.entities.Person;
import projetocsp.entities.TimeSlot;
import projetocsp.utils.Timer;

public class Main {
	
	public static void main(String[] args) {

    Person alice = new Person("Alice");
    Person bob = new Person("Bob");
    Person charlie = new Person("Charlie");
    Person david = new Person("David");
    Person eve = new Person("Eve");
    
    List<Person> members = new ArrayList<>(
      Arrays.asList(alice, bob, charlie, david, eve)
      );
      
    List<TimeSlot> timeSlots = new ArrayList<>();
    for (int i = 1; i <= 24; i++) {
      timeSlots.add(new TimeSlot(i));
    }
    
    List<String> constraintNames = new ArrayList<>();
    constraintNames.add("MaxMembersAtATime");

    // Escolhendo o algoritmo =========================
    // "MinConflictsSolver"
    // "Backtracking + MRV & DEG + LCV + AC3"
    // "Backtracking + MRV & DEG"
    // "Backtracking"
    String algorithm = "MinConflictsSolver";

		//Execucao principal ==============================
    CspListener.StepCounter<Variable, TimeSlot> stepCounter = new CspListener.StepCounter<>();
    AlgorithmCtrl algorithmCtrl = new AlgorithmCtrl(members, timeSlots, constraintNames);
    
    System.out.println("Alocar funcionários ("+algorithm+")");
    Timer timer = new Timer();
    Set<Optional<Assignment<Variable, TimeSlot>>> solutions = algorithmCtrl.useAlgorithm(algorithm, stepCounter);
    String tempo = timer.toString();
    long numResultados = solutions.size();

    // Exibindo os resultados =========================
    System.out.println("Tempo decorrido = "+ tempo);
    System.out.println("Solucoes obtidas = "+ numResultados);
    System.out.println(stepCounter.getResults() + "\n");

	}
}