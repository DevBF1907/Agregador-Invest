package brenno.AgredadorInvestimentos.Repository;


import brenno.AgredadorInvestimentos.Entity.Stock;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock,String> {
    @Repository
    interface UserRepository extends JpaRepository<User, UUID> {
    }
}
