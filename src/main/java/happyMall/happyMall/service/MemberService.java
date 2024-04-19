package happyMall.happyMall.service;

import happyMall.happyMall.Repository.MemberRepository;
import happyMall.happyMall.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Member existingMember = memberRepository.findByLoginId(member.getLoginId());
        if (existingMember != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }



//    전체
    public List<Member> findAll() {
        return memberRepository.findAll();
    }


}
