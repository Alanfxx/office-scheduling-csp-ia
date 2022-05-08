package projetocsp.entities;

public class TimeSlot {
  private Integer hour;
  private Person person;

  public TimeSlot(Integer hour) {
    this.hour = hour;
    this.person = null;
  }

  public Integer getHour() {
    return hour;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }
}
