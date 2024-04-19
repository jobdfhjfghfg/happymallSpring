package happyMall.happyMall.controller;

import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {

    private final InventoryService inventoryService;

//    자주 찾는 상품
    @GetMapping("/favProduct")
    public String favorityProduct(Model model) {
        List<Inventory> inventories = inventoryService.findAll();

        Inventory productOne = null;
        for (Inventory inventory : inventories) {
            if (inventory.getItemName().equals("하루조이")) {
                productOne = inventory;
                break;
            }
        }

        if (productOne != null) {
            model.addAttribute("favProOne", productOne);
            model.addAttribute("productOne", productOne.getItemName());
            model.addAttribute("productOnePrice", productOne.getPrice());
        }

        return "a-favority-product";
    }

    @GetMapping("/example")
    public String example() {
        return "example/example";
    }

//    한정 상품
    @GetMapping("/limited")
    public String limitSpecial() {
        return "limited-special";
    }

//    신상품
    @GetMapping("new-product")
    public String newProduct() {
        return "new-product";
    }

//    추천상품
    @GetMapping("/recommend")
    public String recommend() {
        return "recommend-product";
    }

//    베스트 리뷰
    @GetMapping("/best")
    public String bestReview() {
        return "bestreview";
    }
}
