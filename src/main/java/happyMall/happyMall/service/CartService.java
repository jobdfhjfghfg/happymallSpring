package happyMall.happyMall.service;

import happyMall.happyMall.Repository.CartInventoryRepository;
import happyMall.happyMall.Repository.CartRepository;
import happyMall.happyMall.Repository.InventoryRepository;
import happyMall.happyMall.domain.Cart;
import happyMall.happyMall.domain.CartItem;
import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final CartInventoryRepository cartInventoryRepository;
    private final MemberService memberService;

    @Transactional
    public void addCart(Member member, Inventory newItem, int count) {

        Member member1 = memberService.findOne(member.getId());
        Cart cart = cartRepository.findByMember(member1);
//        장바구니 비어있을때
        if (cart == null) {
            cart = Cart.createCart(member1);
            cartRepository.save(cart);
        }

        Inventory inventory = inventoryRepository.findInventoryById(newItem.getId());
        CartItem cartItem = cartInventoryRepository.findByCartIdAndInventoryId(cart.getId(), inventory.getId());

        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, inventory, count);
            cartInventoryRepository.save(cartItem);
        } else {
            CartItem updateCartItem = cartItem;
            updateCartItem.setCart(cartItem.getCart());
            updateCartItem.setInventory(cartItem.getInventory());
            updateCartItem.setCount(cart.getCount() + count);
            updateCartItem.setPrice(inventory.getPrice() * (cart.getCount() + count));

            cartInventoryRepository.save(updateCartItem);
        }

        cart.setCount(cart.getCount() + count);
    }

    @Transactional
    public void deleteCartItem(CartItem cartItem) {

        cartInventoryRepository.delete(cartItem);
    }

    @Transactional
    public Long save(CartItem cartItem) {
        cartInventoryRepository.save(cartItem);
        return cartItem.getId();
    }

    @Transactional
    public Long saveCart(Cart cart) {
        cartRepository.save(cart);
        return cart.getId();
    }

    public List<CartItem> userCart(Cart cartMember) {
        return cartInventoryRepository.findAll();
    }

    public CartItem findOne(Long cartId) {
        return cartInventoryRepository.findById(cartId).orElse(null);
    }
}
