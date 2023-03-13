package pm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pm.entity.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findById(Integer id);

    @Query(value = "select * from project where name ilike %:name% order by id", nativeQuery = true)
    List<Project> findByName(@Param("name") String name);
}
