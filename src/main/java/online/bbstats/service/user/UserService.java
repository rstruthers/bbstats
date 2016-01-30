package online.bbstats.service.user;


import java.util.Collection;
import java.util.Optional;

import online.bbstats.domain.User;
import online.bbstats.domain.UserForm;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserForm form);

	void update(UserForm form);

}