package covoit.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import covoit.dtos.AddressDto;
import covoit.entities.Address;
import covoit.repository.AddressRepository;
import covoit.exception.AnomalieException;

/**
 * This class is for the methods associated with the class Address
 * 
 */
@Service
public class AddressService {
    @Autowired
    protected AddressRepository repository;

    /**
     * get all the addresses in base
     * 
     * @return An iterable object including all the addresses
     */
    public List<AddressDto> findAll() {
        List<Address> address = repository.findAll();
        List<AddressDto> addressDto = new ArrayList<>();
        for(Address item : address) {
            addressDto.add(new AddressDto().toDTO(item));
        }
        return addressDto;
    }

    /**
     * get the address corresponding to the id given
     * 
     * @param id : Id given
     * @return The address
     */
    public AddressDto findById(int id) {
        Address address = repository.findById(id);
        if(address == null) {
            return null;
        }
        return new AddressDto().toDTO(address);
    }

    /**
     * Update the address corresponding to the id given
     * 
     * @param id      : Id given
     * @param address : modified address
     * @return A confirmation message
     * @throws AnomalieException 
     */
    	public boolean update(int id, AddressDto addressDto) throws AnomalieException {
    	    // Valider les champs avant la mise à jour
    	    validateFields(addressDto);

    	    Address addressDB = repository.findById(id);
    	    if (addressDB == null) {
    	        return false;
    	    }
    	    Address change = addressDto.toBean(addressDto);
    	    addressDB.setCity(change.getCity());
    	    addressDB.setCountry(change.getCountry());
    	    addressDB.setDetail(change.getDetail());
    	    addressDB.setRoutes(change.getRoutes());
    	    repository.save(addressDB);
    	    return true;
    	}

    /**
     * Create an address
     * 
     * @param address : the new address
     * @return A confirmation message
     * @throws AnomalieException 
     */
    public boolean create(AddressDto addressDto) throws AnomalieException {
        // Valider les champs avant la création
        validateFields(addressDto);

        Address addressDb = repository.findByDetailAndCityAndCountry(addressDto.getDetail(), addressDto.getCity(),
                addressDto.getCountry());
        if (addressDb == null) {
            repository.save(addressDto.toBean(addressDto));
            return true;
        }
        return false;
    }


    /**
     * Delete the address corresponding to the id given
     * 
     * @param id : Id given
     */
    public boolean delete(int id) {
        Address addressDb = repository.findById(id);
        if (addressDb == null) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    /**
     * Validate the fields of the address DTO
     * 
     * @param dto The address DTO to validate
     * @throws AnomalieException if validation fails
     */
    public void validateFields(AddressDto dto) throws AnomalieException {
        if (dto.getDetail() == null || dto.getDetail().isEmpty()) {
            throw new AnomalieException("Les champs Detail et Country ne doivent pas être vides.");
        }
        if (dto.getCountry() == null || dto.getCountry().isEmpty()) {
            throw new AnomalieException("Les champs Detail et Country ne doivent pas être vides.");
        }
    }
}