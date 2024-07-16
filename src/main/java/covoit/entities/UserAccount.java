package covoit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Represents a user account in the carpooling system.
 */
@Entity
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String lastName;
	private boolean permis;
	private String password;
	
	public UserAccount() {
		
	}
    /**
     * Gets the unique identifier of the user.
     * 
     * @return the unique identifier of the user
     */
	public Long getId() {
		return id;
	}
    /**
     * Sets the unique identifier of the user.
     * 
     * @param id the unique identifier of the user
     */
	public void setId(Long id) {
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
     * Sets the password of the user.
     * 
     * @param password the password of the user
     */
	public boolean isPermis() {
		return permis;
	}

    /**
     * Sets the driving license status of the user.
     * 
     * @param permis true if the user has a driving license, false otherwise
     */
	public void setPermis(boolean permis) {
		this.permis = permis;
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

	

	

}
