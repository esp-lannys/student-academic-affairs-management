package iu.cse.lannis.servicemailsender.repository;

import iu.cse.lannis.servicemailsender.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
}
