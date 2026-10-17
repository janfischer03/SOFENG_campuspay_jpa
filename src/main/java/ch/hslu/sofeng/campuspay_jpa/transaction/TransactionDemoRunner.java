package ch.hslu.sofeng.campuspay_jpa.transaction;

import ch.hslu.sofeng.campuspay_jpa.customer.CampusPayCustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")
@Order(3)
public class TransactionDemoRunner implements CommandLineRunner {

    private final CampusPayCustomerService customers;
    private final CampusPayTransactionService transactions;

    public TransactionDemoRunner(CampusPayCustomerService customers, CampusPayTransactionService transactions) {
        this.customers = customers;
        this.transactions = transactions;
    }

    @Override
    public void run(String... args) {
        var hans = customers.findByEmail("hans@muster.ch");
        var eva = customers.findByEmail("eva@muster.ch");
        var oliver = customers.findByEmail("oliver@muster.ch");

        System.out.println(transactions.create(hans.getId(), eva.getId(), new BigDecimal("12.50"), "Mittagessen"));
        System.out.println(transactions.create(eva.getId(), hans.getId(), new BigDecimal("4.20"), "Kaffee"));

        try {
            transactions.create(hans.getId(), oliver.getId(), new BigDecimal("5.00"), "Soll abgelehnt werden");
        } catch (IllegalArgumentException ex) {
            System.out.println("Erwartete Ablehnung: " + ex.getMessage());
        }

        System.out.println(transactions.findOutgoing(hans.getId()));
    }
}