package projetocsp.entities;

import java.util.List;

public class Schedule {

	private List<TimeSlot> timeSlots;

	public Schedule(List<TimeSlot> timeSlots) {
    timeSlots.sort((tl1, tl2) -> {
      if (tl1.getHour() < tl2.getHour()) return -1;
      return 1;
    });

		this.timeSlots = timeSlots;
	}
	
	public List<TimeSlot> getTimeSlots() {
		/*
		 * Os blocos de horario que não tiver com funcionario alocado
		 * estará como "null" no campo "person"
		 */
		return timeSlots; 
	}
}
