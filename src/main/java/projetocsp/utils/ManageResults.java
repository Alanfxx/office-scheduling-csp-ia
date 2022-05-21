package projetocsp.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import aima.core.search.csp.Assignment;
import projetocsp.entities.Person;
import projetocsp.entities.Schedule;
import projetocsp.entities.TimeSlot;

public class ManageResults {

	private Set<Optional<Assignment<TimeSlot, Person>>> solutions;
  private List<TimeSlot> timeSlots;
  private List<Person> members;
	
	public ManageResults(
		Set<Optional<Assignment<TimeSlot, Person>>> solutions,
    List<TimeSlot> timeSlots,
    List<Person> members
  ) {
		this.solutions = solutions;
		this.timeSlots = timeSlots;
    this.members = members;
	}

  public String getResult(Schedule schedule) {
    StringBuilder result = new StringBuilder("Hora");
    for (Person p : members) {
      if (p.getName() == "Empty") {
        result.append("\t|");
      } else {
        result.append("\t|")
          .append(p.getName().substring(0, 3))
          .append("..");
      }
    }
    result.append("\n-------------------------------------------------\n");
    for (TimeSlot tl : schedule.getTimeSlots()) {
      result.append(tl.getHour())
        .append("\t");
      for (Person p : members) {
        if (tl.getPerson().getName() == "Empty") {
          result.append("|   \t");
        } else if (p.getName().equals(tl.getPerson().getName())) {
          result.append("|   V\t");
        } else {
          result.append("|   \t");
        }
      }
      result.append("\n");
    }
    return result.toString();
  }
	
	public List<Schedule> getSchedules() {
		List<Schedule> schedules = new ArrayList<>();
		if(solutions.isEmpty()) return schedules;
		
		for (Optional<Assignment<TimeSlot, Person>> solution : solutions) {
			LinkedHashMap<TimeSlot, Person> assignment = solution.get().getVariableToValueMap();
			List<TimeSlot> timeSlots = cloneTimeSlots();

      //Converter uma atribuição em uma lista de blocos de horarios alocados
      addMembersToTimeSlots(timeSlots, assignment);
			
			//Criar um horario e adiociona-lo na lista de horarios
			schedules.add(new Schedule(timeSlots));
		}
		return schedules;
	}

  private void addMembersToTimeSlots(
    List<TimeSlot> timeSlots2,
    LinkedHashMap<TimeSlot, Person> assignment
  ) {
    for (TimeSlot ts : timeSlots2) {
      for (Map.Entry<TimeSlot,Person> entry : assignment.entrySet()) {
        if (ts.equals(entry.getKey())) {
          ts.setPerson(entry.getValue());
        }
      }
    }
  }

  private List<TimeSlot> cloneTimeSlots() {
		List<TimeSlot> timeSlotClones = new ArrayList<>();
		for(TimeSlot timeSlot : timeSlots) {
			try {
				timeSlotClones.add(timeSlot.clone());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return timeSlotClones;
	}
}