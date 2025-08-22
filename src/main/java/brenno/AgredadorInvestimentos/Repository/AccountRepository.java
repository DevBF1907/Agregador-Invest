package brenno.AgredadorInvestimentos.Repository;

import brenno.AgredadorInvestimentos.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account,UUID> {}
