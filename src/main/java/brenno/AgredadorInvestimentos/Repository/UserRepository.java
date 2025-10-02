package brenno.AgredadorInvestimentos.Repository;

import brenno.AgredadorInvestimentos.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Método para buscar usuário por email (usado na autenticação)
    Optional<User> findByEmail(String email);
}
