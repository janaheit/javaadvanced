package be.abis.exercise.test;

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
        assertEquals(personLines.size(), 6);
    }

}