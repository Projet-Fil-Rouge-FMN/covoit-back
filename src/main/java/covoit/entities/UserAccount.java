package covoit.entities;

import java.util.List;

import jakarta.persistence.ElementCollection;


import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private int id;
    private String userName;
    private String lastName;
    private String password;
    private String email;
    private boolean driverLicence;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities= new ArrayList<>();
    
    public UserAccount(String userName,String lastName, String password, String role, String email, boolean driverLicence) {
        super();
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(role);
        this.authorities.add(roleAuthority);
        this.email = email;
        this.driverLicence = driverLicence;
    }
    public UserAccount() {}



    public void addAuthorities(String role) {
    	GrantedAuthority roleAuthority = new SimpleGrantedAuthority(role);
    	this.authorities.add(roleAuthority);
    }
    
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
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
	public String getUserName() {
		return userName;
	}
    /**
     * Sets the first name of the user.
     * 
     * @param name the first name of the user
     */
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserDetails asUserDetails() {
	    return User.builder()
	        .username(this.userName)
	        .password(this.password)
	        .roles(this.authorities.stream()
	            .map(auth -> auth.getAuthority())
	            .toArray(String[]::new))
	        .build();
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", userName=" + userName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", driverLicence=" + driverLicence + ", authorities=" + authorities + "]";
	}


	

}