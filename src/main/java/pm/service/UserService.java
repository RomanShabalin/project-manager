package pm.service;

import org.springframework.stereotype.Service;
import pm.entity.Users;
import pm.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(Users user) {
        userRepository.save(user);
    }
}
