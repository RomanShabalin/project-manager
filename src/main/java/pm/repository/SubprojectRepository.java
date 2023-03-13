package pm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pm.entity.Subproject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubprojectRepository extends JpaRepository<Subproject, Integer> {
    Optional<Subproject> findById(Integer id);

    @Query(value = "select * from subproject where name ilike %:name% order by id", nativeQuery = true)
    List<Subproject> findByName(@Param("name") String name);
}
