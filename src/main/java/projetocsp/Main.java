package projetocsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Scanner;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspHeuristics;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import projetocsp.csp.AlgorithmCtrl;
import projetocsp.entities.Person;
import projetocsp.entities.Schedule;
import projetocsp.entities.TimeSlot;
import projetocsp.utils.ManageResults;
import projetocsp.utils.Timer;

public class Main {
	
	public static void main(String[] args) {
	    // =====[ Restrigindo horarios ]=======================
//		Scanner sc = new Scanner(System.in);
//	    System.out.printf("Informe horario de inicio:\n");
//	    int open = sc.nextInt();
//	    System.out.printf("Informe horario de fechamento:\n");
//	    int close = sc.nextInt();
	    
	    // =====[ Criando um caso ]=======================
	
	    Person alice = new Person("Alice", 2);
	    Person bob = new Person("Bob", 3);
	    Person charlie = new Person("Charlie", 2);
	    Person david = new Person("David", 2);
	    Person eve = new Person("Eve", 4);
	
	   // Valor necessario para atribuir em horarios sem funcionarios
	    Person empty = new Person("Empty", 24);
	    
//	    Person empty = new Person("Empty", close);
	    
	    List<Person> members = new ArrayList<>(
	      Arrays.asList(alice, bob, charlie, david, eve, empty)
	    );
	    
	    List<TimeSlot> timeSlots = new ArrayList<>();
	    for (int i = 1; i <= 24; i++) {
	      timeSlots.add(new TimeSlot(i));
	    }
	
	    // =====[ Setting preferences ]================
	    
	    int[] alicePreferences = { 5, 6, 8, 10, 11, 13, 17, 18, 19, 20 };
	    List<TimeSlot> aliceTimeslots = new ArrayList<>();
	    for (int i : alicePreferences) {
	      aliceTimeslots.add(timeSlots.get(i));
	    }
	    alice.setPreferences(aliceTimeslots);

	    int[] bobPreferences = { 1, 3, 4, 6, 8, 9, 10, 13, 16, 17, 22 };
	    List<TimeSlot> bobTimeslots = new ArrayList<>();
	    for (int i : bobPreferences) {
	      bobTimeslots.add(timeSlots.get(i));
	    }
	    bob.setPreferences(bobTimeslots);

	    int[] charliePreferences = { 2, 5, 9, 11, 12, 14, 20, 21, 22, 23 };
	    List<TimeSlot> charlieTimeslots = new ArrayList<>();
	    for (int i : charliePreferences) {
	      charlieTimeslots.add(timeSlots.get(i));
	    }
	    charlie.setPreferences(charlieTimeslots);
   
	    int[] davidPreferences = { 1, 3, 4, 7, 13, 14, 15, 16, 19, 21, 23 };
	    List<TimeSlot> davidTimeslots = new ArrayList<>();
	    for (int i : davidPreferences) {
	      davidTimeslots.add(timeSlots.get(i));
	    }
	    david.setPreferences(davidTimeslots);
	
		int[] evePreferences = { 1, 2, 3, 4, 8, 9, 11, 13, 13, 14, 18, 22 };
	    List<TimeSlot> eveTimeslots = new ArrayList<>();
	    for (int i : evePreferences) {
	      eveTimeslots.add(timeSlots.get(i));
	    }
	    eve.setPreferences(eveTimeslots);

		// =====[ Contraints ]================
	    
	    List<String> constraintNames = new ArrayList<>();

		Scanner sc = new Scanner(System.in);
	    System.out.printf("Escolha as restrições:\n[1] MaxWorkingHours\n[2] MaxWorkingHours + AssignAllPeople\n[3] MaxWorkingHours + PreferredSchedule + AssignAllPeople\n");
	    int contraints_choice = sc.nextInt();
		// sc.close();
	    
		switch(contraints_choice) {
			case 1:
				constraintNames.add("MaxWorkingHours");
				break;
			case 2:
			    constraintNames.add("MaxWorkingHours");
			    constraintNames.add("AssignAllPeople");
				break;
			case 3:
				constraintNames.add("MaxWorkingHours");
				constraintNames.add("PreferredSchedule");
				constraintNames.add("AssignAllPeople");
				break;
			default:
				constraintNames.add("MaxWorkingHours");
				break;
		}
		
//	    constraintNames.add("MaxWorkingHours");
//	    constraintNames.add("PreferredSchedule");
//	    constraintNames.add("AssignAllPeople");

// 		=====[ Escolhendo o algoritmo ]================
//	    String algorithm = "MinConflictsSolver";
//  	String algorithm = "Backtracking + MRV & DEG + LCV + AC3";
//	    String algorithm = "Backtracking + MRV & DEG";
//	    String algorithm = "Backtracking";

		// Scanner sc_switch = new Scanner(System.in);
	    System.out.printf("Escolha um algoritmo:\n[1] Min Conflicts\n[2] Backtracking + MRV & DEG + LCV + AC3\n[3] Backtracking + MRV & DEG\n[4] Backtracking\n");
	    int algorithm_choice = sc.nextInt();
		sc.close();

	    String algorithm;
	    
		switch(algorithm_choice) {
			case 1:
				algorithm = "MinConflictsSolver";
				break;
			case 2:
				algorithm = "Backtracking + MRV & DEG + LCV + AC3";
				break;
			case 3:
				algorithm = "Backtracking + MRV & DEG";
				break;
			case 4:
				algorithm = "Backtracking";
				break;
			default:
				algorithm = "HashMap";
		}

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
	    }
	}
}