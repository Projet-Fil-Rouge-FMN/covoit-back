package covoit.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**This class is for the addresses
 * 
 */
@Entity
@Table(name = "ADDRESS")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected String detail;
	protected String city;
	protected String country;
	@OneToMany(mappedBy = "ADDRESS")
	private Set<Route> route = new HashSet<>();

	/**
	 * Constructor
	 * 
	 * @param detail
	 * @param city
	 * @param country
	 */
	public Address(String detail, String city, String country) {
		this.detail = detail;
		this.city = city;
		this.country = country;
	}

	/**
	 * Constructor jpa
	 * 
	 */
	public Address() {
	}

	/**
	 * Getter pour detail
	 * 
	 * @return detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Setter pour detail
	 * 
	 * @param detail detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * Getter pour city
	 * 
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Setter pour city
	 * 
	 * @param city city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Getter pour country
	 * 
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Setter pour country
	 * 
	 * @param country country
	 */
	public void setCountry(String country) {
		this.country = country;
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
