package be.abis.exercise.test;

import be.abis.exercise.exception.BirthDateNotCorrectException;
import be.abis.exercise.exception.EmailNotCorrectException;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.FilePersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person p1;
    private Person p2;
    private Person p3;

    @Mock
    private Company c;

    @BeforeEach
    public void setUp(){
        p1 = new Person("Jana", "Heitkemper", LocalDate.now().minusYears(24));

        // person with email & password, birthday tomorrow
        p2 = new Person("Merlin", "Heitkemper", LocalDate.now().minusYears(21).plusDays(1),
                "merlinheitkemper@gmail.com", "12345");

        // person with everything + company
        p3 = new Person("Jutta", "Heitkemper", LocalDate.now().minusYears(126),
                "jutta@email.com", "174", c);
    }

    @Test
    public void checkEqualPersons(){
        Person p1copy = new Person(p1.getFirstName(), p1.getLastName(), p1.getBirthDate());
        p1copy.setPersonNumber(p1.getPersonNumber());
        assertTrue(p1.equals(p1copy));
    }

    @Test
    public void checkHashMap(){
        HashSet set = new HashSet<>();

        Person p1Copy = new Person(p1.getFirstName(), p1.getLastName(), p1.getBirthDate());
        p1Copy.setPersonNumber(p1.getPersonNumber());

        set.add(p1);
        set.add(p1Copy);

        assertEquals(1, set.size());
    }

    @Test
    public void checkHashMapWithAllAttributes(){
        HashSet set = new HashSet<>();

        Person p2Copy = new Person(p2.getFirstName(), p2.getLastName(), p2.getBirthDate(), p2.getEmail(), p2.getPassword());
        p2Copy.setPersonNumber(p2.getPersonNumber());

        set.add(p2);
        set.add(p2Copy);

        assertEquals(1, set.size());
    }

    @Test
    @Tag("AgeTests")
    @DisplayName("Given a birthdate exactly 24 years ago on this day should return 24")
    public void janaIs24BirthdayToday() throws BirthDateNotCorrectException {
        assertEquals(24, p1.calculateAge());
    }

    @Test
    @Tag("AgeTests")
    @DisplayName("Given a person with their 21st birthday tomorrow should return 21")
    public void merlinIs20BirthdayTomorrow() throws BirthDateNotCorrectException {
        assertEquals(20, p2.calculateAge());
    }

    @Test
    @Tag("AgeTests")
    @DisplayName("Given a person that is above 125 years old should throw an exception")
    public void juttaFailsBcSheIs126(){
        // problem : the birthdate is never set bc the exception is already caught in the constructor
        // so if we call calculate age on a person that does not have a birthdate (and everyone actually should have a birthdate)
        // then the calculate age method throws another birthdate not correct exception bc its null
        assertThrows(BirthDateNotCorrectException.class, p3::calculateAge);
    }
    // so this test actually checks if it works if birthdate is null

    @Test
    @Tag("EmailTests")
    @DisplayName("given invalid email then throws exception")
    public void invalidEmailThrowsException(){
        String email = "@gmail.com";
        assertFalse(p1.isValidEmail(email));
    }
}