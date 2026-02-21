package event_scheduler_api.mapper;

import event_scheduler_api.dto.response.UserResponse;
import event_scheduler_api.model.User;
import event_scheduler_api.util.UserDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    @Test
    @DisplayName("toUserResponse should return UserResponse obj when given User is valid")
    void toUserResponseValid() {
        User user = UserDataFactory.createUser();

        UserResponse userResponse = this.userMapper.toUserResponse(user);

        assertEquals("Smith", userResponse.getLastName());
    }
}