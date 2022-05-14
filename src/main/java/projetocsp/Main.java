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
import projetocsp.entities.PersonSchedule;
import projetocsp.entities.Schedule;
import projetocsp.entities.TimeSlot;
import projetocsp.utils.ManageResults;
import projetocsp.utils.Timer;

public class Main {
	
	public static void main(String[] args) {

    // =====[ Criando um caso ]=======================

    Person alice = new Person("Alice", 2);
    Person bob = new Person("Bob", 2);
    Person charlie = new Person("Charlie", 2);
    Person david = new Person("David", 2);
    Person eve = new Person("Eve", 2);
    
    List<Person> members = new ArrayList<>(
      Arrays.asList(alice, bob, charlie, david, eve)
      );
      
    List<TimeSlot> timeSlots = new ArrayList<>();
    for (int i = 1; i <= 24; i++) {
      timeSlots.add(new TimeSlot(i));
    }
    
    List<String> constraintNames = new ArrayList<>();
    constraintNames.add("MaxMembersAtATime");


    // =====[ Escolhendo o algoritmo ]================

    String algorithm = "MinConflictsSolver";
    // String algorithm = "Backtracking + MRV & DEG + LCV + AC3";
    // String algorithm = "Backtracking + MRV & DEG";
    // String algorithm = "Backtracking";


		// =====[ Execucao principal ]====================

    CspListener.StepCounter<Person, PersonSchedule> stepCounter = new CspListener.StepCounter<>();
    AlgorithmCtrl algorithmCtrl = new AlgorithmCtrl(members, timeSlots, constraintNames);
    
    System.out.println("Alocar funcionários ("+algorithm+")");
    Timer timer = new Timer();
    Set<Optional<Assignment<Person, PersonSchedule>>> solutions = algorithmCtrl.useAlgorithm(algorithm, stepCounter);
    String tempo = timer.toString();
    long numResultados = solutions.size();


    // =====[ Exibindo os resultados ]================

    System.out.println("Tempo decorrido = "+ tempo);
    System.out.println("Solucoes obtidas = "+ numResultados);
    System.out.println(stepCounter.getResults() + "\n");

    ManageResults mr = new ManageResults(solutions, timeSlots, members);
    List<Schedule> schedules = mr.getSchedules();

    System.out.println(mr.getResult(schedules.get(0)));
    System.out.println(mr.getResult(schedules.get(1)));
	}
}