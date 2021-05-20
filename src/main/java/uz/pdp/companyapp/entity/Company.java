package uz.pdp.companyapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotNull(message = "CorpName bo'sh bo'lmasligi kerak")
    private String corpName;

    @Column(unique = true)
    @NotNull(message = "DirectorName bo'sh bo'lmasligi kerak")
    private String directorName;

    @OneToOne
    private Address address;
}
