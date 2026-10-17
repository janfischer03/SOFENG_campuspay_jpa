package ch.hslu.sofeng.campuspay_jpa.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CampusPayCustomerRepository extends JpaRepository<CampusPayCustomerEntity, Long> {

    List<CampusPayCustomerEntity> findByFirstName(String firstName);

    Optional<CampusPayCustomerEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<CampusPayCustomerEntity> findByStatusOrderByLastNameAscFirstNameAsc(int status);

    @Query("""
        SELECT c FROM CampusPayCustomerEntity c 
        WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) 
        OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<CampusPayCustomerEntity> findByNameContaining(@Param("name") String name);

    @Query(value = """
        SELECT * FROM campuspay_customers 
        WHERE LOWER(first_name) LIKE LOWER(:pattern)
    """, nativeQuery = true)
    List<CampusPayCustomerEntity> searchNative(@Param("pattern") String pattern);
}