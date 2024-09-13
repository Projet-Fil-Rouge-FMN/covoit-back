package covoit.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**This class is for the vehicles
 * 
 */
@Entity
@Table(name = "VEHICLE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected String registration;
	protected int nbSeat;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_BRAND")
	protected Brand brand;
	@JsonIgnore
@ManyToOne
	@JoinColumn(name = "ID_MODEL")
	protected VehicleModel model;
	@JsonIgnore
@ManyToOne
	@JoinColumn(name = "ID_CATEGORY")
	protected Category category;
	@JsonIgnore
@ManyToMany
	@JoinTable(name="DRIVER_VEHICULE", joinColumns = { @JoinColumn(name = "id_vehicle") }, 
    inverseJoinColumns = { @JoinColumn(name = "id_driver") } )
	private Set<UserAccount> drivers = new HashSet<>();
	
	/** Constructor
	 * @param registration
	 * @param nbSeat
	 * @param brand
	 * @param model
	 * @param category
	 */
	public Vehicle(String registration, int nbSeat, Brand brand, VehicleModel model, Category category) {
		this.registration = registration;
		this.nbSeat = nbSeat;
		this.brand = brand;
		this.model = model;
		this.category = category;
	}

	/** Constructor jpa
	 * 
	 */
	public Vehicle() {
	}

	/** Getter pour registration
	 * @return registration
	 */
	public String getRegistration() {
		return registration;
	}

	/**Setter pour registration
	 * @param registration registration 
	 */
	public void setRegistration(String registration) {
		this.registration = registration;
	}

	/** Getter pour nbSeat
	 * @return nbSeat
	 */
	public int getNbSeat() {
		return nbSeat;
	}

	/**Setter pour nbSeat
	 * @param nbSeat nbSeat 
	 */
	public void setNbSeat(int nbSeat) {
		this.nbSeat = nbSeat;
	}

	/** Getter pour brand
	 * @return brand
	 */
	public Brand getBrand() {
		return brand;
	}

	/**Setter pour brand
	 * @param brand brand 
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/** Getter pour model
	 * @return model
	 */
	public VehicleModel getModel() {
		return model;
	}

	/**Setter pour model
	 * @param model model 
	 */
	public void setModel(VehicleModel model) {
		this.model = model;
	}

	/** Getter pour category
	 * @return category
	 */
	public Category getCategory() {
		return category;
	}

	/**Setter pour category
	 * @param category category 
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/** Getter pour id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/** Getter pour drivers
	 * @return drivers
	 */
	public Set<UserAccount> getDrivers() {
		return drivers;
	}

	/**Setter pour drivers
	 * @param drivers drivers 
	 */
	public void setDrivers(Set<UserAccount> drivers) {
		this.drivers = drivers;
	}
	
}
