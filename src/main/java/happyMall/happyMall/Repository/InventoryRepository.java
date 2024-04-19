package happyMall.happyMall.Repository;

import happyMall.happyMall.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByItemName(String itemName);


    Inventory findInventoryById(Long id);
}
