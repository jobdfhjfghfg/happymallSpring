package happyMall.happyMall.controller;

import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.domain.Member;
import happyMall.happyMall.exception.NotEnoughStockException;
import happyMall.happyMall.service.CartService;
import happyMall.happyMall.service.InventoryService;
import happyMall.happyMall.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final InventoryService inventoryService;
    private final MemberService memberService;
    private final CartService cartService;

    //    1번 제품상세페이지
    @GetMapping("/favProOne/{inventoryId}/info")
    public String favProOne(@PathVariable("inventoryId") Long inventoryId, Model model, HttpSession session) {

        Member memberSession = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<Member> memberList = memberService.findAll();
        Inventory inventory = inventoryService.findOne(inventoryId);
        List<Inventory> inventories = inventoryService.findAll();

        Long loginMember = null;
        for (Member member : memberList) {
            if (member.getId().equals(memberSession.getId())) {
                loginMember = member.getId();
                break;
            }
        }

        Inventory productOrder = null;
        for (Inventory inventoryItem : inventories) {
            if (inventoryItem.getItemName().equals("하루조이")) {
                productOrder = inventoryItem;
                break;
            }
        }

        if (productOrder != null) {
            model.addAttribute("productOrder", productOrder);
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("productOrderId", productOrder.getId());
            model.addAttribute("productOne", productOrder.getItemName());
            model.addAttribute("productOnePrice", productOrder.getPrice());
            model.addAttribute("productQuantity", productOrder.getStockQuantity());

            if (inventory.getStockQuantity() == 0) {
                model.addAttribute("outOfStockMessage", "이 상품은 현재 재고가 없습니다.");
            }
        }

        return "product/productOrder";
    }


    //    장바구니 버튼
    @PostMapping("/member/cart/{inventoryId}")
    public String addCartItem(@PathVariable("inventoryId")Long inventoryId, @RequestParam("count") int count, HttpSession session) {
        Member loggedInUser = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loggedInUser != null) {
            Inventory itemId = inventoryService.findOne(inventoryId);
            // 로그인한 사용자의 정보를 이용하여 카트에 상품을 추가합니다.
            cartService.addCart(loggedInUser, itemId, count);
            return "redirect:/member/cart";
        } else {
            // 로그인되지 않은 경우 처리
            return "redirect:/login"; // 로그인 페이지로 이동하거나 로그인을 요구하는 페이지로 리다이렉트합니다.
        }
    }

//    오더 상세페이지
    @GetMapping("/productOrder/{inventoryId}/pay")
    public String productOrderForm(@PathVariable Long inventoryId, Model model, HttpSession session) {
        List<Inventory> inventories = inventoryService.findAll();

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        } else {
            return "redirect:/login";
        }

        for (Inventory inventory : inventories) {
            if (inventory.getItemName().equals("하루조이")) {
                model.addAttribute("productOrder", inventories);
                model.addAttribute("inventoryId", inventoryId);
                model.addAttribute("productOne", inventory.getItemName());
                model.addAttribute("productOnePrice", inventory.getPrice());
                break;
            }
        }
        return "order/productOrderInfo";
    }


}
