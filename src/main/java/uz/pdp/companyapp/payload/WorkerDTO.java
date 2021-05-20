package uz.pdp.companyapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {

    private String phoneNumber;


    private String street;

    private String homeNumber;

    private Integer department_id;
}
