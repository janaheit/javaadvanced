package be.abis.exercise.test;


import be.abis.exercise.exception.ZipCodeNotCorrectException;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class LoggingTest {

    public static void main(String[] args) {
        // Logger log = LogManager.getLogger("exceptionLogger");
        // log.error("test");

        Address testAddress = new Address("Goethestrasse", "12", "59348", "Vorst", "Belgium", "BE");
        try {
            testAddress.checkZipCode();
        } catch (ZipCodeNotCorrectException e) {
            System.out.println(e.getMessage());
        }

        Address testAddress2 = new Address("Goethestrasse", "12", "59348", "Vorst", "Belgium", "NL");
        try {
            testAddress2.checkZipCode();
        } catch (ZipCodeNotCorrectException e) {
            System.out.println(e.getMessage());
        }

        Person person = new Person("Jana", "Heitkemper", LocalDate.now());



    }
}
