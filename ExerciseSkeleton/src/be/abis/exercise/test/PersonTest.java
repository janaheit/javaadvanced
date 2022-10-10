package be.abis.exercise.test;

import be.abis.exercise.model.Person;
import be.abis.exercise.repository.FilePersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private List<Person> persons;

    @BeforeEach
    public void setUp(){
        persons = FilePersonRepository.getInstance().getPersons();
    }

    @Test
    public void checkEqualPersons(){
        Person p1 = persons.get(0);
        Person p2 = new Person(p1.getFirstName(), p1.getLastName(), p1.getBirthDate(), p1.getEmail(), p1.getPassword());
        p2.setPersonNumber(p1.getPersonNumber());
        assertTrue(p1.equals(p2));
    }

    @Test
    public void checkHashMap(){
        HashSet set = new HashSet<>();

        Person p1 = persons.get(0);
        Person p2 = new Person(p1.getFirstName(), p1.getLastName(), p1.getBirthDate(), p1.getEmail(), p1.getPassword());
        p2.setPersonNumber(p1.getPersonNumber());

        set.add(p1);
        set.add(p2);

        assertEquals(set.size(), 1);
    }

    @Test
    public void calculateSandysAge(){
        Person sandy = persons.get(0);
        assertEquals(44, sandy.calculateAge());
    }
}