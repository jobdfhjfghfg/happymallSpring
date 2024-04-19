package happyMall.happyMall.controller;

import happyMall.happyMall.domain.Inventory;
import happyMall.happyMall.domain.Member;
import happyMall.happyMall.domain.Role;
import happyMall.happyMall.form.LoginForm;
import happyMall.happyMall.service.InventoryService;
import happyMall.happyMall.service.LoginService;
import happyMall.happyMall.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final InventoryService inventoryService;
    private final MemberService memberService;

    //    로그인
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());

        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, Model model) {

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

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMemberCheck = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginMemberCheck == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberCheck);

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {
            return "login/loginForm";
        }

        Member loginMember = (Member) session1.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember.getGrade() == Role.USER) {
            return "userLoginIndex";
        } else if (loginMember.getGrade() == Role.ADMIN) {
            return "adminLoginIndex";
        }



        return "redirect:/";
    }


//    로그인 끝

//    로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); //세션 있으면 세션 삭제
        }
        return "redirect:/";
    }
}
