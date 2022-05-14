package projetocsp.entities;

public class TimeSlot implements Cloneable {
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

  @Override
	public TimeSlot clone() {
		TimeSlot result;
		try {
			result = (TimeSlot) super.clone();
			result.hour = hour;
			result.person = null;
		} catch (CloneNotSupportedException e) {
			throw new UnsupportedOperationException("TimeSlot nao pode ser clonado");
		}
		return result;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((hour == null) ? 0 : hour.hashCode());
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
    TimeSlot other = (TimeSlot) obj;
    if (hour == null) {
      if (other.hour != null)
        return false;
    } else if (!hour.equals(other.hour))
      return false;
    return true;
  }
}
