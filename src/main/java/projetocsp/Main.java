package projetocsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspListener;
import projetocsp.csp.AlgorithmCtrl;
import projetocsp.entities.Person;
import projetocsp.entities.Schedule;
import projetocsp.entities.TimeSlot;
import projetocsp.utils.ManageResults;
import projetocsp.utils.Timer;

public class Main {
	
	public static void main(String[] args) {

    // =====[ Criando um caso ]=======================

    Person alice = new Person("Alice", 2);
    Person bob = new Person("Bob", 5);
    Person charlie = new Person("Charlie", 2);
    Person david = new Person("David", 2);
    Person eve = new Person("Eve", 2);

    //Valor necessario para atribuir em horarios sem funcionarios
    Person empty = new Person("Empty", 24);

    List<Person> members = new ArrayList<>(
      Arrays.asList(alice, bob, charlie, david, eve, empty)
    );

    List<TimeSlot> timeSlots = new ArrayList<>();
    for (int i = 1; i <= 24; i++) {
      timeSlots.add(new TimeSlot(i));
    }

    bob.setPreferences(new ArrayList<>(
      Arrays.asList(
        timeSlots.get(0),
        timeSlots.get(1),
        timeSlots.get(2),
        timeSlots.get(3),
        timeSlots.get(4),
        timeSlots.get(5),
        timeSlots.get(6),
        timeSlots.get(7),
        timeSlots.get(8),
        timeSlots.get(9),
        timeSlots.get(10),
        timeSlots.get(11),
        timeSlots.get(12)
      )
    ));

    List<String> constraintNames = new ArrayList<>();
    constraintNames.add("MaxWorkingHours");
    constraintNames.add("PreferredSchedule");
    constraintNames.add("AssignAllPeople");


    // =====[ Escolhendo o algoritmo ]================

    String algorithm = "MinConflictsSolver";
    // String algorithm = "Backtracking + MRV & DEG + LCV + AC3";
    // String algorithm = "Backtracking + MRV & DEG";
    // String algorithm = "Backtracking";


		// =====[ Execucao principal ]====================

    CspListener.StepCounter<TimeSlot, Person> stepCounter = new CspListener.StepCounter<>();
    AlgorithmCtrl algorithmCtrl = new AlgorithmCtrl(members, timeSlots, constraintNames);
    
    System.out.println("\nAlocar funcionários ("+algorithm+")");
    Timer timer = new Timer();
    Set<Optional<Assignment<TimeSlot, Person>>> solutions = algorithmCtrl.useAlgorithm(algorithm, stepCounter);
    String tempo = timer.toString();
    long numResultados = solutions.size();


    // =====[ Exibindo os resultados ]================

    System.out.println("Tempo decorrido = "+ tempo);
    System.out.println("Solucoes obtidas = "+ numResultados);

    if (stepCounter.getResults().get("assignmentCount") != null)
      System.out.println("Atribuicoes = "+stepCounter.getResults().get("assignmentCount"));
  
    if (stepCounter.getResults().get("inferenceCount") != null)
      System.out.println("Inferencias = "+stepCounter.getResults().get("inferenceCount"));

    System.out.println();

    ManageResults mr = new ManageResults(solutions, timeSlots, members);
    List<Schedule> schedules = mr.getSchedules();

    if (numResultados > 0) {
      System.out.println(mr.getResult(schedules.get(0)));
      // System.out.println(mr.getResult(schedules.get(1)));
    }
	}
}