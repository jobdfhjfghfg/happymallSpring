package happyMall.happyMall.Repository;

import happyMall.happyMall.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String name;

    private OrderStatus orderStatus;
}
