package covoit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import covoit.entities.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{

	UserAccount findByName(String name);
}
