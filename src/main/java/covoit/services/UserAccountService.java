package covoit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import covoit.dtos.UserAccountDTO;
import covoit.entities.Address;
import covoit.entities.UserAccount;
import covoit.repository.UserAccountRepository;

@Service
public class UserAccountService {
@Autowired
private UserAccountRepository userAccountRepository;

@Autowired
private PasswordEncoder passwordEncoder;

public void save(UserAccount user) {
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	userAccountRepository.save(user);
}

@Autowired
private CarpoolRepository carpoolRepository;

@Override
public void createCarpool(CarpoolDTO carpoolDTO) {
    Carpool carpool = convertToEntity(carpoolDTO);
    carpoolRepository.save(carpool);
}

@Override
public void updateCarpool(Long id, CarpoolDTO carpoolDTO) {
    Carpool carpool = carpoolRepository.findById(id).orElseThrow(() -> new RuntimeException("Covoiturage non trouvé"));
    carpool.setAvailableSeat(carpoolDTO.getAvailableSeat());
    carpool.setStarDate(carpoolDTO.getStartDate());
    carpool.setVehicle(convertToEntity(carpoolDTO.getVehicle()));
    carpool.setRoute(convertToEntity(carpoolDTO.getRoute()));
    carpool.setUserAccounts(carpoolDTO.getUserAccounts().stream().map(this::convertToEntity).collect(Collectors.toList()));
    carpoolRepository.save(carpool);
}

@Override
public CarpoolDTO getCarpoolById(Long id) {
    Carpool carpool = carpoolRepository.findById(id).orElseThrow(() -> new RuntimeException("Covoiturage non trouvé"));
    return convertToDto(carpool);
}

@Override
public void deleteCarpool(Long id) {
    carpoolRepository.deleteById(id);
}

@Override
public List<CarpoolDTO> getAllCarpools() {
    return carpoolRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
}

private Carpool convertToEntity(CarpoolDTO carpoolDTO) {
    Carpool carpool = new Carpool();
    carpool.setAvailableSeat(carpoolDTO.getAvailableSeat());
    carpool.setStarDate(carpoolDTO.getStartDate());
    carpool.setVehicle(convertToEntity(carpoolDTO.getVehicle()));
    carpool.setRoute(convertToEntity(carpoolDTO.getRoute()));
    carpool.setUserAccounts(carpoolDTO.getUserAccounts().stream().map(this::convertToEntity).collect(Collectors.toList()));
    return carpool;
}

private CarpoolDTO convertToDto(Carpool carpool) {
    CarpoolDTO carpoolDTO = new CarpoolDTO();
    carpoolDTO.setId(carpool.getId());
    carpoolDTO.setAvailableSeat(carpool.getAvailableSeat());
    carpoolDTO.setStartDate(carpool.getStarDate());
    carpoolDTO.setVehicle(convertToDto(carpool.getVehicle()));
    carpoolDTO.setRoute(convertToDto(carpool.getRoute()));
    carpoolDTO.setUserAccounts(carpool.getUserAccounts().stream().map(this::convertToDto).collect(Collectors.toList()));
    return carpoolDTO;
}

private VehicleDTO convertToDto(Vehicle vehicle) {
    VehicleDTO vehicleDTO = new VehicleDTO();
    vehicleDTO.setId(vehicle.getId());
    vehicleDTO.setRegistration(vehicle.getRegistration());
    vehicleDTO.setNbSeat(vehicle.getNbSeat());
    vehicleDTO.setBrand(convertToDto(vehicle.getBrand()));
    vehicleDTO.setModel(convertToDto(vehicle.getModel()));
    vehicleDTO.setCategory(convertToDto(vehicle.getCategory()));
    return vehicleDTO;
}

private RouteDTO convertToDto(Route route) {
    RouteDTO routeDTO = new RouteDTO();
    routeDTO.setId(route.getId());
    routeDTO.setDuration(route.getDuration());
    routeDTO.setKmTotal(route.getKmTotal());
    routeDTO.setStartAddress(convertToDto(route.getStartAddress()));
    routeDTO.setEndAddress(convertToDto(route.getEndAddress()));
    return routeDTO;
}

private UserAccountDTO convertToDto(UserAccount user) {
    UserAccountDTO userDTO = new UserAccountDTO();
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setLastName(user.getLastName());
    userDTO.setDriverLicence(user.isDriverLicence());
    userDTO.setPassword(user.getPassword());
    return userDTO;
}

private Vehicle convertToEntity(VehicleDTO vehicleDTO) {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(vehicleDTO.getId());
    vehicle.setRegistration(vehicleDTO.getRegistration());
    vehicle.setNbSeat(vehicleDTO.getNbSeat());
    vehicle.setBrand(convertToEntity(vehicleDTO.getBrand()));
    vehicle.setModel(convertToEntity(vehicleDTO.getModel()));
    vehicle.setCategory(convertToEntity(vehicleDTO.getCategory()));
    return vehicle;
}

private Route convertToEntity(RouteDTO routeDTO) {
    Route route = new Route();
    route.setId(routeDTO.getId());
    route.setDuration(routeDTO.getDuration());
    route.setKmTotal(routeDTO.getKmTotal());
    route.setStartAddress(convertToEntity(routeDTO.getStartAddress()));
    route.setEndAddress(convertToEntity(routeDTO.getEndAddress()));
    return route;
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

private Address convertToDto(Address address) {
     address = new Address();
    address.setId(address.getId());
    address.setStreet(address.getStreet());
    address.setCity(address.getCity());
    address.setZipCode(address.getZipCode());
    address.setCountry(address.getCountry());
    return address;
}

private Address convertToEntity(AddressDTO addressDTO) {
    Address address = new Address();
    address.setId(addressDTO.getId());
    address.setStreet(addressDTO.getStreet());
    address.setCity(addressDTO.getCity());
    address.setZipCode(addressDTO.getZipCode());
    address.setCountry(addressDTO.getCountry());
    return address;
}

}
