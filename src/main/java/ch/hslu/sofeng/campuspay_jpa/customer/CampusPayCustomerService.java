package ch.hslu.sofeng.campuspay_jpa.customer;

import ch.hslu.sofeng.campuspay_jpa.common.exception.DuplicateResourceException;
import ch.hslu.sofeng.campuspay_jpa.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CampusPayCustomerService {

    private final CampusPayCustomerRepository repository;
    private final CampusPayCustomerMapper mapper;

    public CampusPayCustomerService(CampusPayCustomerRepository repository, CampusPayCustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CampusPayCustomerEntity> findAll() {
        return repository.findAll();
    }

    public CampusPayCustomerEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Kunde fehlt: " + id));
    }

    public CampusPayCustomerEntity findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("E-Mail fehlt: " + email));
    }

    public List<CampusPayCustomerEntity> findByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    public List<CampusPayCustomerEntity> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    public List<CampusPayCustomerEntity> searchNative(String pattern) {
        return repository.searchNative(pattern);
    }

    @Transactional
    public CampusPayCustomerEntity create(CampusPayCustomerEntity request) {
        validate(request);
        if (request.getId() != null) {
            throw new IllegalArgumentException("Neue Kunden haben keine ID.");
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("E-Mail bereits vorhanden.");
        }
        return repository.save(request);
    }

    @Transactional
    public CampusPayCustomerEntity update(Long id, CampusPayCustomerEntity request) {
        validate(request);
        CampusPayCustomerEntity customer = findById(id);

        if (!customer.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("E-Mail bereits vorhanden.");
        }

        mapper.updateEntity(customer, request);
        return repository.save(customer);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private void validate(CampusPayCustomerEntity customer) {
        if (customer == null
                || customer.getFirstName() == null || customer.getFirstName().isBlank()
                || customer.getLastName() == null || customer.getLastName().isBlank()
                || customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException("Pflichtfelder dürfen nicht leer sein.");
        }
        if (customer.getStatus() != 0 && customer.getStatus() != 1) {
            throw new IllegalArgumentException("Status muss 0 oder 1 sein.");
        }
    }
}