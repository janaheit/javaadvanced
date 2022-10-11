package be.abis.exercise.repository;

import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.Person;
import be.abis.exercise.model.Session;

import java.util.List;

public interface SessionRepository {
    List<Session> getSessions();

    void printSessionsSortedToFile(String file);
    void writeAllSessionsToFile(String file, List<Session> sessions);
}
