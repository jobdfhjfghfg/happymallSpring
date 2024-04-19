package happyMall.happyMall.domain;

import happyMall.happyMall.service.InventoryService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int count;

    private int price;


    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setCount(0);
        cart.setMember(member);
        return cart;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.setCart(null); // 이전에 설정된 Member의 Cart 속성을 null로 설정하여 관계 제거
        }
        this.member = member; // 새로운 Member를 설정
        if (member != null) {
            member.setCart(this); // 새로 설정된 Member의 Cart 속성을 현재 Cart로 설정하여 관계 설정
        }
    }

    public void removeItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }
}
