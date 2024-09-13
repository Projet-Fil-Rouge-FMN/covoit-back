package covoit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**This class is for the routes
 * 
 */
@Entity
@Table(name = "ROUTE")
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected double duration;
	protected double kmTotal;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_START_ADDRESS")
	protected Address startAddress;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_END_ADDRESS")
	protected Address endAddress;
	
	/** Constructor
	 * @param duration
	 * @param kmTotal
	 * @param startAddress
	 * @param endAddress
	 */
	public Route(double duration, double kmTotal, Address startAddress, Address endAddress) {
		this.duration = duration;
		this.kmTotal = kmTotal;
		this.startAddress = startAddress;
		this.endAddress = endAddress;
	}

	/** Constructor jpa
	 * 
	 */
	public Route() {
	}

	/** Getter pour duration
	 * @return duration
	 */
	public double getDuration() {
		return duration;
	}

	/**Setter pour duration
	 * @param duration duration 
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/** Getter pour kmTotal
	 * @return kmTotal
	 */
	public double getKmTotal() {
		return kmTotal;
	}

	/**Setter pour kmTotal
	 * @param kmTotal kmTotal 
	 */
	public void setKmTotal(double kmTotal) {
		this.kmTotal = kmTotal;
	}

	/** Getter pour startAddress
	 * @return startAddress
	 */
	public Address getStartAddress() {
		return startAddress;
	}

	/**Setter pour startAddress
	 * @param startAddress startAddress 
	 */
	public void setStartAddress(Address startAddress) {
		this.startAddress = startAddress;
	}

	/** Getter pour endAddress
	 * @return endAddress
	 */
	public Address getEndAddress() {
		return endAddress;
	}

	/**Setter pour endAddress
	 * @param endAddress endAddress 
	 */
	public void setEndAddress(Address endAddress) {
		this.endAddress = endAddress;
	}

	/** Getter pour id
	 * @return id
	 */
	public int getId() {
		return id;
	}	
}
