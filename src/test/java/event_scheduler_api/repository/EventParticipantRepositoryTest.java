package event_scheduler_api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventParticipantRepositoryTest {
    @Autowired
    private EventParticipantRepository repository;

    @Autowired
    private TestEntityManager entityManager;
}