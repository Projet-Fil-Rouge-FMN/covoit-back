package covoit.services;

import covoit.dtos.CarpoolDTO;
import covoit.dtos.UserAccountDTO;
import covoit.entities.Carpool;
import covoit.entities.Route;
import covoit.entities.UserAccount;
import covoit.entities.Vehicle;
import covoit.repository.CarpoolRepositorry;
import covoit.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private CarpoolRepositorry carpoolRepository;

    public void registerUser(UserAccountDTO userDTO) {
        UserAccount userAccount = mapToEntity(userDTO);
        userAccountRepository.save(userAccount);
    }

    public void updateUser(UserAccountDTO userDTO) {
        UserAccount userAccount = mapToEntity(userDTO);
        userAccountRepository.save(userAccount);
    }

    public UserAccountDTO findById(int id) {
        UserAccount userAccount = userAccountRepository.findById(id).orElse(null);
        return mapToDTO(userAccount);
    }

    public void deleteUser(int id) {
        userAccountRepository.deleteById(id);
    }

    public List<UserAccountDTO> findAllUsers() {
        List<UserAccount> users = userAccountRepository.findAll();
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void login(String email, String password) {
        // Implement login logic
    }

    public void logout(int id) {
        // Implement logout logic
    }

    public List<CarpoolDTO> getCarpoolInfo(int userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElse(null);
        return userAccount.getCarpools().stream().map(this::mapToCarpoolDTO).collect(Collectors.toList());
    }

    public void bookCarpool(int userId, int carpoolId) {
        UserAccount user = userAccountRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserAccount carpool = carpoolRepository.findById(carpoolId).orElseThrow(() -> new RuntimeException("Carpool not found"));
        user.getCarpools().addAll((Collection<? extends Carpool>) carpool);
        userAccountRepository.save(user);
    }

    public void cancelCarpool(int userId, int carpoolId) {
        UserAccount user = userAccountRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserAccount carpool = carpoolRepository.findById(carpoolId).orElseThrow(() -> new RuntimeException("Carpool not found"));
        user.getCarpools().remove(carpool);
        userAccountRepository.save(user);
    }


    private UserAccount mapToEntity(UserAccountDTO userDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userDTO.getId());
        userAccount.setName(userDTO.getName());
        userAccount.setLastName(userDTO.getLastName());
        userAccount.setDriverLicence(userDTO.isDriverLicence());
        userAccount.setPassword(userDTO.getPassword());
        List<Carpool> carpools = userDTO.getCarpools().stream().map(this::mapToCarpoolEntity).collect(Collectors.toList());
        userAccount.setCarpools(carpools);
        return userAccount;
    }

    private UserAccountDTO mapToDTO(UserAccount userAccount) {
        UserAccountDTO userDTO = new UserAccountDTO();
        userDTO.setId(userAccount.getId());
        userDTO.setName(userAccount.getName());
        userDTO.setLastName(userAccount.getLastName());
        userDTO.setDriverLicence(userAccount.isDriverLicence());
        userDTO.setPassword(userAccount.getPassword());
        List<CarpoolDTO> carpoolDTOs = userAccount.getCarpools().stream().map(this::mapToCarpoolDTO).collect(Collectors.toList());
        userDTO.setCarpools(carpoolDTOs);
        return userDTO;
    }

    private CarpoolDTO mapToCarpoolDTO(Carpool carpool) {
        CarpoolDTO carpoolDTO = new CarpoolDTO();
        carpoolDTO.setId(carpool.getId());
        carpoolDTO.setAvailableSeat(carpool.getAvailableSeat());
        carpoolDTO.setStartDate(carpool.getStarDate());
        carpoolDTO.setVehicle(mapToVehicleDTO(carpool.getVehicule()));
       // carpoolDTO.setRoute(mapToRouteDTO(carpool.getRoute()));
        List<UserAccountDTO> userDTOs = carpool.getUserAccounts().stream().map(this::mapToDTO).collect(Collectors.toList());
        carpoolDTO.setUserAccounts(userDTOs);
        return carpoolDTO;
    }

    private Vehicle mapToVehicleDTO(Vehicle vehicule) {
	// TODO Auto-generated method stub
	return null;
    }

    private Carpool mapToCarpoolEntity(CarpoolDTO carpoolDTO) {
        Carpool carpool = new Carpool();
        carpool.setId(carpoolDTO.getId());
        carpool.setAvailableSeat(carpoolDTO.getAvailableSeat());
        carpool.setStarDate(carpoolDTO.getStartDate());
        carpool.setVehicle(mapToVehicleEntity(carpoolDTO.getVehicle()));
        //carpool.setRoute(mapToRouteEntity(carpoolDTO.getRoute()));
        List<UserAccount> users = carpoolDTO.getUserAccounts().stream().map(this::mapToEntity).collect(Collectors.toList());
        carpool.setUserAccounts(users);
        return carpool;
    }

    private Vehicle mapToVehicle(Vehicle vehicle) {
	return vehicle;
        // Implement the mapping logic from Vehicle entity to VehicleDTO
    }

    private Vehicle mapToVehicleEntity(Vehicle vehicleDTO) {
	return vehicleDTO;
        // Implement the mapping logic from VehicleDTO to Vehicle entity
    }
//
//    private Route mapToRouteDTO(Route route) {
//	return route;
//        // Implement the mapping logic from Route entity to RouteDTO
//    }
//
//    private Route mapToRouteEntity(Route routeDTO) {
//	return routeDTO;
//        // Implement the mapping logic from RouteDTO to Route entity
//    }
}
