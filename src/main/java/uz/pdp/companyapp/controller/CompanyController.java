package uz.pdp.companyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapp.entity.Company;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.payload.CompanyDTO;
import uz.pdp.companyapp.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/all")
    public HttpEntity<?> all(){

        return ResponseEntity.ok(companyService.getAll());
    }

    @PostMapping("/add")
    public HttpEntity<?> addCompany(@Valid @RequestBody CompanyDTO companyDTO){
        ApiResponse apiResponse = companyService.addCompany(companyDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);

    }

    @PutMapping("/api/edit/{id}")
    public HttpEntity<?>editCompany(@Valid @PathVariable Integer id,@RequestBody CompanyDTO companyDTO){
        ApiResponse apiResponse = companyService.editCompany(id, companyDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?201:404).body(apiResponse);
    }

    @DeleteMapping("/deleteMapping/{id}")
    public HttpEntity<?> deleteCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:400).body(apiResponse);
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
