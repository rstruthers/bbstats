package online.bbstats.service.user;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import online.bbstats.domain.User;
import online.bbstats.domain.UserForm;
import online.bbstats.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.debug("Getting user={}", id);
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        LOGGER.debug("Getting all users");
        return userRepository.findAll(new Sort("email"));
    }

    @Override
    public User create(UserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }

	@Override
	public void update(UserForm form) {
		Optional<User> userOpt = getUserById(form.getId());
		User user = userOpt.get();
		if (user == null) {
			return;
		}
		user.setEmail(form.getEmail());
		if (StringUtils.trimToNull(form.getPassword()) != null) {
			user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
		}
        user.setRole(form.getRole());
        userRepository.save(user);
	}

}
