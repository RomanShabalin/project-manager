package pm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pm.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
}
