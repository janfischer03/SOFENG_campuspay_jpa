package ch.hslu.sofeng.campuspay_jpa.transaction;

import ch.hslu.sofeng.campuspay_jpa.customer.CampusPayCustomerEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "campuspay_transactions")
public class CampusPayTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private CampusPayCustomerEntity sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private CampusPayCustomerEntity recipient;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(length = 200)
    private String description;

    protected CampusPayTransactionEntity() {}

    public CampusPayTransactionEntity(CampusPayCustomerEntity sender, CampusPayCustomerEntity recipient, BigDecimal amount, String description) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.description = description;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public CampusPayCustomerEntity getSender() { return sender; }
    public CampusPayCustomerEntity getRecipient() { return recipient; }
    public BigDecimal getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", amount=" + amount +
                ", createdAt=" + createdAt + ", description='" + description + "'}";
    }
}