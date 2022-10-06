package be.abis.exercise.repository;

import be.abis.exercise.model.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> getPersons();

    void printPersonsSortedToFile(String file);
    void writeAllPersonsToFile(String file);
}
