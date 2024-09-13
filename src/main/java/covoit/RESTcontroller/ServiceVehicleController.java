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

import covoit.dtos.ServiceVehicleDto;
import covoit.exception.AnomalieException;
import covoit.services.ServiceVehicleService;
import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/servicevehicle")
public class ServiceVehicleController {

	@Autowired
	private ServiceVehicleService service;

	/**
	 * Get all addresses
	 * 
	 */
	@GetMapping
	public List<ServiceVehicleDto> getAll() {
		return service.findAll();
	}

	/**
	 * get the address corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return The address
	 */
	@GetMapping("/{id}")
	public ServiceVehicleDto getById(@PathVariable int id) {
		return service.findById(id);
	}

	/**
	 * Update the Service vehicle corresponding to the id given
	 * 
	 * @param id             : Id given
	 * @param serviceVehicle : modified Service vehicle
	 * @return A confirmation message
	 */
	// TODO EXCEPTION ANOMALIE
	@PutMapping("/{id}")
	public ResponseEntity<String> updateById(@PathVariable int id, @Valid @RequestBody ServiceVehicleDto serviceVehicle,
			BindingResult result) throws AnomalieException {
		if (result.hasErrors()) {
			throw new AnomalieException(result.getAllErrors().get(0).getDefaultMessage());
		}
		service.update(id, serviceVehicle);
		return ResponseEntity.ok("Service vehicle updated");
	}

	/**
	 * Create a Service vehicle
	 * 
	 * @param serviceVehicle : the new Service vehicle
	 * @return A confirmation message
	 */
	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody ServiceVehicleDto serviceVehicle, BindingResult result)
			throws AnomalieException {
		if (!service.create(serviceVehicle)) {
			throw new AnomalieException(result.getAllErrors().get(0).getDefaultMessage());
		}
		return ResponseEntity.ok("Service vehicle created");
	}

	/**
	 * Delete the Service vehicle corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return A confirmation message
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok("Suppression reussie");
	}

}
