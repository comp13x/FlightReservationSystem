package com.gerardrama.service.user;

import com.gerardrama.entity.User;
import com.gerardrama.entity.form.UserCreateForm;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    User create(UserCreateForm form);

    User addTrip(long userId, Integer tripId);
}
