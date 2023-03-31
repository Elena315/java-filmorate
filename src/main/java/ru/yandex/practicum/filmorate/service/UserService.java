package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(Long userId) {
        return userStorage.getUserById(userId);
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User delete(Long id) {
        return userStorage.delete(id);
    }

    public void addFriend(Long userId, Long friendId) {
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("Нельзя добавить самого себя в друзья!");
        }
        friendStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("Нельзя удалить самого себя из друзей!");
        }
        friendStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        List<User> friends = new ArrayList<>();
        if (userId != null) {
            friends = friendStorage.getFriends(userId);
        }
        return friends;
    }

    public List<User> getCommonFriends(Long firstUserId, Long secondUserId) {
        User firstUser = userStorage.getUserById(firstUserId);
        User secondUser = userStorage.getUserById(secondUserId);
        Set<User> intersection = null;

        if ((firstUser != null) && (secondUser != null)) {
            intersection = new HashSet<>(friendStorage.getFriends(firstUserId));
            intersection.retainAll(friendStorage.getFriends(secondUserId));
        }
        assert intersection != null;
        return new ArrayList<>(intersection);
    }

}
