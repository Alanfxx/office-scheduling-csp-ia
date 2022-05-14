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
}
