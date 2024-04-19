package happyMall.happyMall.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryForm {

    private Long id;

    private String itemName;

    private int price;

    private int stockQuantity;

    private LocalDateTime inventoryInDate;
}
