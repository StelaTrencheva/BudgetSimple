package budget.simple.repository;

import budget.simple.logic.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersistentUserData extends JpaRepository<User, Long> {

}
