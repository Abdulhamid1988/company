package uz.pdp.companyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapp.entity.Address;
import uz.pdp.companyapp.entity.Worker;
import uz.pdp.companyapp.payload.ApiResponse;
import uz.pdp.companyapp.payload.CompanyDTO;
import uz.pdp.companyapp.payload.WorkerDTO;
import uz.pdp.companyapp.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(workerService.getAll());
    }

    @PostMapping("/add")
    public HttpEntity<?> addWorker(@Valid @RequestBody WorkerDTO workerDTO){
        ApiResponse apiResponse = workerService.addWorker(workerDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);


    }

    @PutMapping("/api/edit/{id}")
    public HttpEntity<?>editWorker(@Valid @PathVariable Integer id,@RequestBody WorkerDTO workerDTO){
        ApiResponse apiResponse = workerService.edit(id, workerDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?201:404).body(apiResponse);
    }


    @DeleteMapping("/deleteMapping/{id}")
    public HttpEntity<?> deleteWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.delete(id);
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
