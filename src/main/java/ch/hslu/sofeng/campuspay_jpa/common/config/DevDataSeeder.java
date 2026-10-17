package ch.hslu.sofeng.campuspay_jpa.common.config;

import ch.hslu.sofeng.campuspay_jpa.customer.CampusPayCustomerEntity;
import ch.hslu.sofeng.campuspay_jpa.customer.CampusPayCustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@Profile("dev") // Nur registrieren, wenn das Profil dev gilt.
@Order(1) // Vor dem DemoRunner ausführen.
public class DevDataSeeder implements CommandLineRunner {
    private final CampusPayCustomerRepository repository;
    public DevDataSeeder(CampusPayCustomerRepository repository) {
        this.repository = repository;
    }
    @Override
    @Transactional // Alle Seed-Datensätze gemeinsam speichern.
    public void run(String... args) {
        // Einfacher Schutz gegen doppelte Startdaten bei Neustarts.
        // Mit ddl-auto=create ist die Tabelle nach jedem Start leer.
        if (repository.count() > 0) {
            System.out.println("Kunden vorhanden: Seeder übersprungen.");
            return;
        }
        var hans = new CampusPayCustomerEntity(
                "Hans", "Muster", "hans@muster.ch", 1);
        var eva = new CampusPayCustomerEntity(
                "Eva", "Muster", "eva@muster.ch", 1);
        var oliver = new CampusPayCustomerEntity(
                "Oliver", "Muster", "oliver@muster.ch", 0);
        repository.saveAll(List.of(hans, eva, oliver));
        System.out.println("3 CampusPay-Kunden gespeichert.");
    }
}
