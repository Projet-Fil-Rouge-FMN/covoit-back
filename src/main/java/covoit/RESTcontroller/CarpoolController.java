package covoit.RESTcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import covoit.dtos.CarpoolDTO;
import covoit.services.CarpoolService;

@RestController
@RequestMapping("/carpool")
public class CarpoolController {

    @Autowired
    private CarpoolService carpoolService;

    @PostMapping("/create")
    public void createCarpool(@RequestBody CarpoolDTO carpoolDTO) {
        carpoolService.createCarpool(carpoolDTO);
    }

    @PutMapping("/update/{id}")
    public void updateCarpool(@PathVariable int id, @RequestBody CarpoolDTO carpoolDTO) {
        carpoolService.updateCarpool(id, carpoolDTO);
    }

    @GetMapping("/{id}")
    public CarpoolDTO getCarpoolById(@PathVariable int id) {
        return carpoolService.getCarpoolById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCarpool(@PathVariable int id) {
        carpoolService.deleteCarpool(id);
    }

    @GetMapping("/all")
    public List<CarpoolDTO> getAllCarpools() {
        return carpoolService.getAllCarpools();
    }
}