package happyMall.happyMall.controller;

import happyMall.happyMall.Repository.InventoryRepository;
import happyMall.happyMall.Repository.MemberRepository;
import happyMall.happyMall.Repository.OrderSearch;
import happyMall.happyMall.Repository.ProductOrderRepository;
import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.domain.Member;
import happyMall.happyMall.domain.ProductOrder;
import happyMall.happyMall.form.OrderForm;
import happyMall.happyMall.service.InventoryService;
import happyMall.happyMall.service.MemberService;
import happyMall.happyMall.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductOrderController {

    private final MemberService memberService;
    private final InventoryService inventoryService;
    private final ProductOrderService productOrderService;

//    (관리자) 주문 페이지
    @GetMapping("/orderAdmin")
    public String orderForm(Model model) {

        List<Member> members = memberService.findAll();
        List<Inventory> inventories = inventoryService.findAll();

        model.addAttribute("members", members);
        model.addAttribute("items", inventories);
        model.addAttribute("orderForm", new OrderForm());
        return "order/orderAdmin";
    }

    @PostMapping("/orderAdmin")
    public String order(@RequestParam Long memberId,
                        @RequestParam Long itemId,
                        @RequestParam int quantity) {
                    productOrderService.order(memberId, itemId, quantity);
                    return "redirect:/orderAdmin";
    }

    @GetMapping("/orderAdminList")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<ProductOrder> productOrders = productOrderService.findAllOrdersByStatusAndMemberName(orderSearch);

        model.addAttribute("order", productOrders);

        return "order/adminOrderList";
    }
}
