package covoit.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private String userName;
    private String lastName;
    private String password;
    private String email;
    private boolean driverLicence;
    @ManyToMany
    @JoinTable(name = "user_carpool",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "carpool_id"))
    private Set<Carpool> carpools = new HashSet<>();
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
    public UserDetails asUserDetails() {
        return new User(userName, password, authorities);
    }


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
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", userName=" + userName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", driverLicence=" + driverLicence + ", authorities=" + authorities + "]";
	}
	public Set<Carpool> getCarpools() {
        return carpools;
    }

    public void setCarpools(Set<Carpool> carpools) {
        this.carpools = carpools;
    }

    // Method to add a carpool
    public void addCarpool(Carpool carpool) {
        this.carpools.add(carpool);
    }

    // Method to remove a carpool
    public void removeCarpool(Carpool carpool) {
        this.carpools.remove(carpool);
    }



	

}