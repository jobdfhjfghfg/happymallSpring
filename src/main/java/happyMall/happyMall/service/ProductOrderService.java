package happyMall.happyMall.service;

import happyMall.happyMall.Repository.*;
import happyMall.happyMall.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final InventoryRepository inventoryRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(Long memberId, Long inventoryId, int quantity) {

        Optional<Inventory> inventoryOptional = inventoryRepository.findById(inventoryId);

        Optional<Member> memberOptional = memberRepository.findById(memberId);



        if (inventoryOptional.isPresent() && memberOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            Member member = memberOptional.get();

            // 주문 상품 생성
            OrderItem orderItem = OrderItem.createOrderItem(inventory, inventory.getPrice(), quantity);

            // 배송 정보
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            delivery.setBuyerPhoneNumber(member.getUserPhoneNumber());
            delivery.setStatus(DeliveryStatus.READY);


            // 주문 생성 및 주문 상품 추가
            ProductOrder productOrder = ProductOrder.createOrder(member, delivery, quantity);
            productOrder.setOrderPrice(inventory.getPrice());
            productOrder.addOrderItem(orderItem);


            // 주문 저장
            productOrderRepository.save(productOrder);

            return productOrder.getId();
        } else {
            throw new IllegalStateException("주문 정보가 없습니다.");
        }
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Optional<ProductOrder> orderOptional = productOrderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            ProductOrder order = orderOptional.get();
            order.cancel();
        }
    }

    public List<ProductOrder> findAll() {
        return productOrderRepository.findAll();
    }

    public List<ProductOrder> findAllOrdersByStatusAndMemberName(OrderSearch orderSearch) {

        return orderRepository.findAllByString(orderSearch);
    }

}
