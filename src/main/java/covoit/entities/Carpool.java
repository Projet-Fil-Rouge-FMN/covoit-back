package covoit.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Represents a reservation in the carpooling system.
 */
@Entity
public class Carpool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int availableSeat;
    private LocalDate starDate;

    @OneToMany
    private Vehicle vehicle;
 


    @ManyToOne
    private Route route;
    
    @ManyToMany
    @JoinTable(name = "useraccount_carpoll", joinColumns = @JoinColumn(name = "id_carpool"), inverseJoinColumns = @JoinColumn(name = "id_userAccount"))
    private List<UserAccount> userAccounts = new ArrayList<>();

    public Carpool() {
	
    }



    /**
     * Gets the unique identifier of the reservation.
     * 
     * @return the unique identifier of the reservation
     */
    public int getId() {
	return id;
    }

    /**
     * Sets the unique identifier of the reservation.
     * 
     * @param id the unique identifier of the reservation
     */
    public void setId(int id) {
	this.id = id;
    }

    public int getAvailableSeat() {
	return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
	this.availableSeat = availableSeat;
    }

    /**
     * Gets the start date and time of the reservation.
     * 
     * @return the start date and time of the reservation
     */
    public LocalDate getStarDate() {
	return starDate;
    }

    /**
     * Sets the start date and time of the reservation.
     * 
     * @param startDateTime the start date and time of the reservation
     */
    public void setStarDate(LocalDate starDate) {
	this.starDate = starDate;
    }


    /**
     * @return
     */
    public Route getRoute() {
        return route;
    }

    /**
     * @param route
     */
    public void setRoute(Route route) {
        this.route = route;
    }
    /**
     * Sets the user who made the reservation.
     * 
     * @param user the user who made the reservation
     */
    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    /**
     * Gets the user who made the reservation.
     * 
     * @return the user who made the reservation
     */
    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }


    /**
     * Gets the vehicle associated with the reservation.
     * 
     * @return the vehicle associated with the reservation
     */
    public Vehicle getVehicule() {
	return vehicle;
    }

    /**
     * Sets the vehicle associated with the reservation.
     * 
     * @param vehicle the vehicle associated with the reservation
     */



    public void setVehicle(Vehicle mapToVehicleEntity) {
	this.vehicle = mapToVehicleEntity;
	
    }

}
