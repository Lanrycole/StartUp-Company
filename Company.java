package company;


import java.io.EOFException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Company Class represent the Company DataBase this class has the method as add
 * employee,delete employee,find employee,find senior employee All the employee
 * is represented by an Arraylist class used Manager, Staff, Temp
 * 
 * @author Lanre Oreyomi
 * @version 1.3
 * 
 */
public class Company {
	/* An Array hold Employee object */
	private ArrayList<Employee> eList;

	/**
	 * Maximum number of Employees, final 10 {@value }
	 */
	// private final int MAXNUMBEREMPLOYEE = 10;
	private int currentEmployee = 0;

	public void setCurrentEmployee(int currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	public int getCurrentEmployee() {
		return this.currentEmployee;
	}

	/**
	 * Default constructor
	 */
	public Company() {
		eList = new ArrayList<Employee>();
	}

	/**
	 * addEmployee Add a new employee to the Arraylist, before that will generate
	 * some additional information based on the employee type: Manager Staff and
	 * Tempotory Employee
	 * 
	 * @param name           employee's name
	 * @param employeeNumber employee's number
	 * @param startDate      starting date of employ
	 * @param salary         employee's number
	 * @param employeeType   employee's number : Manager,Staff,Temp
	 * @return Employee
	 * @throws OutOfMemoryError Run out of memory
	 */
	public Employee addEmployee(String name, int employeeNumber, OurDate startDate, double salary, int employeeType)
			throws OutOfMemoryError {
		final int MANAGER = 1;
		final int STAFF = 2;
		final int TEMP = 3;
		Employee eNew;

		switch (employeeType) {

		case MANAGER:
			eNew = new Manager(name, employeeNumber, startDate, salary);
			break;
		case STAFF:
			eNew = new Staff(name, employeeNumber, startDate, salary);
			break;
		case TEMP:
			eNew = new Temp(name, employeeNumber, startDate, salary);
			break;
		default:
			eNew = null;
			break;
		}
		if (null == eNew) {
			/* left here just for debug purpose */
			System.out.println("Wrong Input Can not process");
		}

		if (!isMaxmiumEmployees() && null != eNew) {
			/* make sure not duplicate */
			for (Employee e1 : eList) {
				if (e1.equals(eNew))
					return null;
			}
			eList.add(eNew);
		}

		return eNew;

	}

	/**
	 * Return the currentNumber of employees within arraylist
	 * 
	 * @return size
	 */
	public int currentNumberEmployees() {

		return eList.size();
	}

	/**
	 * check to see if the Heap memory is enough to save the new employee ,change in
	 * version 1.3
	 * 
	 * @return boolean
	 * @throws OutOfMemoryError Memory run out
	 */
	public boolean isMaxmiumEmployees() throws OutOfMemoryError {
		/*
		 * Check the avaliable memory to see if there is enought memory to save a new
		 * employee
		 * 
		 */
		boolean maxReached = false;

		long heapFreeSize = Runtime.getRuntime().freeMemory();
		System.out.println(heapFreeSize);
		/**
		 * Since it is hard to calculate the real object size we assume the maxmium size
		 * of employee and it's sub object is 128 Byte
		 */
		if (heapFreeSize <= 128) {
			maxReached = true;
			throw new OutOfMemoryError("No Memory Error to save the a new Employee!!");
		}

		return maxReached;
	}

	public Employee addEmployee(Employee e) {
		eList.add(e);
		setCurrentEmployee(currentEmployee++);
		return e;
	}

	/**
	 * found the employee according to the id return : Employee if not founded or
	 * wrong id , will return null
	 * 
	 * @param empNum Employee's name
	 * @return Employee Employee,could be null
	 */
	public Employee findEmployee(int empNum) {
		/*
		 * if find employee based on the empNumb , return null if not founded
		 * 
		 */
		Employee e = null;
		int employeeNumber = 0;

		for (Employee el : eList) {

			if (el.getEmployeeNumber() == empNum) {
				e = el;
				this.currentEmployee = employeeNumber;
				// break to exit loop: only try find the first match
				break;
			}
			employeeNumber++;
		}
		return e;
	}

	public Employee findEmployeeByNumber(int currentNumber) {
		/*
		 * if find employee based on the empNumb , return null if not founded
		 * 
		 */
		Employee e = null;
		
		if (currentNumber >= 0 && currentNumber < eList.size())
			e =  eList.get(currentNumber);
		return e;
	}

	/**
	 * delete the employee according to the id return : Employee if not founded or
	 * wrong id , will return null
	 * 
	 * @param empNum employee's id
	 * @return Employee employee deleted could be null if it not exist
	 */
	public Employee deleteEmployee(int empNum) {
		/*
		 * not found employee number return null
		 * 
		 */
		Employee e = null;
		int employeeNumber = 0;

		for (Employee el : eList) {

			if (el.getEmployeeNumber() == empNum) {
				eList.remove(el);
				this.currentEmployee = employeeNumber;
				e = el;
				// break to exit loop: only delete the first founded
				break;
			}
			employeeNumber++;
		}
		return e;

	}

	/**
	 * new Employee array copy of current number of employees return ArrayList of
	 * Employee
	 * 
	 * @return ArrayList<Employee> list of Employees
	 */
	public ArrayList<Employee> getEmployees() {
		ArrayList<Employee> eReturnList = new ArrayList<Employee>();
		for (Employee e : eList) {
			eReturnList.add(e);
		}
		return eReturnList;
	}

	/**
	 * new Employee arraylist copy of all senior employees ArrayList of Employee
	 * 
	 * @return ArrayList<Employee> List of Senior Employees
	 */
	public ArrayList<Employee> findSeniorEmployee() {
		final int YEAR_BEFORE_2008 = 2008;
		ArrayList<Employee> seniorList = new ArrayList<Employee>();

		for (Employee e : eList) {
			if (e.getStartDate().getYear() <= YEAR_BEFORE_2008) {
				seniorList.add(e);
			}
		}

		return seniorList;
	}

	/**
	 * serialize the Employee inside the database to a file
	 */
	public void saveEmployeesToFile() {

		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		// get the employee list
		ArrayList<Employee> writeList = this.getEmployees();
		file = new File("CurrentEmployees.emp");
		// not exist create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			for (Employee e : writeList)
				out.writeObject(e);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				out.close();
				this.setCurrentEmployee(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * De-serialize the Employee from a file
	 */
	public void loadEmployeesFromFile() {
		File file = new File("CurrentEmployees.emp");
		FileInputStream fis = null;
		ObjectInputStream in = null;
		Employee employee = null;
		ArrayList<Employee> readList = new ArrayList<>();

		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);

			while ((employee = (Employee) in.readObject()) != null) {
				readList.add(employee);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			for (Employee em : readList)
				System.out.println(em);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				in.close();
				this.setCurrentEmployee(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Employee findCurrentEmployee() {
		if (eList.get(currentEmployee) != null)
			return eList.get(currentEmployee);
		else
			return null;

	}

	public Employee getPrivousEmployee() {
		/*
		 * validate currentEmployee first
		 */
		if (currentEmployee >= 0 && currentEmployee < eList.size()) {
			/* for last one current always p to last */
			if (currentEmployee == 0) {
				currentEmployee = 0;
				return eList.get(currentEmployee);
			} else
				return eList.get(--currentEmployee);
		}
		return null;

	}

	public Employee getNextEmployee() {
		/*
		 * validate currentEmployee first
		 */
		System.out.println(currentEmployee);
		if (currentEmployee >= 0 && currentEmployee < eList.size()) {
			/* for last one current always p to last */
			if (currentEmployee == (eList.size() - 1)) {
				currentEmployee = eList.size() - 1;
				return eList.get(currentEmployee);
			} else
				return eList.get(++currentEmployee);
		}

		// default return null
		return null;

	}

}