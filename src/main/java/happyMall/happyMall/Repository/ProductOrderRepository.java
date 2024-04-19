package happyMall.happyMall.Repository;


import happyMall.happyMall.domain.OrderStatus;
import happyMall.happyMall.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

//    @Query("select o from ProductOrder o join o.member m " + "where o.status = :status and m.name like %:name%")
//    List<ProductOrder> findAllByStatusAndMemberName(@Param("status") OrderStatus orderStatus, @Param("name") String name);

//    List<ProductOrder> findAllByStatusAndMemberName(OrderStatus status, String name);


}
