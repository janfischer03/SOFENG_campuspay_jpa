package ch.hslu.sofeng.campuspay_jpa.transaction;

import ch.hslu.sofeng.campuspay_jpa.customer.CampusPayCustomerRepository;
import ch.hslu.sofeng.campuspay_jpa.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CampusPayTransactionService {

    private final CampusPayTransactionRepository transactions;
    private final CampusPayCustomerRepository customers;

    public CampusPayTransactionService(CampusPayTransactionRepository transactions, CampusPayCustomerRepository customers) {
        this.transactions = transactions;
        this.customers = customers;
    }

    @Transactional
    public CampusPayTransactionEntity create(Long senderId, Long recipientId, BigDecimal amount, String description) {
        if (senderId == null || recipientId == null || senderId.equals(recipientId)) {
            throw new IllegalArgumentException("Zwei unterschiedliche Kunden erforderlich.");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein.");
        }

        BigDecimal normalized;
        try {
            normalized = amount.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException("Maximal zwei relevante Nachkommastellen.");
        }
        if (normalized.precision() > 12) {
            throw new IllegalArgumentException("Betrag passt nicht in DECIMAL(12,2).");
        }
        if (description != null && description.length() > 200) {
            throw new IllegalArgumentException("Beschreibung ist zu lang.");
        }

        var sender = customers.findById(senderId).orElseThrow(
                () -> new ResourceNotFoundException("Sender fehlt: " + senderId));
        var recipient = customers.findById(recipientId).orElseThrow(
                () -> new ResourceNotFoundException("Empfaenger fehlt: " + recipientId));

        if (sender.getStatus() != 1 || recipient.getStatus() != 1) {
            throw new IllegalArgumentException("Beide Kunden muessen aktiv sein.");
        }

        return transactions.save(new CampusPayTransactionEntity(sender, recipient, normalized, description));
    }

    public List<CampusPayTransactionEntity> findOutgoing(Long customerId) {
        return transactions.findBySender_IdOrderByCreatedAtDesc(customerId);
    }
}