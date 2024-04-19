package happyMall.happyMall.controller;

import happyMall.happyMall.domain.Address;
import happyMall.happyMall.domain.Member;
import happyMall.happyMall.domain.Role;
import happyMall.happyMall.form.MemberForm;
import happyMall.happyMall.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

//    회원가입 폼
    @GetMapping("/signUp")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "signUp/signUpForm";
    }

    @PostMapping("/signUp")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "signUp/signUpForm";
        }

        Address address = new Address();
        Member member = new Member();

        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setBirthday(form.getBirthday());
        member.setEmailAddress(form.getEmailAddress());
        address.setZipcode(form.getZipcode());
        address.setFullAddr(form.getFullAddr());
        address.setExtraAddr(form.getExtraAddr());
        member.setAddress(address);
        member.setUserPhoneNumber(form.getUserPhoneNumber());
        member.setGrade(Role.USER);

        memberService.join(member);
        return "redirect:/";
    }
//    회원가입 끝


//    회원목록 조회
    @GetMapping("/list")
    public String memberList(Model model) {
        List<Member> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);
        return "member/memberList";
    }
//    회원목록 조회 끝

//    회원수정
    @GetMapping("/member/{memberId}/edit")
    public String updateMember(@PathVariable("memberId") Long memberId, Model model) {

        Member findMember = memberService.findOne(memberId);

        MemberForm updateMember = new MemberForm();

        updateMember.setLoginId(findMember.getLoginId());
        updateMember.setPassword(findMember.getPassword());
        updateMember.setName(findMember.getName());
        updateMember.setBirthday(findMember.getBirthday());
        updateMember.setEmailAddress(findMember.getEmailAddress());
        updateMember.setUserPhoneNumber(findMember.getUserPhoneNumber());
        updateMember.setFullAddr(findMember.getAddress().getFullAddr());
        updateMember.setExtraAddr(findMember.getAddress().getExtraAddr());
        updateMember.setZipcode(findMember.getAddress().getZipcode());
        updateMember.setGrade(findMember.getGrade());

        model.addAttribute("updateMember", updateMember);

        return "member/updateMember";
    }

    @PostMapping("/member/{memberId}/edit")
    public String updateMemberResister(@ModelAttribute MemberForm form, @PathVariable Long memberId) {

        Member member = memberService.findOne(memberId);

        Address address = new Address(form.getFullAddr(), form.getExtraAddr(), form.getZipcode());

        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setEmailAddress(form.getEmailAddress());
        member.setBirthday(form.getBirthday());
        member.setUserPhoneNumber(form.getUserPhoneNumber());
        member.setAddress(address);
        member.setGrade(form.getGrade());

        log.info("버그 체크 {}", member);
        memberService.save(member);
        return "redirect:/list";
    }

//    관리자 등록 페이지
    @GetMapping("/adminForm")
    public String adminSignUpForm(Model model) {
        model.addAttribute("adminForm", new MemberForm());
        return "member/adminSignUpForm";
    }

    @PostMapping("/adminForm")
    public String adminSignUp(@Valid MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {
            return "member/adminSignUpForm";
        }

        Member member = new Member();
        member.setLoginId(memberForm.getLoginId());
        member.setPassword(memberForm.getPassword());
        member.setName(memberForm.getName());
        member.setUserPhoneNumber(memberForm.getUserPhoneNumber());
        member.setGrade(Role.ADMIN);

        memberService.save(member);
        return "redirect:/";
    }
}
