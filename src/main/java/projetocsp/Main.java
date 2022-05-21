package projetocsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
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

    /*
     * [IMPORTANTE]
     * A soma de todos as workingHours nao pode ultrapassar 24 horas
     */
    Person alice = new Person("Alice", 4);
    Person bob = new Person("Bob", 3);
    Person charlie = new Person("Charlie", 2);
    Person david = new Person("David", 2);
    Person eve = new Person("Eve", 4);

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


    // =====[ Escolhendo as restrições ]==============

    Scanner read = new Scanner(System.in);
    String input = "";
    System.out.println("Selecione as restricoes:\n");
    System.out.println("[1] MaxWorkingHours\n[2] PreferredSchedule\n[3] AssignAllPeople");
    System.out.println("[4] Todas as restricoes\n");
    System.out.print("Exemplo >>1,2\n\n>>");
    input = read.nextLine();

    List<String> options = new ArrayList<String>(
      Arrays.asList(input.trim().split(","))
    );

    List<String> constraintNames = new ArrayList<>();
    if (options.contains("4")) {
      constraintNames.add("MaxWorkingHours");
      constraintNames.add("PreferredSchedule");
      constraintNames.add("AssignAllPeople");
    } else {
      for (String option : options) {
        switch(option) {
          case "1":
            constraintNames.add("MaxWorkingHours");
            break;
          case "2":
            constraintNames.add("PreferredSchedule");
            break;
          case "3":
            constraintNames.add("AssignAllPeople");
        }
      }
    }


    // =====[ Escolhendo o algoritmo ]================

    input = "";
    System.out.println("Selecione um algoritmo:\n");
    System.out.println("[1] MinConflictsSolver");
    System.out.println("[2] Backtracking + MRV & DEG + LCV + AC3");
    System.out.println("[3] Backtracking + MRV & DEG");
    System.out.print("[4] Backtracking\n\n>>");

    String alg = read.nextLine();
    String algorithm = "";
    switch(alg.trim().charAt(0)) {
      case '1':
        algorithm = "MinConflictsSolver";
        break;
      case '2':
        algorithm = "Backtracking + MRV & DEG + LCV + AC3";
        break;
      case '3':
        algorithm = "Backtracking + MRV & DEG";
        break;
      case '4':
        algorithm = "Backtracking";
    }


		// =====[ Execucao principal ]====================

    CspListener.StepCounter<TimeSlot, Person> stepCounter = new CspListener.StepCounter<>();
    AlgorithmCtrl algorithmCtrl = new AlgorithmCtrl(members, timeSlots, constraintNames);
    
    Timer timer = new Timer();
    Set<Optional<Assignment<TimeSlot, Person>>> solutions = algorithmCtrl.useAlgorithm(algorithm, stepCounter);
    String tempo = timer.toString();
    long numResultados = solutions.size();
    
    
    // =====[ Exibindo os resultados ]================
    
    ManageResults mr = new ManageResults(solutions, timeSlots, members);
    List<Schedule> schedules = mr.getSchedules();
    
    input = "";
    int currentSolution = 1;
    
    do {
      try {clear();} catch (Exception e) {}
      System.out.println("Restricoes selecionadas = " + constraintNames);
      System.out.println("Algoritmo selecionado = " + algorithm);
      System.out.println("Tempo decorrido = " + tempo);
      System.out.println("Solucoes obtidas = " + numResultados);
  
      if (stepCounter.getResults().get("assignmentCount") != null)
        System.out.println("Atribuicoes = "+stepCounter.getResults().get("assignmentCount"));
    
      if (stepCounter.getResults().get("inferenceCount") != null)
        System.out.println("Inferencias = "+stepCounter.getResults().get("inferenceCount"));
  
      if (schedules.size() == 0) {
        read.close();
        return;
      } else {
        System.out.println();
        if (schedules.size() == currentSolution - 1) currentSolution = 1;
  
        System.out.println("Exibindo solucao: " + currentSolution);
        System.out.println();
        System.out.println(mr.getResult(schedules.get(currentSolution - 1)));
        currentSolution++;
        System.out.print("[Enter] Proxima solucao | [S] Sair\n>>");
        input = read.nextLine();
      }
    } while(!input.toLowerCase().equals("s"));

    read.close();
	}

  private static void clear() throws IOException, InterruptedException{
    //Limpa a tela no windows, no linux e no MacOS
    if (System.getProperty("os.name").contains("Windows"))
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    else
      Runtime.getRuntime().exec("clear");
  }
}