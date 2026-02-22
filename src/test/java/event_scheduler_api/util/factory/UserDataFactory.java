package event_scheduler_api.util.factory;

import event_scheduler_api.model.User;

import java.util.UUID;

public class UserDataFactory {
    private UserDataFactory(){}

    public static final UUID USER_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public static User createUser() {
        User user = new User();
        user.setId(USER_UUID);
        user.setEmail("someone@email.com");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Smith");

        return user;
    }
}
