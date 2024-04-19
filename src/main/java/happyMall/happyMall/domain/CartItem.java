package happyMall.happyMall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cartItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    private int count;

    private int price;

    private String itemName;

    public static CartItem createCartItem(Cart cart, Inventory inventory, int amount) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setInventory(inventory);
        cartItem.setCount(amount);
        cartItem.setPrice(inventory.getPrice());
        cartItem.setItemName(inventory.getItemName());
        return cartItem;
    }

//    카트에 같은거 담으면 수량 증가
    public void addCount(int count) {
        this.count += count;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        cart.getCartItems().add(this);
    }

}
