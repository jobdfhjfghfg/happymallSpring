package happyMall.happyMall.form;

import happyMall.happyMall.domain.Inventory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {

    private Long id;

    private Inventory inventory;

    private int quantity;
}
