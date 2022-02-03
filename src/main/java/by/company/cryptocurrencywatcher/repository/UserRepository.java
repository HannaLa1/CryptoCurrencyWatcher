package by.company.cryptocurrencywatcher.repository;

import by.company.cryptocurrencywatcher.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
