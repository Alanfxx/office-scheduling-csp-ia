package projetocsp.entities;

import java.util.ArrayList;
import java.util.List;

public class Person implements Cloneable {

  private String name;
  private Integer workingHours;
  private List<TimeSlot> preferences;

  public Person(String name, Integer workingHours) {
    this.name = name;
    this.workingHours = workingHours;
    this.setPreferences(new ArrayList<TimeSlot>());
  }

  public List<TimeSlot> getPreferences() {
    return preferences;
  }

  public void setPreferences(List<TimeSlot> preferences) {
    this.preferences = preferences;
  }

  public String getName() {
    return name;
  }

  public Integer getWorkingHours() {
    return workingHours;
  }

  @Override
  protected Person clone() throws CloneNotSupportedException {
		Person result;
		try {
			result = (Person) super.clone();
			result.name = name;
			result.workingHours = workingHours;
		} catch (CloneNotSupportedException e) {
			throw new UnsupportedOperationException("Pessoa nao pode ser clonada");
		}
		return result;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((workingHours == null) ? 0 : workingHours.hashCode());
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
    Person other = (Person) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (workingHours == null) {
      if (other.workingHours != null)
        return false;
    } else if (!workingHours.equals(other.workingHours))
      return false;
    return true;
  }
}
