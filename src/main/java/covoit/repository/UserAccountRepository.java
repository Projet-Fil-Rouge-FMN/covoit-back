package covoit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import covoit.dtos.UserAccountDTO;
import covoit.entities.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{

	UserAccount findByName(String name);
	List<UserAccountDTO> findAllUsers();
}
