package happyMall.happyMall.Repository;

import happyMall.happyMall.domain.ProductOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;


    public List<ProductOrder>findAllByString(OrderSearch orderSearch){
        String jpql = "select o from ProductOrder o join o.member m";
        boolean isFirstCondition = true;  //동적으로 생성

        //주문 상태 검색
        if(orderSearch.getOrderStatus() != null) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        //값이 있으면
        if(StringUtils.hasText(orderSearch.getName())){
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<ProductOrder> query = em.createQuery(jpql, ProductOrder.class).setMaxResults(1000);

        if(orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }

        if(StringUtils.hasText((orderSearch.getName()))) {
            query = query.setParameter("name", orderSearch.getName());
        }
        return query.getResultList();
    }
}
