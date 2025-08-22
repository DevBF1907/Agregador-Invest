package brenno.AgredadorInvestimentos.Repository;

import brenno.AgredadorInvestimentos.Entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public  interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
