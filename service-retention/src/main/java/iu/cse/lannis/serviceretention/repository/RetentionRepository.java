package iu.cse.lannis.serviceretention.repository;

import iu.cse.lannis.serviceretention.entity.Retention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetentionRepository extends JpaRepository<Retention, Long> {
}
