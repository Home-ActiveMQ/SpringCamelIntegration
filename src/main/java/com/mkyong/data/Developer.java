package com.mkyong.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_developers")
public class Developer {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dev_seq")
	@SequenceGenerator(name = "dev_seq", sequenceName = "SEQ_TBL_DEVELOPER", allocationSize = 5)
    @Column(name="id")
	private int id;

	@Column(name="FIRST_NAME")
    private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column
	private String specialty;

	@Column
	private int experience;

	@Column
	private int salary;

	public Developer() {}

	public Developer(String firstName, String lastName, String specialty, int experience, int salary) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialty = specialty;
		this.experience = experience;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Developer [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", specialty=" + specialty
				+ ", experience=" + experience + ", salary=" + salary + "]";
	}
}
