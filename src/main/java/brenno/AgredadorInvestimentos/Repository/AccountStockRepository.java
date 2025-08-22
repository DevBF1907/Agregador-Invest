package brenno.AgredadorInvestimentos.Repository;

import brenno.AgredadorInvestimentos.Entity.AccountStock;
import brenno.AgredadorInvestimentos.Entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> { }

