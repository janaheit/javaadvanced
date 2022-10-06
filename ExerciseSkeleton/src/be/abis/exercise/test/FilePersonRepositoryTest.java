package be.abis.exercise.test;

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
    public void arePersonsWrittenToFile(){
        filePersonRepository.writeAllPersonsToFile("/temp/javacourses/persons.txt");

        List<String> personLines;
        try {
            personLines = Files.readAllLines(Paths.get("/temp/javacourses/persons.txt"));
            assertEquals(personLines.size(), 6);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}