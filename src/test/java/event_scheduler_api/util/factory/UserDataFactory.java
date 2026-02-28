package event_scheduler_api.util.factory;

import event_scheduler_api.model.User;

import java.util.UUID;

public class UserDataFactory {
    private UserDataFactory(){}

    public static User createUser() {
        User user = new User();
        user.setEmail("someone@email.com");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Smith");

        return user;
    }
}
