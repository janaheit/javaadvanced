package be.abis.exercise.repository;

import be.abis.exercise.exception.EmailNotCorrectException;
import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FilePersonRepository implements PersonRepository {
	private Logger log = LogManager.getLogger("exceptionLogger");

	private static FilePersonRepository filePersonRepository;

	private ArrayList<Person> persons = new ArrayList<Person>();

	private FilePersonRepository(){

		try {
			List<String> personStrings = Files.readAllLines(Paths.get("/temp/javacourses/persons.txt"));
			for(String s:personStrings){
				persons.add(createPerson(s));
			}
			persons.trimToSize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	public Person createPerson(String personLine){
		String[] elements = personLine.split(";");

		// parse mandatory fields
		String number = elements[0];
		String firstName = elements[1];
		String lastName = elements[2];

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate birthDate;
		if (elements[3].equals("null")){
			birthDate = LocalDate.now();
		} else {
			birthDate = LocalDate.parse(elements[3], formatter);
		}

		// create base person
		Person p = new Person(firstName, lastName, birthDate);
		p.setPersonNumber(Integer.parseInt(number));

		if (!elements[4].equals("null")) {
			try {
				p.setEmail(elements[4]);
			} catch (EmailNotCorrectException e) {
				System.out.println(e.getMessage());
			}
		}

		if (!elements[5].equals("null")) {
			p.setPassword(elements[5]);
		}

		Company c;
		if (!elements[6].equals( "null")) {
			c = new Company(elements[6]);
			p.setCompany(c);

			if (!elements[7].equals("null")) {
				Address a = new Address(elements[7], elements[8], elements[9], elements[10], elements[11], elements[12]);
				c.setAddress(a);
			}
		}

		return p;
	}

	@Override
	public Person findPersonByID(int id) throws PersonNotFoundException {
		Person person = persons.stream()
				.filter(p -> p.getPersonNumber()==id)
				.findAny()
				.orElseThrow(() -> {
					log.error("Person with number " + id + " was not found.");
					return new PersonNotFoundException("Person with number " + id + " was not found.");
				});

		return person;
	}

	@Override
	public Person findPerson(String email, String password) throws PersonNotFoundException {
		Person person = persons.stream()
				.filter(p -> p.getEmail().equals(email) && p.getPassword().equals(password))
				.findAny()
				.orElseThrow(() -> {
					log.error("Person was not found.");
					return new PersonNotFoundException("Email or password not correct for " + email);
				});

		return person;
	}

	@Override
	public void printPersonsSortedToFile(String file){
		Collections.sort(persons);
		try (PrintWriter pw = new PrintWriter(file)){
			for (Person c: persons){
				pw.println(c);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void writeAllPersonsToFile(String file){
		try (PrintWriter pw = new PrintWriter(file)){
			for (Person p:persons){
				StringBuilder pl = writePersonLine(p);
				pw.println(pl);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public void writeAllPersonsToFile(String file, List<Person> personList){
		try (PrintWriter pw = new PrintWriter(file)){
			for (Person p:personList){
				StringBuilder pl = writePersonLine(p);
				pw.println(pl);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public void writeOnePersonToFile(String file, Person person){
		try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))){
			StringBuilder pl = writePersonLine(person);
			pw.println(pl);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public StringBuilder writePersonLine(Person p){
		StringBuilder personLine = new StringBuilder();

		// data that every person has
		personLine.append(p.getPersonNumber());
		personLine.append(";");
		personLine.append(p.getFirstName());
		personLine.append(";");
		personLine.append(p.getLastName());
		personLine.append(";");

		// optional data of person
		if (p.getBirthDate() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			personLine.append(p.getBirthDate().format(formatter));
		} else personLine.append("null");
		personLine.append(";");

		if (p.getEmail() != null) {
			personLine.append(p.getEmail());
		} else personLine.append("null");
		personLine.append(";");

		if (p.getPassword() != null){
			personLine.append(p.getPassword());
		} else personLine.append("null");
		personLine.append(";");

		if (p.getCompany() != null) {
			personLine.append(p.getCompany().getName());
			personLine.append(";");

			if (p.getCompany().getAddress() != null && p.getCompany().getAddress().getCountry() != null) {
				personLine.append(p.getCompany().getAddress().getStreet());
				personLine.append(";");

				personLine.append(p.getCompany().getAddress().getNr());
				personLine.append(";");

				personLine.append(p.getCompany().getAddress().getZipCode());
				personLine.append(";");

				personLine.append(p.getCompany().getAddress().getTown());
				personLine.append(";");

				personLine.append(p.getCompany().getAddress().getCountry());
				personLine.append(";");

				personLine.append(p.getCompany().getAddress().getCountryCode());
				personLine.append(";");
			} else {
				personLine.append("null;");
				personLine.append("null;");
				personLine.append("null;");
				personLine.append("null;");
				personLine.append("null;");
				personLine.append("null;");
			}
		} else {
			personLine.append("null;");
			personLine.append("null;");
			personLine.append("null;");
			personLine.append("null;");
			personLine.append("null;");
			personLine.append("null;");
			personLine.append("null;");

		}
		System.out.println(personLine);
		return personLine;
	}

	@Override
	public List<Person> getPersons() {
		return persons;
	}

	public void setCompanies(ArrayList<Person> persons) {
		this.persons = persons;
	}
	


	public static FilePersonRepository getInstance() {
		if (filePersonRepository == null) filePersonRepository= new FilePersonRepository();
		return filePersonRepository;
	}

}
