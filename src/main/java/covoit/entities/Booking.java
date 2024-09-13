package covoit.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * This class is for the booking
 * 
 */
@Entity
@Table(name = "BOOKING")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected LocalDateTime startDate;
	protected LocalDateTime endDate;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_SERVICE_VEHICLE")
	protected ServiceVehicle serviceVehicle;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_DRIVER")
	protected UserAccount driver;

	/**
	 * Constructor
	 * 
	 * @param startDate
	 * @param endDate
	 * @param serviceVehicle
	 * @param driver
	 */
	public Booking(LocalDateTime startDate, LocalDateTime endDate, ServiceVehicle serviceVehicle, UserAccount driver) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.serviceVehicle = serviceVehicle;
		this.driver = driver;
	}

	/**
	 * Constructor jpa
	 * 
	 */
	public Booking() {
	}

	/**
	 * Getter pour startDate
	 * 
	 * @return startDate
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * Setter pour startDate
	 * 
	 * @param startDate startDate
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter pour endDate
	 * 
	 * @return endDate
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * Setter pour endDate
	 * 
	 * @param endDate endDate
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getter pour serviceVehicle
	 * 
	 * @return serviceVehicle
	 */
	public ServiceVehicle getServiceVehicle() {
		return serviceVehicle;
	}

	/**
	 * Setter pour serviceVehicle
	 * 
	 * @param serviceVehicle serviceVehicle
	 */
	public void setServiceVehicle(ServiceVehicle serviceVehicle) {
		this.serviceVehicle = serviceVehicle;
	}

	/**
	 * Getter pour driver
	 * 
	 * @return driver
	 */
	public UserAccount getDriver() {
		return driver;
	}

	/**
	 * Setter pour driver
	 * 
	 * @param driver driver
	 */
	public void setDriver(UserAccount driver) {
		this.driver = driver;
	}

	/**
	 * Getter pour id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

}
