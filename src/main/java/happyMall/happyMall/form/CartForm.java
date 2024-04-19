package happyMall.happyMall.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartForm {

    private Long id;

    private String itemName;

    private int price;

    private int count;
}
