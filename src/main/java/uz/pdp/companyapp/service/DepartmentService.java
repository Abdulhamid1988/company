package uz.pdp.companyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyapp.entity.Company;
import uz.pdp.companyapp.entity.Department;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.payload.DepartmentDTO;
import uz.pdp.companyapp.repository.CompanyRepository;
import uz.pdp.companyapp.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;


    public List<Department> getAll(){
        return departmentRepository.findAll();
    }

    public Department getOneById(Integer id){
        Optional<Department> byId = departmentRepository.findById(id);
        return byId.orElse(null);
    }

    public ApiResponse addDepartment(DepartmentDTO departmentDTO){
        Department department = new Department();
        boolean existsByName = departmentRepository.existsByName(departmentDTO.getName());

        if (existsByName){
            return new ApiResponse("bu departmentName allaqachon mavjud",false);
        }

        department.setName(departmentDTO.getName());

        Optional<Company> byId = companyRepository.findById(departmentDTO.getCompany_id());
        if (byId.isPresent()){
            Company company = byId.get();
            department.setCompany(company);
        }else {
            return new ApiResponse("nu company mavjud emas",false);
        }

        departmentRepository.save(department);
        return new ApiResponse("added successfully",true);

    }

    public ApiResponse  editDepartment(Integer id,DepartmentDTO departmentDTO){
        Optional<Department> byId = departmentRepository.findById(id);
        if (byId.isPresent()){
            Department department = byId.get();

            boolean existsByName = departmentRepository.existsByName(departmentDTO.getName());

            if (existsByName){
                return new ApiResponse("bu Department bazada mavjud",false);
            }

            department.setName(departmentDTO.getName());
            Optional<Company> companyRepositoryById = companyRepository.findById(departmentDTO.getCompany_id());
            if (companyRepositoryById.isPresent()){
                Company company = companyRepositoryById.get();
                department.setCompany(company);
            }else {
                return new ApiResponse("Bu kompaniya mavjud emas",false);
            }

            departmentRepository.save(department);
            return new ApiResponse("edited successfully",true);


        }
        return new ApiResponse(" Department Id not fount",false);

    }

    public ApiResponse delete(Integer id){
        Optional<Department> byId = departmentRepository.findById(id);
        if (byId.isPresent()){
            departmentRepository.deleteById(id);
            return new ApiResponse("deleted successfully",true);
        }
        return new ApiResponse("Department id not fount",false);
    }


}
