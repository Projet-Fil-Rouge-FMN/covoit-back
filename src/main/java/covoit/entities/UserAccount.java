package covoit.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

/**
 * Represents a user account in the carpooling system.
 */
@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastName;
    private boolean driverLicence;
    private String password;
    @ManyToMany(mappedBy = "userAccounts")
    private List<Carpool> carpools;
    /**
     * Constructor
     * 
     * @param name
     * @param lastName
     * @param driverLicence
     * @param password
     */
    public UserAccount(String name, String lastName, boolean driverLicence, String password) {
	super();
	this.name = name;
	this.lastName = lastName;
	this.driverLicence = driverLicence;
	this.password = password;
    }

    /**
     * Constructor jpa
     * 
     */
    public UserAccount() {

    }

    /**
     * Gets the unique identifier of the user.
     * 
     * @return the unique identifier of the user
     */
    public int getId() {
	return id;
    }

    /**
     * Sets the unique identifier of the user.
     * 
     * @param id the unique identifier of the user
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Gets the first name of the user.
     * 
     * @return the first name of the user
     */
    public String getName() {
	return name;
    }

    /**
     * Sets the first name of the user.
     * 
     * @param name the first name of the user
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName the last name of the user
     */
    public String getLastName() {
	return lastName;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user
     */
    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * Obtient le mot de passe de l'utilisateur.
     * 
     * @return le mot de passe de l'utilisateur
     */
    public String getPassword() {
	return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     * 
     * @param password le mot de passe de l'utilisateur
     */
    public void setPassword(String password) {
	this.password = password;
    }

    public boolean isDriverLicence() {
	return driverLicence;
    }

    public void setDriverLicence(boolean driverLicence) {
	this.driverLicence = driverLicence;
    }

    public List<Carpool> getCarpools() {
	return carpools;
    }

    public void setCarpools(List<Carpool> carpools) {
	this.carpools = carpools;
    }

}
