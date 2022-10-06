package be.abis.exercise.repository;

import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilePersonRepository implements PersonRepository {

	private static FilePersonRepository filePersonRepository;

	private ArrayList<Person> persons = new ArrayList<Person>();

	private FilePersonRepository(){
		// creating Persons by hand for now
		Person p1 = new Person("Jana","Heitkemper", LocalDate.of(1998, 01, 06),
				"janaheitkemper@gmail.com","pqsszord", new Company("Smals"));
		Person p2 = new Person("Anna","Müller");
		Person p3 = new Person("Merlin","Heitkemper");
		Person p4 = new Person("Gina","Beden");
		Person p5 = new Person("Maya","Weidenlebbert");
		Person p6 = new Person("Ola","Herz");

		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		persons.add(p4);
		persons.add(p5);
		persons.add(p6);
	}

	@Override
	public List<Person> getPersons() {
		return persons;
	}

	public void setCompanies(ArrayList<Person> persons) {
		this.persons = persons;
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

	private StringBuilder writePersonLine(Person p){
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
		}
		personLine.append(";");

		if (p.getEmail() != null) {
			personLine.append(p.getEmail());
		}
		personLine.append(";");

		if (p.getPassword() != null){
			personLine.append(p.getPassword());
		}
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
				personLine.append(";");
				personLine.append(";");
				personLine.append(";");
				personLine.append(";");
				personLine.append(";");
				personLine.append(";");
			}
		} else {
			personLine.append(";");
			personLine.append(";");
			personLine.append(";");
			personLine.append(";");
			personLine.append(";");
			personLine.append(";");
			personLine.append(";");

		}
		return personLine;
	}

	public static FilePersonRepository getInstance() {
		if (filePersonRepository == null) filePersonRepository= new FilePersonRepository();
		return filePersonRepository;
	}

}
