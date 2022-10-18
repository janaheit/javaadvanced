package be.abis.exercise.test;

import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyTest {

    private Company abis;
    private Company abis2;
    private Company smals;

    @BeforeEach
    void setUp(){
        abis = new Company("Abis");
        abis2 = new Company("Abis");
        smals = new Company("Smals");
    }

    @Mock
    private Address address1;
    private Address address2;

    @Test
    void equalCompaniesWithoutAddress(){
        assertTrue(abis.equals(abis2));
    }

    @Test
    void equalCompaniesWithAddress(){
        abis.setAddress(address1);
        abis2.setAddress(address1);

        assertTrue(abis.equals(abis2));
    }

    @Test
    void notEqualDifferentNameWithoutAddress(){
        assertNotEquals(abis, smals);
    }

    @Test
    void notEqualDifferentNameWithSameAddress(){
        abis.setAddress(address2);
        smals.setAddress(address2);
        assertNotEquals(abis, smals);
    }

    @Test
    void notEqualDifferentNameWithDifferentAddress(){
        abis.setAddress(address2);
        smals.setAddress(address1);
        assertNotEquals(abis, smals);
    }

    @Test
    void notEqualSameNameWithDifferentAddress(){
        abis.setAddress(address1);
        abis2.setAddress(address2);
        assertNotEquals(abis, abis2);
    }

    @Test
    void notEqualCompanyAndAddress(){
        assertNotEquals(abis, address1);
    }

    @Test
    void notEqualCompanyAndNull(){
        assertNotEquals(abis, null);
    }

}