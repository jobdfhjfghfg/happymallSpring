package happyMall.happyMall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@Slf4j
@ToString
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "orderItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productOrder_id")
    private ProductOrder productOrder;

    private int orderPrice;

    private int orderQuantity;

    public static OrderItem createOrderItem(Inventory inventory, int orderPrice, int orderQuantity) {

        OrderItem orderItem = new OrderItem();
        orderItem.setInventory(inventory);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setOrderQuantity(orderQuantity);

        inventory.removeStock(orderQuantity);
        return orderItem;
    }

    //    취소
    public void cancel() {
        getInventory().addStock(orderQuantity);
    }

    //    전체주문가격 조회
    public int getTotalPrice() {
        return getOrderQuantity() * getOrderPrice();
    }
}
