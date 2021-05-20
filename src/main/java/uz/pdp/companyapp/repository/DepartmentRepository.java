package uz.pdp.companyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.companyapp.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    boolean existsByName(String name);

}
