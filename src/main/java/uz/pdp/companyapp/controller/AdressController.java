package uz.pdp.companyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapp.entity.Address;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/adress")
public class AdressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAll(){
        return ResponseEntity.ok(addressService.getAll());
    }

    @PostMapping("/addAddress")
    public HttpEntity<?>addAdress(@Valid @RequestBody Address address){
        ApiResponse apiResponse = addressService.AddAdress(address);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);
    }


    @PutMapping("/editAdress/{id}")
    public HttpEntity<?>editAddress(@Valid @PathVariable Integer id, @RequestBody Address address){
        ApiResponse apiResponse = addressService.editAdress(id, address);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }


    @DeleteMapping("/delete/{id}")
    public HttpEntity<?>delete(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
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
