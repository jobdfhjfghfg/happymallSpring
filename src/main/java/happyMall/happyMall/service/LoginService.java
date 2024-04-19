package happyMall.happyMall.service;

import happyMall.happyMall.Repository.MemberRepository;
import happyMall.happyMall.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId);
        if (member != null && member.getPassword().equals(password)) {
            return member;
        }
        return null;
    }
}
