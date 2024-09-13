package covoit.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**This class is for the service vehicles
 * 
 */
@Entity
@Table(name = "SERVICE_VEHICLE")
public class ServiceVehicle extends Vehicle{
	protected String state;
	protected String picture;
	protected String motorization;
	protected double co2Km;
	@JsonIgnore
	@OneToMany(mappedBy = "serviceVehicle")
	private Set<Booking> bookings = new HashSet<>();
	
	/** Constructor
	 * @param registration
	 * @param nbSeat
	 * @param brand
	 * @param model
	 * @param category
	 * @param state
	 * @param picture
	 * @param motorization
	 * @param co2Km
	 */
	public ServiceVehicle(String registration, int nbSeat, Brand brand, VehicleModel model, Category category, String state,
			String picture, String motorization, double co2Km) {
		super(registration, nbSeat, brand, model, category);
		this.state = state;
		this.picture = picture;
		this.motorization = motorization;
		this.co2Km = co2Km;
	}

	/** Constructor jpa
	 * 
	 */
	public ServiceVehicle() {
	}

	/** Getter pour state
	 * @return state
	 */
	public String getState() {
		return state;
	}

	/**Setter pour state
	 * @param state state 
	 */
	public void setState(String state) {
		this.state = state;
	}

	/** Getter pour picture
	 * @return picture
	 */
	public String getPicture() {
		return picture;
	}

	/**Setter pour picture
	 * @param picture picture 
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/** Getter pour motorization
	 * @return motorization
	 */
	public String getMotorization() {
		return motorization;
	}

	/**Setter pour motorization
	 * @param motorization motorization 
	 */
	public void setMotorization(String motorization) {
		this.motorization = motorization;
	}

	/** Getter pour co2Km
	 * @return co2Km
	 */
	public double getCo2Km() {
		return co2Km;
	}

	/**Setter pour co2Km
	 * @param co2Km co2Km 
	 */
	public void setCo2Km(double co2Km) {
		this.co2Km = co2Km;
	}

	/** Getter pour bookings
	 * @return bookings
	 */
	public Set<Booking> getBookings() {
		return bookings;
	}

	/**Setter pour bookings
	 * @param bookings bookings 
	 */
	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
	
}
