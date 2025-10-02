package brenno.AgredadorInvestimentos.Repository;

import brenno.AgredadorInvestimentos.Entity.Account;
import brenno.AgredadorInvestimentos.Entity.AccountStock;
import brenno.AgredadorInvestimentos.Entity.AccountStockId;
import brenno.AgredadorInvestimentos.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
    Optional<AccountStock> findByAccountAndStock(Account account, Stock stock);
}

