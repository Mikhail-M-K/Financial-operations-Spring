package repos;

import model.CashOrder;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface CashOrderRepo extends JpaRepository<CashOrder, Long> {
}
