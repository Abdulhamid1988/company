package uz.pdp.companyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.companyapp.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    boolean existsByHomeNumberAndStreet(String homeNumber, String street);
}
