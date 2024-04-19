package happyMall.happyMall.domain;

import happyMall.happyMall.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Inventory {

    @Id
    @GeneratedValue
    @Column(name = "inventory_id")
    private Long id;

    private String itemName;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "inventory")
    private List<ProductOrder> productOrder = new ArrayList<>();

    @OneToMany(mappedBy = "inventory")
    private List<OrderItem> orderItem = new ArrayList<>();

    @OneToMany(mappedBy = "inventory")
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "inventory")
    private List<Cart> carts = new ArrayList<>();

    private String inventoryInDate;

    public void addStock(int quantity) {

        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {

        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }

        this.stockQuantity = restStock;
    }

}
