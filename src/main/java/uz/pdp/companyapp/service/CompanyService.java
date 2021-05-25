package uz.pdp.companyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.companyapp.entity.Address;
import uz.pdp.companyapp.entity.Company;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.payload.CompanyDTO;
import uz.pdp.companyapp.repository.AddressRepository;
import uz.pdp.companyapp.repository.CompanyRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;


    /**
     * get All company
     * @return
     */
    public List<Company> getAll(){
        return companyRepository.findAll();
    }

    /**
     * get Adress bu Adress
     * @param id
     * @return
     */

    public Company getOne(Integer id){
        Optional<Company> byId = companyRepository.findById(id);
        return byId.orElse(null);
    }
    /**
     * Delete Company  by Id
     * @param id
     * @return
     */
    public ApiResponse deleteAddress(@PathVariable Integer id){
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()){
            addressRepository.deleteById(byId.get().getAddress().getId());
            companyRepository.deleteById(id);
            return new ApiResponse("Deleted successfully",true);
        }
        return new ApiResponse("id not fount",false);
    }

    public ApiResponse addCompany( @Valid  @RequestBody  CompanyDTO companyDTO){
        Company company = new Company();
        boolean checkName = companyRepository.existsByCorpName(companyDTO.getCorpName());
        if (checkName){
            return new ApiResponse("bu nomdagi companiya mavjud",false);
        }
        company.setCorpName(companyDTO.getCorpName());
        company.setDirectorName(companyDTO.getDirectorName());

        boolean check = addressRepository.existsByHomeNumberAndStreet(companyDTO.getStreet(), companyDTO.getHomeNumber());
        if (check){
            return new ApiResponse("bu ADRESS mavjud",false);
        }
        Address address = new Address();
        address.setStreet(companyDTO.getStreet());
        address.setHomeNumber(companyDTO.getHomeNumber());
        addressRepository.save(address);
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("added successfully",true);
    }

    public ApiResponse editCompany(@PathVariable Integer id,@Valid @RequestBody CompanyDTO companyDTO){
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()){
            Company company = byId.get();
            boolean checkName = companyRepository.existsByCorpName(companyDTO.getCorpName());
            if (checkName){
                return new ApiResponse("Bu Companiya DB da mavjud",false);
            }
            company.setCorpName(companyDTO.getCorpName());
            company.setDirectorName(companyDTO.getDirectorName());

            Optional<Address> addressOptional = addressRepository.findById(company.getAddress().getId());
            if (addressOptional.isPresent()){
                Address address = addressOptional.get();
                address.setStreet(companyDTO.getStreet());
                address.setHomeNumber(companyDTO.getHomeNumber());
                addressRepository.save(address);
                company.setAddress(address);
            }else {
                return new ApiResponse(" id not found",false);
            }

            companyRepository.save(company);
        }
        return new ApiResponse("bu id mavjud emas",false);
    }
}
