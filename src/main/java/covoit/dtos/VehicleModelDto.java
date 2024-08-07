package covoit.dtos;

import java.util.Set;

import covoit.entities.Vehicle;
import covoit.entities.VehicleModel;

public class VehicleModelDto {
	private int id;
	private String name;
	private Set<Vehicle> vehicles;
	
	public VehicleModelDto toDto(VehicleModel vm) {
		VehicleModelDto vhDto = new VehicleModelDto();
		vhDto.setId(vm.getId());
		vhDto.setName(vm.getName());
		vhDto.setVehicles(vm.getVehicles());
		return vhDto;
	}
	public VehicleModel toBean(VehicleModelDto vmDto) {
		VehicleModel vm = new VehicleModel();
		vm.setName(vmDto.getName());
		vm.setVehicles(vmDto.getVehicles());
		return vm;
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
	public Set<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}
