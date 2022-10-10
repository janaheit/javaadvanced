package be.abis.exercise.test;

import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.FilePersonRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsTest {

    public static void main(String[] args) {
        List<Person> persons = FilePersonRepository.getInstance().getPersons();

        System.out.println("all people not linked to a company:");
        persons.stream()
                .filter(person -> person.getCompany() == null)
                .forEach(System.out::println);

        System.out.println("------------------------------------");
        System.out.println("persons that start with S");
        persons.stream()
                .filter(person -> person.getLastName().startsWith("S"))
                .sorted(Comparator.comparing(p -> p.getFirstName()))
                .map(person -> person.getFirstName() + " " + person.getLastName().toUpperCase())
                .forEach(System.out::println);


        System.out.println("------------------------------------");
        System.out.println("distinct companies");
        persons.stream()
                .filter(person -> person.getCompany()!= null)
                .map(person -> person.getCompany())
                .distinct()
                .forEach(System.out::println);

        System.out.println("------------------------------------");
        System.out.println("nr of people that work in Leuven");
        long count = persons.stream()
                .filter(person -> person.getCompany()!= null && person.getCompany().getAddress().getTown().equals("Leuven"))
                .count();
        System.out.println(count);

        System.out.println("------------------------------------");
        System.out.println("youngest person");
        Person youngest = persons.stream()
                .sorted(Comparator.comparing(Person::calculateAge))
                .findFirst().get();

        System.out.println(youngest);

        System.out.println("------------------------------------");
        System.out.println("First person older than 50");
        Person olderThan50 = persons.stream()
                .filter(person -> person.calculateAge() > 50)
                .sorted(Comparator.comparing(Person::calculateAge))
                .findFirst().get();

        System.out.println(olderThan50);

        System.out.println("------------------------------------");
        System.out.println("Group persons by company");
        Map<Company, List<Person>> groupByCompany = persons.stream()
                .filter(p -> p.getCompany() != null)
                .collect(Collectors.groupingBy(p -> p.getCompany()));
        System.out.println(groupByCompany);

        System.out.println("------------------------------------");
        System.out.println("How many persons per company?");
        Map<Company, Long> countByCompany = persons.stream()
                .filter(p -> p.getCompany() != null)
                .collect(Collectors.groupingBy(p -> p.getCompany(), Collectors.counting()));
        System.out.println(countByCompany);

        System.out.println("------------------------------------");
        System.out.println("Average age of all ");
        double averageAge = persons.stream()
                .collect(Collectors.averagingLong(p -> p.calculateAge()));

        System.out.println(averageAge);

        System.out.println("--------------------Exercise 4-------------------");

        System.out.println("Size before: " + persons.size());
        persons.removeIf(person -> person.getCompany() == null);
        System.out.println("Size after removing persons without company: "+persons.size());

        System.out.println("--------------------Exercise 5-------------------");
        FilePersonRepository repository = FilePersonRepository.getInstance();

        System.out.println(persons);

        try (Stream<String> lines = Files.lines(Paths.get("/temp/javacourses/persons.txt"))){
            lines.map(line -> repository.createPerson(line))
                    .filter(person -> person.getCompany() != null
                            && person.getCompany().getAddress().getCountryCode().equals("BE")
                            && person.calculateAge() > 40)
                    .forEach(person -> repository.writeOnePersonToFile("/temp/javacourses/personsfiltered.txt", person));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        String emailRegex = "\\S@[a-z]+\\.[a-z]{2,5}";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher("l?k@gmail.com");
        System.out.println(matcher.matches());
    }
}
