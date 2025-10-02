package brenno.AgredadorInvestimentos.Repository;

import brenno.AgredadorInvestimentos.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByAccount_AccountId(UUID accountId);
}
