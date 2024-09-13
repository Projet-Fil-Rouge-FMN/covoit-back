package covoit.RESTcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import covoit.dtos.RouteDto;
import covoit.dtos.VehicleDto;
import covoit.exception.AnomalieException;
import covoit.services.VehicleService;
import jakarta.validation.Valid;

/**
 * Define routes linked to Addresses
 * 
 */
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	@Autowired
	private VehicleService service;

	/**
	 * Get all vehicles
	 * 
	 */
	@GetMapping
	public List<VehicleDto> findAll() {
		return service.findAll();
	}

	/**
	 * get the vehicle corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return The vehicle
	 */
	@GetMapping("/{id}")
	public VehicleDto findById(@PathVariable int id) {
		return service.findById(id);
	}
	

	/**Update the vehicle corresponding to the id given
	 * @param id : Id given
	 * @param vehicle : modified vehicle
	 */
	@PutMapping("/{id}")
	public void update(@PathVariable int id, VehicleDto vehicleDto) {
		service.update(id,vehicleDto);
	}
	
	/**Create a vehicle 
	 * @param vehicle : the new vehicle
	 */
	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody VehicleDto vehicleDto, BindingResult result)
			throws AnomalieException {
		if (!service.create(vehicleDto)) {
			throw new AnomalieException(result.getAllErrors().get(0).getDefaultMessage());
		}
		return ResponseEntity.ok("Creation reussi");
	}
	/**Delete the vehicle corresponding to the id given
	 *  @param id : Id given
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok("Suppression reussie");
	}
}
