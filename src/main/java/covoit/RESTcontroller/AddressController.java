package covoit.RESTcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import covoit.entities.Address;
import covoit.services.AddressService;

/**Define routes linked to Addresses
 * 
 */
@RestController
@RequestMapping("/addresses")
public class AddressController {
	@Autowired
	private AddressService service;
	
	/**Get all addresses
	 * 
	 */
	@GetMapping("/")
	public Iterable<Address> getAddresses() {
		return service.getAdresses();
	}
	
	/**get the address corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return The address
	 */
	@GetMapping("/{id}")
	public Address getAddress(@PathVariable int id) {
		return service.getAddress(id);
	}
	
	/**Update the address corresponding to the id given
	 * @param id : Id given
	 * @param address : modified address
	 * @return A confirmation message
	 */
	@PutMapping("/{id}")
	public void updateAddress(@PathVariable int id, Address address) {
		service.updateAddress(id, address);
	}
	
	/**Create an address 
	 * @param address : the new address
	 * @return A confirmation message
	 */
	@PostMapping
	public void createAddress(Address address) {
		service.createAddress(address);
	}

	/**Delete the address corresponding to the id given
	 *  @param id : Id given
	 * @return A confirmation message
	 */
	@DeleteMapping("/{id}")
	public void deleteAddress(int id) {
		service.deleteAddress(id);
	}
}
