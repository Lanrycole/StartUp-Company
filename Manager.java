package company;


import java.util.Scanner;

/**
 * The Manager Class is a subclass of Employee, Manager defines a new title
 * field Parent class : Employee
 * 
 * @author Lanre Oreyomi
 * @version 1.3
 */
public class Manager extends Employee {
	/**
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	private String title;

	/**
	 * Default constructor
	 */
	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Overloaded Constructor, take 4 parameters for Manager
	 * 
	 * @param name Manager employee's name
	 * @param employeeNumber  Manager employee's id
	 * @param startDate Manager employee's Starting employee date
	 * @param salary employee's salary
	 */
	public Manager(String name, int employeeNumber, OurDate startDate, double salary) {
		super(name, employeeNumber, startDate, salary);
	}

	/**
	 * Overloaded Constructor, take 5 parameters for Manager
	 * 
	 * @param name Manager employee's name
	 * @param employeeNumber Manager employee's id
	 * @param startDate Manager employee's Starting employee date
	 * @param salary Manager employee's salary
	 * @param title Manager employee's title info
	 */
	public Manager(String name, int employeeNumber, OurDate startDate, double salary, String title) {
		super(name, employeeNumber, startDate, salary);
		this.title = title;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Overloaded load ExtraInfo class, load manager employee title info
	 */
	@Override
	public void loadExtraInfo(Scanner input) {
		// TODO Auto-generated method stub
		String title;

		System.out.println("TITLE IS: ");
		input.nextLine();
		title = input.nextLine();

		this.setTitle(title);
	}
	
	
	/**
	 * Getter : get the title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter : set the title
	 * 
	 * @param title title of the Manager
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * Overided toString method from parent class Employee return : String
	 */
	/**
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + "\tManager [title=" + title + "]";
	}
	/*
	 * Overided equals method from parent class Employee return : boolean
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String getExtraInfo() {
		// TODO Auto-generated method stub
		return this.title;
	}

	@Override
	public void setExtraInfo(String s) {
		this.title = s;
		// TODO Auto-generated method stub	
	}

}
