package happyMall.happyMall.Repository;

import happyMall.happyMall.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartInventoryRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndInventoryId(Long id, Long id1);
}
