package ch.hslu.sofeng.campuspay_jpa.customer;

import org.springframework.stereotype.Component;

@Component
public class CampusPayCustomerMapper {

    public void updateEntity(CampusPayCustomerEntity target, CampusPayCustomerEntity source) {
        if (target == null || source == null) {
            throw new IllegalArgumentException("Quelle und Ziel sind erforderlich.");
        }
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setStatus(source.getStatus());
    }
}