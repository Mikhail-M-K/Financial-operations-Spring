package repos;

import model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo  extends JpaRepository<Client, Long> {
}
