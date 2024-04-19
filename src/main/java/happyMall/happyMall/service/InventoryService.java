package happyMall.happyMall.service;

import happyMall.happyMall.Repository.InventoryRepository;
import happyMall.happyMall.domain.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

//    저장
    @Transactional
    public Long save(Inventory inventory) {

        inventoryRepository.save(inventory);
        return inventory.getId();
    }


    public Inventory findOne(Long inventoryID) {
        return inventoryRepository.findById(inventoryID).orElse(null);
    }

    public String findByItemName(String itemName) {
        Inventory inventory = inventoryRepository.findByItemName(itemName);
        return inventory != null ? inventory.getItemName() : null;
    }

    public Integer findByPrice(String itemName) {
        Inventory inventory = inventoryRepository.findByItemName(itemName);
        return inventory != null ? inventory.getPrice() : 0;
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

}
