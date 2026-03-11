package event_scheduler_api.util.factory;

import event_scheduler_api.model.Friendship;

public class FriendshipDataFactory {
    private FriendshipDataFactory(){}

    public static Friendship createFriendshipBetweenUser1AndUser2() {
        Friendship friendship = new Friendship();
        friendship.setUser1(UserDataFactory.createUser1());
        friendship.setUser2(UserDataFactory.createUser2());

        return friendship;
    }
}
