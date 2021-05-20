package uz.pdp.companyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyapp.entity.Address;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    /**
     * get All Adress
     * @return
     */
    public List<Address> getAll(){
        return addressRepository.findAll();
    }

    /**
     * get Adress bu Adress
     * @param id
     * @return
     */

    public Address getOne(Integer id){
        Optional<Address> byId = addressRepository.findById(id);
        return byId.orElse(null);
    }

    /**
     * Delete Adress by Id
     * @param id
     * @return
     */
    public ApiResponse deleteAddress(Integer id){
        Optional<Address> byId = addressRepository.findById(id);
        if (byId.isPresent()){
            addressRepository.deleteById(id);
            return new ApiResponse("Deleted successfully",true);
        }
        return new ApiResponse("id not fount",false);
    }

    /**
     * Adding new Address
     * @param address
     * @return
     */
    public ApiResponse AddAdress(Address address){
        Address addingAddress = new Address();
        boolean checkHN = addressRepository.existsByHomeNumberAndStreet(address.getHomeNumber(), address.getStreet());
        if (checkHN){
            return new ApiResponse("This HomeNUmber and Street already exist",false);
        }

        addingAddress.setHomeNumber(address.getHomeNumber());

        addingAddress.setStreet(address.getStreet());

        addressRepository.save(addingAddress);
        return new ApiResponse("successfully Added",true);

    }

    /**
     * editing Adress by id
     * @param id
     * @param address
     * @return
     */
    public ApiResponse editAdress(Integer id, Address address){
        Optional<Address> byId = addressRepository.findById(id);
        if (byId.isPresent()){
            boolean check = addressRepository.existsByHomeNumberAndStreet(address.getHomeNumber(), address.getStreet());
            if (check){
                return new ApiResponse("This HomeNUmber and Street already exist",false);
            }
            Address editingAdress = byId.get();
            editingAdress.setHomeNumber(address.getHomeNumber());
            editingAdress.setStreet(address.getStreet());
            addressRepository.save(editingAdress);
            return new ApiResponse("edited successfully",false);

        }

        return new ApiResponse("bu id topilmadi",false);
    }
}
