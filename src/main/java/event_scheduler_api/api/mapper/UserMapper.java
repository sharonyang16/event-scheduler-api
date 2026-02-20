package event_scheduler_api.api.mapper;

import event_scheduler_api.api.dto.response.UserResponse;
import event_scheduler_api.api.dto.response.UserSummaryResponse;
import event_scheduler_api.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId().toString())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hostingEvents(user.getHostingEvents())
                .participatingEvents(user.getParticipatingEvents())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserSummaryResponse toUserSummaryResponse(User user) {
        return UserSummaryResponse.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
