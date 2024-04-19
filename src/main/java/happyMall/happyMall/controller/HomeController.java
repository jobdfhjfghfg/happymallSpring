package happyMall.happyMall.controller;

import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.domain.Member;
import happyMall.happyMall.domain.Role;
import happyMall.happyMall.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final InventoryService inventoryService;

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {

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

//        세션이 없으면 Home
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            // 세션이 없거나 로그인 상태가 아니면 로그인 전 홈페이지로 이동
            return "home";
        } else {
            // 로그인 상태인 경우
            Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER); // 세션에서 로그인한 회원 정보 가져오기
            model.addAttribute("loginMember", loginMember); // View에 로그인한 회원 정보 전달

            if (loginMember.getGrade() == Role.USER) {
                return "userLoginIndex"; // 사용자 페이지로 이동
            } else {
                return "adminLoginIndex"; // 관리자 페이지로 이동
            }
        }

    }
}
