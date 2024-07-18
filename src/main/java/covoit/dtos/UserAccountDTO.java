package covoit.dtos;

import java.util.List;

public class UserAccountDTO {

    private int id;
    private String name;
    private String lastName;

    private boolean driverLicence;
    private String password;
    private List<CarpoolDTO> carpools;

    public List<CarpoolDTO> getCarpools() {
	return carpools;
    }

    public void setCarpools(List<CarpoolDTO> carpools) {
	this.carpools = carpools;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

 

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public boolean isDriverLicence() {
	return driverLicence;
    }

    public void setDriverLicence(boolean driverLicence) {
	this.driverLicence = driverLicence;
    }

    @Override
    public String toString() {
	return "UserAccountDto [name=" + name + ", lastName=" + lastName + ",  password="
		+ password + "]";
    }
}
