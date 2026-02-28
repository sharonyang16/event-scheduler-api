package event_scheduler_api.repository;

import event_scheduler_api.model.User;
import event_scheduler_api.util.factory.UserDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("findByEmail should return user when given email exists")
    void findByEmailValid() {
        User user = UserDataFactory.createUser();
        this.entityManager.persistAndFlush(user);

        Optional<User> foundUser = this.repository.findByEmail("someone@email.com");

        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getEmail(), "someone@email.com");
        assertEquals(foundUser.get().getFirstName(), "John");
    }

    @Test
    @DisplayName("findByEmail should not return user when given email doesn't exist")
    void findByEmailMissing() {
        Optional<User> foundUser = this.repository.findByEmail("nonexistent@email.com");

        assertTrue(foundUser.isEmpty());
    }

    @Test
    @DisplayName("existsByEmail should return true when given email exists")
    void existsByEmailTrue() {
        User user = UserDataFactory.createUser();
        this.entityManager.persistAndFlush(user);

        boolean result = this.repository.existsByEmail("someone@email.com");
        assertTrue(result);
    }

    @Test
    @DisplayName("existsByEmail should return false when given email doesn't exists")
    void existsByEmailFalse() {
        boolean result = this.repository.existsByEmail("random@email.org");
        assertFalse(result);
    }

    @Test
    @DisplayName("deleteByEmail should delete user by email when given email exists")
    void deleteByEmailValid() {
        User user = UserDataFactory.createUser();
        this.entityManager.persistAndFlush(user);

        Optional<User> foundUser = this.repository.findByEmail("someone@email.com");
        assertTrue(foundUser.isPresent());

        this.repository.deleteByEmail("someone@email.com");
        Optional<User> foundUserAfterDelete = this.repository.findByEmail("someone@email.com");
        assertTrue(foundUserAfterDelete.isEmpty());
    }

    @Test
    @DisplayName("deleteByEmail should have no impact when given email doesn't exists")
    void deleteByEmailNothing() {
        Optional<User> foundUser = this.repository.findByEmail("notsomeone@email.com");
        assertTrue(foundUser.isEmpty());

        this.repository.deleteByEmail("notsomeone@email.com");
        Optional<User> foundUserAfterDelete = this.repository.findByEmail("notsomeone@email.com");
        assertTrue(foundUserAfterDelete.isEmpty());
    }
}