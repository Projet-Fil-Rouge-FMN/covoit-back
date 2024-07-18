package covoit.services;

import java.nio.file.FileVisitResult;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import covoit.dtos.CarpoolDTO;
import covoit.dtos.UserAccountDTO;
import covoit.entities.Carpool;
import covoit.entities.UserAccount;
import covoit.repository.CarpoolRepositorry;

@Service
public class CarpoolServiceImpl implements CarpoolService {
    @Autowired
    private CarpoolRepositorry carpoolRepository;

    @Override
    public void createCarpool(CarpoolDTO carpoolDTO) {
//        Carpool carpool = convertToEntity(carpoolDTO);
//        carpoolRepository.save(carpool);
    }

    @Override
    public void updateCarpool(int id, CarpoolDTO carpoolDTO) {

    }

    @Override
    public CarpoolDTO getCarpoolById(int id) {
	return null;
    }

    @Override
    public void deleteCarpool(int id) {
        carpoolRepository.deleteById(id);
    }
 // Example method to convert List<UserAccountDTO> to List<CarpoolDTO>
    public List<CarpoolDTO> convertUserAccountListToCarpoolList(List<UserAccountDTO> userAccountDTOList) {
        return userAccountDTOList.stream()
                .map(this::convertUserAccountToCarpool) // Convert each UserAccountDTO to CarpoolDTO
                .collect(Collectors.toList());
    }

    // Example conversion method
    private CarpoolDTO convertUserAccountToCarpool(UserAccountDTO userAccountDTO) {
        CarpoolDTO carpoolDTO = new CarpoolDTO();
        // Assuming you have specific fields to copy
        carpoolDTO.setId(userAccountDTO.getId());
        //carpoolDTO.setName(userAccountDTO.getName());
        // Add more field mappings as needed
        return carpoolDTO;
    }

    @Override
    public List<CarpoolDTO> getAllCarpools() {
        return null;
    }

    private Carpool convertToEntity(CarpoolDTO carpoolDTO) {
        Carpool carpool = new Carpool();
        carpool.setAvailableSeat(carpoolDTO.getAvailableSeat());
        carpool.setStarDate(carpoolDTO.getStartDate());
        carpool.setVehicle(carpoolDTO.getVehicle());
        carpool.setRoute(carpoolDTO.getRoute());
        carpool.setUserAccounts(carpoolDTO.getUserAccounts().stream().map(this::convertToEntity).collect(Collectors.toList()));
        return carpool;
    }

    private CarpoolDTO convertToDto(Carpool carpool) {
        CarpoolDTO carpoolDTO = new CarpoolDTO();
        carpoolDTO.setId(carpool.getId());
        carpoolDTO.setAvailableSeat(carpool.getAvailableSeat());
        carpoolDTO.setStartDate(carpool.getStarDate());
        carpoolDTO.setVehicle(carpool.getVehicule());
        carpoolDTO.setRoute(carpool.getRoute());
        carpoolDTO.setUserAccounts(carpool.getUserAccounts().stream().map(this::convertToDto).collect(Collectors.toList()));
        return carpoolDTO;
    }

    private UserAccountDTO convertToDto(UserAccount user) {
        UserAccountDTO userDTO = new UserAccountDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        userDTO.setDriverLicence(user.isDriverLicence());
        return userDTO;
    }

    private UserAccount convertToEntity(UserAccountDTO userDTO) {
        UserAccount user = new UserAccount();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setDriverLicence(userDTO.isDriverLicence());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}