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

	private Set<Optional<Assignment<Person, TimeSlot>>> solutions;
  private List<TimeSlot> timeSlots;
  private List<Person> members;
	
	public ManageResults(
		Set<Optional<Assignment<Person, TimeSlot>>> solutions,
    List<TimeSlot> timeSlots,
    List<Person> members
  ) {
		this.solutions = solutions;
		this.timeSlots = timeSlots;
    this.members = members;
	}

  public String getResult(Schedule schedule) {
    String result = "Hora";
    for (Person p : members) {
      result += "\t|" + p.getName().substring(0, 3);
    }
    result += "\n";
    result += "________________________________________________\n";
    for (TimeSlot tl : schedule.getTimeSlots()) {
      result += tl.getHour() + "\t";
      for (Person p : members) {
        if (tl.getPerson() == null) {
          result += "| \t";
        } else if (p.getName().equals(tl.getPerson().getName())) {
          result += "|   V\t";
        } else {
          result += "| \t";
        }
      }
      result += "\n";
    }
    return result;
  }
	
	public List<Schedule> getSchedules() {
		List<Schedule> schedules = new ArrayList<>();
		if(solutions.isEmpty()) return schedules;
		
		for (Optional<Assignment<Person, TimeSlot>> solution : solutions) {
			LinkedHashMap<Person, TimeSlot> assignment = solution.get().getVariableToValueMap();
			List<TimeSlot> timeSlots = cloneTimeSlots();

      //Converter uma atribuição em uma lista de blocos de horarios alocados
      addMembersToTimeSlots(timeSlots, assignment);
			
			//Criar um horario e adiociona-lo na lista de horarios
			schedules.add(new Schedule(timeSlots));
		}
		return schedules;
	}

  private void addMembersToTimeSlots(List<TimeSlot> timeSlots2, LinkedHashMap<Person, TimeSlot> assignment) {
    for (Map.Entry<Person, TimeSlot> entry : assignment.entrySet()) {
      for (TimeSlot ts : timeSlots2) {
        if (ts.getHour() == entry.getValue().getHour()) {
          ts.setPerson(entry.getKey());
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