package ch.hslu.sofeng.campuspay_jpa.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampusPayTransactionRepository extends JpaRepository<CampusPayTransactionEntity, Long> {

    List<CampusPayTransactionEntity> findBySender_IdOrderByCreatedAtDesc(Long senderId);

    List<CampusPayTransactionEntity> findByRecipient_IdOrderByCreatedAtDesc(Long recipientId);
}