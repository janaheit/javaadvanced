package be.abis.exercise.test;

import be.abis.exercise.repository.FileSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(2, nr);
    }
}