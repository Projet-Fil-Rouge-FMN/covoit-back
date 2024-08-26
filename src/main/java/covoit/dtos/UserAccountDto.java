package covoit.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import covoit.entities.UserAccount;

/**
 * Data Transfer Object for User.
 */
public class UserAccountDto {

	private int id;
	private String userName;
	private String lastName;
	private String email;
	private boolean driverLicence;
	private String password;
	private List<CarpoolDto> carpools = new ArrayList<>();
	private String role;

	
	public UserAccountDto toDto(UserAccount user) {
		UserAccountDto userDto = new UserAccountDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setUserName(user.getUserName());
		userDto.setLastName(user.getLastName());
		userDto.setDriverLicence(user.isDriverLicence());
		userDto.setPassword(user.getPassword());
		userDto.setRole(user.getAuthorities().toString());
		userDto.setCarpools(carpools);
		return userDto;
	}
	public UserAccount toBean(UserAccountDto userDto) {
		UserAccount user = new UserAccount();
		user.setEmail(userDto.getEmail());
		user.setUserName(userDto.getUserName());
		user.setLastName(userDto.getLastName());
		user.setDriverLicence(userDto.isDriverLicence());
		user.setPassword(userDto.getPassword());
		user.addAuthorities(userDto.getRole());
		return user;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	 * Gets the userName of the user.
	 * 
	 * @return the userName of the user
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the userName of the user.
	 * 
	 * @param userName the username of the user
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the password of the user.
	 * 
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 * 
	 * @param password the password of the user
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

	public List<CarpoolDto> getCarpools() {
		return carpools;
	}

	public void setCarpools(List<CarpoolDto> carpools) {
		this.carpools = carpools;
	}



	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserAccountDto [id=" + id + ", userName=" + userName + ", lastName=" + lastName + ", email=" + email
				+ ", driverLicence=" + driverLicence + ", password=" + password + ", carpools=" + carpools + ", role="
				+ role + "]";
	}


}
