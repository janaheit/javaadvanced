package be.abis.exercise.test;

import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.FilePersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilePersonRepositoryTest {
    private FilePersonRepository filePersonRepository = FilePersonRepository.getInstance();

    @Test
    public void arePersonsReadFromFile() throws IOException {
        List<Person> persons = filePersonRepository.getPersons();
        System.out.println(persons);

        List<String> personLines;
        personLines = Files.readAllLines(Paths.get("/temp/javacourses/persons.txt"));
        assertEquals(personLines.size(), persons.size());
    }

    @Test
    public void arePersonsWrittenToFile() throws IOException {
        filePersonRepository.writeAllPersonsToFile("/temp/javacourses/persons.txt");

        List<String> personLines;
        personLines = Files.readAllLines(Paths.get("/temp/javacourses/persons.txt"));
        assertEquals(personLines.size(), 9);
    }

    @Test
    public void findSandyByID() throws PersonNotFoundException {
        Person sandy = filePersonRepository.findPersonByID(1);
        assertEquals("Sandy", sandy.getFirstName());
    }

    @Test
    public void findNonExistingByID() throws PersonNotFoundException {
        assertThrows(PersonNotFoundException.class, () -> filePersonRepository.findPersonByID(10));
    }

    @Test
    public void findSandyByEmail() throws PersonNotFoundException {
        Person sandy = filePersonRepository.findPerson("sschillebeeckx@abis.be", "somepass1");
        assertEquals("Sandy", sandy.getFirstName());
    }


    @Test
    public void findNonExistingByEmail() throws PersonNotFoundException {
        assertThrows(PersonNotFoundException.class, () -> filePersonRepository.findPerson("janaheitkemper@web.de", "pqsszord"));
    }
}