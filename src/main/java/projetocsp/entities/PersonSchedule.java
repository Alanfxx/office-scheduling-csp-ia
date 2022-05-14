package projetocsp.entities;

import java.util.List;

public class PersonSchedule {
  private List<Integer> schedule;

  public PersonSchedule(List<Integer> schedule) {
    this.schedule = schedule;
  }

  public List<Integer> getSchedule() {
    return schedule;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PersonSchedule other = (PersonSchedule) obj;
    if (schedule == null) {
      if (other.schedule != null)
        return false;
    } else if (!schedule.equals(other.schedule))
      return false;
    return true;
  }
}
