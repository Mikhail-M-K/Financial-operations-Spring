package repos;

import model.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccountRepo  extends JpaRepository <ClientAccount, Long> {
}
