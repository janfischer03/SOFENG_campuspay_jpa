package ch.hslu.sofeng.campuspay_jpa.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Order(2) // Seeder mit Order(1) ist bereits abgeschlossen.
public class CampusPayDemoRunner implements CommandLineRunner {
    private final CampusPayCustomerService service;
    public CampusPayDemoRunner(CampusPayCustomerService service) {
        this.service = service;
    }
    @Override
    public void run(String... args) {
        var customers = service.findAll();
        customers.forEach(System.out::println);
        if (!customers.isEmpty()) {
            // Keine hart codierte ID: vorhandene ID verwenden.
            System.out.println(service.findById(customers.get(0).getId()));
        }
    }
}
