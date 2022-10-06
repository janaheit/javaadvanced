package be.abis.exercise.test;

import be.abis.exercise.model.*;
import be.abis.exercise.repository.FileCompanyRepository;
import be.abis.exercise.repository.FilePersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PublicSessionTest {

    private PublicSession publicSession;


    @BeforeEach
    void setUp() {
        Company company = FileCompanyRepository.getInstance().getCompanies().get(0);
        company.setAddress(new Address("Fonsnylaan", "223", "1190", "Brussel", "Belgie", "BE"));
        List<Person> persons = FilePersonRepository.getInstance().getPersons();

        publicSession = new PublicSession(Course.JAVA_ADVANCED, LocalDate.of(2022, 12, 18), company,
                persons.get(0));

        publicSession.addEnrolment(persons.get(1), persons.get(2), persons.get(3), persons.get(4));
    }

    @Test
    public void checkParticipantsToFile(){
        publicSession.printListOfParticipants();
    }
}