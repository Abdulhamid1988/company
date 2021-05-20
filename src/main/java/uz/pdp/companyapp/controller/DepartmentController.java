package uz.pdp.companyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.payload.DepartmentDTO;
import uz.pdp.companyapp.service.DepartmentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/all")
    public HttpEntity<?> all(){
        return ResponseEntity.ok(departmentService.getAll());
    }

    @PostMapping("/add")
    public HttpEntity<?> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?201:404).body(apiResponse);
    }


    @PutMapping("/edit")
    public HttpEntity<?> editDepartment(@PathVariable Integer id, @RequestBody DepartmentDTO departmentDTO){
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = departmentService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?HttpStatus.ACCEPTED : HttpStatus.BAD_GATEWAY).body(delete);
    }





    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
