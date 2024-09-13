package covoit.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**This class is for the models of vehicles
 * 
 */
@Entity
@Table(name = "MODEL")
public class VehicleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected String name;
	@JsonIgnore
@OneToMany(mappedBy = "model")
	private Set<Vehicle> vehicles = new HashSet<>();
	
	/** Constructor
	 * @param name
	 */
	public VehicleModel(String name) {
		this.name = name;
	}

	/** Constructor jpa
	 * 
	 */
	public VehicleModel() {
	}

	/** Getter pour name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**Setter pour name
	 * @param name name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter pour id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/** Getter pour vehicles
	 * @return vehicles
	 */
	public Set<Vehicle> getVehicles() {
		return vehicles;
	}

	/**Setter pour vehicles
	 * @param vehicles vehicles 
	 */
	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
		
}
