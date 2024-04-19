package happyMall.happyMall.Repository;

import happyMall.happyMall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String loginId);

//`    Member findByName(String name);`
}
