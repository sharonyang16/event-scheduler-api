package event_scheduler_api.repository;

import event_scheduler_api.model.Friendship;
import event_scheduler_api.util.factory.FriendshipDataFactory;
import event_scheduler_api.util.factory.UserDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FriendshipRepositoryTest {
    @Autowired
    private FriendshipRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("")
    void findFriendshipByUser1AndUser2Valid() {
        Friendship friendship = FriendshipDataFactory.createFriendshipBetweenUser1AndUser2();
        this.entityManager.persistAndFlush(friendship);

        Optional<Friendship> foundFriendship = this.repository.findFriendshipByUser1AndUser2(
                UserDataFactory.createUser1(),
                UserDataFactory.createUser2());


    }
}