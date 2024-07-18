package covoit.services;

import java.util.List;

import covoit.dtos.CarpoolDTO;

public interface CarpoolService {

    void createCarpool(CarpoolDTO carpoolDTO);
    void updateCarpool(int id, CarpoolDTO carpoolDTO);
    CarpoolDTO getCarpoolById(int id);
    void deleteCarpool(int id);
    List<CarpoolDTO> getAllCarpools();
   
}
