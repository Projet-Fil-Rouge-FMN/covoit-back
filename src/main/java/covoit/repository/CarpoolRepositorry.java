package covoit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import covoit.entities.Carpool;
import covoit.entities.Category;
import covoit.entities.UserAccount;

public interface CarpoolRepositorry  extends JpaRepository<UserAccount, Integer>{


    List<Carpool> findByUserAccounts_Id(int userId);
    List<Carpool> fifindByI(int carpoolId);
    void deleteById(int id);

}
