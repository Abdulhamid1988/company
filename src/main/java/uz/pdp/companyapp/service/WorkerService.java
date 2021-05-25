package uz.pdp.companyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyapp.entity.Address;
import uz.pdp.companyapp.entity.Department;
import uz.pdp.companyapp.entity.Worker;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.payload.WorkerDTO;
import uz.pdp.companyapp.repository.AddressRepository;
import uz.pdp.companyapp.repository.DepartmentRepository;
import uz.pdp.companyapp.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    public List<Worker> getAll(){
        return workerRepository.findAll();
    }

    public Worker  getOne(Integer id){
        Optional<Worker> byId = workerRepository.findById(id);
        return byId.orElse(null);
    }

    public ApiResponse addWorker(WorkerDTO workerDTO){

        Worker worker = new Worker();
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDTO.getPhoneNumber());
        if (existsByPhoneNumber){
            return new ApiResponse("this phone number has already exist",false);
        }
        worker.setPhoneNumber(workerDTO.getPhoneNumber());
        Address address = new Address();

        boolean b = addressRepository.existsByHomeNumberAndStreet(workerDTO.getHomeNumber(), workerDTO.getStreet());
        if (b){
            return new ApiResponse("bu address mavjud",false);
        }
        address.setStreet(workerDTO.getStreet());
        address.setHomeNumber(workerDTO.getHomeNumber());

        Optional<Department> byId = departmentRepository.findById(workerDTO.getDepartment_id());
        if (byId.isPresent()){
            Department department = byId.get();
            worker.setDepartment(department);
        }else {
            return new ApiResponse("bu department id topilmadi",false);
        }
        addressRepository.save(address);
        worker.setAddress(address);
        workerRepository.save(worker);
        return new ApiResponse("Success",true);
    }

    public ApiResponse edit(Integer id,WorkerDTO workerDTO){
        Optional<Worker> byId = workerRepository.findById(id);
        if (byId.isPresent()){
            Worker worker = byId.get();

            boolean byPhoneNumber = workerRepository.existsByPhoneNumber(workerDTO.getPhoneNumber());

            if (byPhoneNumber){
                return new ApiResponse("bu raqam band",false);
            }
            worker.setPhoneNumber(workerDTO.getPhoneNumber());

            Optional<Address> address = addressRepository.findById(worker.getAddress().getId());


            if (address.isPresent()){
                Address editingAdress = address.get();
                boolean b = addressRepository.existsByHomeNumberAndStreet(workerDTO.getHomeNumber(), workerDTO.getStreet());
                if (b){
                    return new ApiResponse("Bu address mavjud ",false);
                }
                editingAdress.setHomeNumber(workerDTO.getHomeNumber());
                editingAdress.setStreet(workerDTO.getStreet());
                addressRepository.save(editingAdress);
                worker.setAddress(editingAdress);
            }else {
                return new ApiResponse("id not fount",false);
            }
            Optional<Department> departmentRepositoryById = departmentRepository.findById(workerDTO.getDepartment_id());
            if (departmentRepositoryById.isPresent()){

                Department department = departmentRepositoryById.get();

                worker.setDepartment(department);
            }else {
                return new ApiResponse("id not fount bro",false);
            }
            workerRepository.save(worker);
            return new ApiResponse("edited successfully",true);

        }
        return new ApiResponse("id not fount bro",false);
    }

    public ApiResponse delete(Integer id){
        Optional<Worker> byId = workerRepository.findById(id);
        if (byId.isPresent()){
            addressRepository.deleteById(byId.get().getAddress().getId());
            workerRepository.deleteById(id);
            return new ApiResponse("Deleted successfully",true);
        }
        return new ApiResponse("id not fount",false);
    }
}
