package projetocsp.entities;

import aima.core.search.csp.Variable;

public class Person extends Variable {

  // private List<TimeSlot> preferences;
  private Integer workingHours;

  public Person(String name, Integer workingHours) {
    super(name);
    this.workingHours = workingHours;
  }

  public Integer getWorkingHours() {
    return workingHours;
  }

  // public boolean free() {
  // }
}
