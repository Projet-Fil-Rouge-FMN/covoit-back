package covoit;

import covoit.dtos.AddressDto;
import covoit.entities.Address;
import covoit.exception.AnomalieException;
import covoit.repository.AddressRepository;
import covoit.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllAddresses() {
        List<Address> mockAddresses = new ArrayList<>();
        mockAddresses.add(new Address("Detail1", "City1", "Country1"));
        mockAddresses.add(new Address("Detail2", "City2", "Country2"));

        when(addressRepository.findAll()).thenReturn(mockAddresses);

        List<AddressDto> result = addressService.findAll();

        assertEquals(2, result.size());
        assertEquals("City1", result.get(0).getCity());
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Existing() {
        Address mockAddress = new Address("Detail", "City", "Country");
        when(addressRepository.findById(1)).thenReturn(mockAddress);

        AddressDto result = addressService.findById(1);

        assertNotNull(result);
        assertEquals("City", result.getCity());
        verify(addressRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(addressRepository.findById(1)).thenReturn(null);

        AddressDto result = addressService.findById(1);

        assertNull(result);
        verify(addressRepository, times(1)).findById(1);
    }

    @Test
    void testCreateAddress_Success() throws AnomalieException {
        AddressDto dto = new AddressDto();
        dto.setDetail("Detail");
        dto.setCity("City");
        dto.setCountry("Country");

        when(addressRepository.findByDetailAndCityAndCountry("Detail", "City", "Country")).thenReturn(null);

        boolean result = addressService.create(dto);

        assertTrue(result);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testCreateAddress_Duplicate() throws AnomalieException {
        Address mockAddress = new Address("Detail", "City", "Country");
        AddressDto dto = new AddressDto();
        dto.setDetail("Detail");
        dto.setCity("City");
        dto.setCountry("Country");

        when(addressRepository.findByDetailAndCityAndCountry("Detail", "City", "Country")).thenReturn(mockAddress);

        boolean result = addressService.create(dto);

        assertFalse(result);
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testCreateAddress_MissingFields() {
        AddressDto dto = new AddressDto();
        dto.setDetail("");  // Champ vide
        dto.setCity("City");
        dto.setCountry(""); // Champ vide

        // Test que l'exception est bien levée lorsque les champs sont manquants
        AnomalieException exception = assertThrows(AnomalieException.class, () -> addressService.create(dto));
        assertEquals("Les champs Detail et Country ne doivent pas être vides.", exception.getMessage());
    }

    @Test
    void testUpdateAddress_Success() throws AnomalieException {
        Address mockAddress = new Address("OldDetail", "OldCity", "OldCountry");
        when(addressRepository.findById(1)).thenReturn(mockAddress);

        AddressDto dto = new AddressDto();
        dto.setDetail("NewDetail");
        dto.setCity("NewCity");
        dto.setCountry("NewCountry");

        boolean result = addressService.update(1, dto);

        assertTrue(result);
        assertEquals("NewCity", mockAddress.getCity());
        verify(addressRepository, times(1)).save(mockAddress);
    }

    @Test
    void testUpdateAddress_NotFound() throws AnomalieException {
        when(addressRepository.findById(1)).thenReturn(null);

        AddressDto dto = new AddressDto();
        dto.setDetail("Detail");
        dto.setCity("City");
        dto.setCountry("Country");

        boolean result = addressService.update(1, dto);

        assertFalse(result);
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testUpdateAddress_MissingFields() {
        AddressDto dto = new AddressDto();
        dto.setDetail("");  // Champ vide
        dto.setCity("City");
        dto.setCountry(""); // Champ vide

        // Test que l'exception est bien levée lorsque les champs sont manquants
        AnomalieException exception = assertThrows(AnomalieException.class, () -> addressService.update(1, dto));
        assertEquals("Les champs Detail et Country ne doivent pas être vides.", exception.getMessage());
    }

    @Test
    void testDeleteAddress_Success() {
        Address mockAddress = new Address("Detail", "City", "Country");
        when(addressRepository.findById(1)).thenReturn(mockAddress);

        boolean result = addressService.delete(1);

        assertTrue(result);
        verify(addressRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteAddress_NotFound() {
        when(addressRepository.findById(1)).thenReturn(null);

        boolean result = addressService.delete(1);

        assertFalse(result);
        verify(addressRepository, never()).deleteById(1);
    }
}
