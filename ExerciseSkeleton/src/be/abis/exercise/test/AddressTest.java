package be.abis.exercise.test;

import be.abis.exercise.exception.ZipCodeNotCorrectException;
import be.abis.exercise.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {


    @BeforeEach
    void setUp() {
    }

    @Test
    void checkCorrectNL() {
        Address address = new Address("Kanaalstraat", "85", "8933DB",
                "Leeuwarden", "Netherlands", "NL");

        assertDoesNotThrow(address::checkZipCode);
    }

    @Test
    void checkWrongNLThrows() {
        Address address = new Address("Kanaalstraat", "85", "8933 DB",
                "Leeuwarden", "Netherlands", "NL");

        assertThrows(ZipCodeNotCorrectException.class, address::checkZipCode);
    }

    @Test
    void checkCorrectBE() {
        Address address = new Address("Rue de Bourgogne", "99", "1190",
                "Brussels", "België", "BE");

        assertDoesNotThrow(address::checkZipCode);
    }


        @Test
    void checkWrongBEThrows() {
        Address address = new Address("Kanaalstraat", "85", "8933 DB",
                "Leeuwarden", "België", "BE");

        assertThrows(ZipCodeNotCorrectException.class, address::checkZipCode);
    }
}