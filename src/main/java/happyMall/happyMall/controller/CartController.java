package happyMall.happyMall.controller;

import happyMall.happyMall.domain.Cart;
import happyMall.happyMall.domain.CartItem;
import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.domain.Member;
import happyMall.happyMall.form.CartForm;
import happyMall.happyMall.service.CartService;
import happyMall.happyMall.service.InventoryService;
import happyMall.happyMall.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;
    private final InventoryService inventoryService;

//    장바구니 페이지
    @GetMapping("/member/cart")
    public String userCartPage(Model model, HttpSession session) {

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/";
        }

        Long memberId = loginMember.getId();

        Member member = memberService.findOne(memberId);
        if (member.getCart() != null) {
            Cart cartMember = member.getCart();

            //            장바구니에서 모두 불러옴
            List<CartItem> cartItemList = cartService.userCart(cartMember);

//            총 가격
            int totalPrice = 0;
            for (CartItem cartItem1 : cartItemList) {
                int itemTotalPrice = cartItem1.getCount() * cartItem1.getInventory().getPrice();
                totalPrice += cartItem1.getCount() * cartItem1.getInventory().getPrice();
                cartItem1.setPrice(itemTotalPrice);
            }



            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", cartMember.getCount());
            model.addAttribute("cartItems", cartItemList);
            session.setAttribute("cartItems", cartItemList);
        }
        return "cart/userCart";
    }

//    장바구니 선택 상품 주문 버튼
    @PostMapping("/member/cart/order")
    public String orderSelectedItems(@RequestParam("selectedItems") List<Long> selectedItemsIds, Model model, HttpSession session) {

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/";
        }

        List<CartItem> selectedItems = new ArrayList<>();

        for (Long itemId : selectedItemsIds) {
            CartItem cartItem = cartService.findOne(itemId);
            selectedItems.add(cartItem);
        }

        model.addAttribute("selectedItems", selectedItems);
        session.setAttribute("selectedItems", selectedItems);

        return "redirect:/order/selectedProductOrderInfo";

    }

//    결제전 페이지
    @GetMapping("/order/selectedProductOrderInfo")
    public String showSelectedProductOrderInfo(Model model, HttpSession session) {

        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("selectedItems");

        model.addAttribute("selectedItems", cartItems);

        return "order/selectedProductOrderInfo";
    }

//    장바구니 수정 폼
    @GetMapping("/member/cart/{cartId}/edit")
    public String updateCartForm(@PathVariable("cartId") Long cartId, Model model) {
        CartItem cartItem = cartService.findOne(cartId);

        CartForm updateCart = new CartForm();

        updateCart.setItemName(cartItem.getItemName());
        updateCart.setCount(cartItem.getCount());

        model.addAttribute("updateCart", updateCart);

        return "cart/updateCart";
    }

//    장바구니 수정
    @PostMapping("/member/cart/{cartId}/edit")
    public String updateCart(@ModelAttribute CartForm cartForm, @PathVariable("cartId") Long cartId) {

        CartItem cartItem = cartService.findOne(cartId);

//        이전 수량 저장
        int previousCount = cartItem.getCount();

//        새로운 수량 수정
        cartItem.setItemName(cartForm.getItemName());
        cartItem.setCount(cartForm.getCount());

//        저장
        cartService.save(cartItem);

//        Cart 엔티티 업데이트
        Cart cart = cartItem.getCart();
        int updateCount = cart.getCount() - previousCount + cartForm.getCount();
        cart.setCount(updateCount);
        cartService.saveCart(cart);
        return "redirect:/member/cart";
    }

//    장바구니 삭제
    @PostMapping("/member/cart/{cartId}/delete")
    public String deleteCartItem(@PathVariable("cartId") Long cartId) {

        CartItem cartItem = cartService.findOne(cartId);

        cartService.deleteCartItem(cartItem);

        return "redirect:/member/cart";
    }
}
