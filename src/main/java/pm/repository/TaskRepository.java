package pm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pm.entity.Users;
import pm.entity.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findById(Integer id);

    @Query(value = "select * from task where name ilike %:name% order by id", nativeQuery = true)
    List<Task> findByName(@Param("name") String name);

    Task findByUsersAndId(Users user, Integer id);
}
