package be.abis.exercise.test;

import be.abis.exercise.exception.CompanyNotFoundException;
import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;
import be.abis.exercise.model.Session;
import be.abis.exercise.repository.FileCompanyRepository;
import be.abis.exercise.repository.FilePersonRepository;
import be.abis.exercise.repository.FileSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileSessionRepositoryTest {
    private FileSessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = FileSessionRepository.getInstance();
    }

    @Test
    public void createdCorrectly(){
        int nr = sessionRepository.getSessions().size();
        assertEquals(4, nr);
    }

    @Test
    public void coursesByJefSmits() throws PersonNotFoundException {
        Person jef = FilePersonRepository.getInstance().findPersonByName("Jef Smits");
        List<Session> sessions = sessionRepository.findSessionsByInstructor(jef);
        sessions.forEach(System.out::println);
        assertEquals(4, sessions.size());
    }

    @Test
    public void coursesInDecember2022(){
        List<Session> sessions = sessionRepository.findSessionsByMonthAndYear(12, 2022);
        assertEquals(1, sessions.size());
    }

    @Test
    public void coursesInLocation() throws CompanyNotFoundException {
        Company company = FileCompanyRepository.getInstance().findCompanyByName("Abis");
        List<Session> sessions = sessionRepository.findSessionsByLocation(company);
        assertEquals(4, sessions.size());
    }


}