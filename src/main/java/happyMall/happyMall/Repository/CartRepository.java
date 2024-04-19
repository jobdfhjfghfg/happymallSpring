package happyMall.happyMall.Repository;

import happyMall.happyMall.domain.Cart;
import happyMall.happyMall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMember(Member member);

}
