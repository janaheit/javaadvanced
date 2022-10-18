package be.abis.exercise.test;

import be.abis.exercise.exception.InvoiceException;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.CompanySession;
import be.abis.exercise.model.Course;
import be.abis.exercise.model.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CompanySessionTest {

    private CompanySession cs;

    @Mock
    private Course c;
    @Mock
    private Company company1;
    @Mock
    private Instructor instructor;

    @BeforeEach
    void setUp(){
        cs = new CompanySession(c, LocalDate.now(), company1, instructor, company1, 15);
    }

    @Test
    void courseWithPricePerDay20ResultsIn4200Invoice() throws InvoiceException {
        when(c.getDailyPrice()).thenReturn(20.0);
        when(c.getDays()).thenReturn(14);

        assertEquals(4200.0, cs.invoice());
    }

    @Test
    void tooHighPriceThrowsInvoiceException() {
        when(c.getDailyPrice()).thenReturn(10.0);
        when(c.getDays()).thenReturn(34);

        assertThrows(InvoiceException.class, () -> cs.invoice());
    }

    @Test
    void tooLowPriceThrowsInvoiceException() {
        when(c.getDailyPrice()).thenReturn(10.0);
        when(c.getDays()).thenReturn(5);

        assertThrows(InvoiceException.class, () -> cs.invoice());
    }

    @Test
    void lowerBoundaryDoesntThrowException() throws InvoiceException {
        when(c.getDailyPrice()).thenReturn(10.0);
        when(c.getDays()).thenReturn(6);

        assertEquals(900, cs.invoice());
    }

    @Test
    void upperBoundaryDoesntThrowException() throws InvoiceException {
        cs.setNumberOfPersons(50);
        when(c.getDailyPrice()).thenReturn(10.0);
        when(c.getDays()).thenReturn(10);

        assertEquals(5000, cs.invoice());
    }



}