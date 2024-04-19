package happyMall.happyMall.controller;

import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.exception.DiffPriceException;
import happyMall.happyMall.form.InventoryForm;
import happyMall.happyMall.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

//    상품 조회
    @GetMapping("/resister")
    private String item(Model model) {
        model.addAttribute("itemForm", new InventoryForm());
        return "inventory/resisterItem";
    }

//    상품 등록
    @PostMapping("/resister")
    public String save(InventoryForm form, Model model) {
//        그 전에 등록된 동일 이름의 상품이 있을때 거기에 추가되게끔 작업
        List<Inventory> inventories = inventoryService.findAll();
        boolean found = false;
        boolean diffPrice = false;
        for (Inventory inventory : inventories) {
            String itemName = inventory.getItemName();
            if (itemName != null && itemName.equals(form.getItemName())) {
                if (inventory.getPrice() == form.getPrice()) {
                    // 가격이 같은 경우 재고 수량 업데이트
                    int newStockQuantity = inventory.getStockQuantity() + form.getStockQuantity();
                    inventory.setStockQuantity(newStockQuantity);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = LocalDateTime.now().format(formatter);
                    inventory.setInventoryInDate(formattedDateTime);
                    inventoryService.save(inventory);
                    found = true;
                    break;
                } else {
                    diffPrice = true;
                    throw new DiffPriceException("오류");
                }
            }
        }


        if(!diffPrice && !found) {
            Inventory inventory1 = new Inventory();
            inventory1.setItemName(form.getItemName());
            inventory1.setPrice(form.getPrice());
            inventory1.setStockQuantity(form.getStockQuantity());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            inventory1.setInventoryInDate(formattedDateTime);

            inventoryService.save(inventory1);
        }
        return "redirect:/";
    }

//   (관리자) 상품 목록 조회
    @GetMapping("/itemList")
    public String itemList(Model model) {
        List<Inventory> items = inventoryService.findAll();

        model.addAttribute("items", items);
        return "inventory/itemList";
    }


//   (관리자) 상품 수정 페이지
    @GetMapping("/itemList/{itemId}/edit")
    public String updateList(@PathVariable("itemId") Long itemId, Model model) {

        Inventory item = inventoryService.findOne(itemId);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setItemName(item.getItemName());
        inventoryForm.setPrice(item.getPrice());
        inventoryForm.setStockQuantity(item.getStockQuantity());
        model.addAttribute("inventoryForm", inventoryForm);

        return "inventory/editItem";
    }

//   (관리자) 상품 수정
    @PostMapping("/itemList/{itemId}/edit")
    public String updateItem(@ModelAttribute("inventoryForm") InventoryForm inventoryForm, @PathVariable Long itemId) {

        Inventory item = inventoryService.findOne(itemId);

        Inventory inventory = item;
        inventory.setItemName(inventoryForm.getItemName());
        inventory.setPrice(inventoryForm.getPrice());
        inventory.setStockQuantity(inventory.getStockQuantity());
        inventoryService.save(inventory);

        return "redirect:/itemList";
    }
}
