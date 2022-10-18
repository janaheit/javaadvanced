package be.abis.exercise.model;

import be.abis.exercise.exception.BirthDateNotCorrectException;
import be.abis.exercise.exception.EmailNotCorrectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person implements Instructor, CourseParticipant, Comparable<CourseParticipant> {

	private Logger consoleLog = LogManager.getLogger("Console");
	private Logger exceptionLogger = LogManager.getLogger("exceptionLogger");
	private static int counter = 0;

	private int personNumber;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String email;
	private String password;
	private Company company;

	public Person(String firstName, String lastName, LocalDate birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		personNumber = ++counter;

		try {
			calculateAge(birthDate);
			this.birthDate = birthDate;
		} catch (BirthDateNotCorrectException e) {
			System.out.println(e.getMessage());
		}

		consoleLog.info("Person with first name "+ firstName + " created.");
	}

	public Person(String firstName, String lastName, LocalDate birthdate, Company company) {
		this(firstName, lastName, birthdate);
		this.company = company;
	}

	public Person(String firstName, String lastName, LocalDate birthDate, String email,
				  String password) {
		this(firstName,lastName, birthDate);
		this.password = password;
		try {
			setEmail(email);
		} catch (EmailNotCorrectException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Person(String firstName, String lastName, LocalDate birthDate, String email,
			String password, Company company) {
		this(firstName,lastName,birthDate, company);
		this.password = password;
		try {
			setEmail(email);
		} catch (EmailNotCorrectException e) {
			System.out.println(e.getMessage());
		}
	}

	public Boolean isValidEmail(String email){
		String emailRegex = "\\w+[.?,;]*\\w*@[a-z]+\\.[a-z][a-z]+";

		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return personNumber == person.personNumber && firstName.equals(person.firstName)
				&& lastName.equals(person.lastName) && Objects.equals(birthDate, person.birthDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(personNumber, firstName, lastName);
	}

	public void teach(Course course) {
		System.out.println(this + " teaches " + course.getTitle());
	}

	@Override
	public String getName() {
		return firstName + " " + lastName;
	}

	public long calculateAge() throws BirthDateNotCorrectException {
		return calculateAge(this.birthDate);
	}

	public long calculateAge(LocalDate date) throws BirthDateNotCorrectException {
		if (date ==null) throw new BirthDateNotCorrectException("BirthDate is null");

		LocalDate dateToday = LocalDate.now();

		long years = ChronoUnit.YEARS.between(date, dateToday);

		if (years < 0) throw new BirthDateNotCorrectException("This birthdate lies in the future.");
		if (years > 125) throw new BirthDateNotCorrectException("This person is too old (>125)");
		return years;
	}

	public void attendCourse(Course course) {
		System.out.println(this + " follows " + course.getTitle());
	}

	@Override
	public int compareTo(CourseParticipant o) {
		return this.lastName.compareTo(((Person)o).lastName);
	}

	public static class FirstNameComparator implements Comparator<CourseParticipant>{
		@Override
		public int compare(CourseParticipant o1, CourseParticipant o2) {
			return ((Person)o1).getFirstName().compareToIgnoreCase(((Person)o2).getFirstName());
		}

	}

	// GETTERS AND SETTERS

	public int getPersonNumber() {
		return personNumber;
	}
	
	public void setPersonNumber(int personNumber) {
		this.personNumber=personNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fName) {
		firstName = fName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lName) {
		lastName = lName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		try {
			calculateAge(birthDate);
			this.birthDate = birthDate;
		} catch (BirthDateNotCorrectException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws EmailNotCorrectException {

		if (isValidEmail(email)){
			this.email = email;
		} else {
			exceptionLogger.error("Unvalid email address");
			throw new EmailNotCorrectException("This email is not valid, please enter another one.");
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static int getNumberOfPersons() {
		return counter;
	}


    
}