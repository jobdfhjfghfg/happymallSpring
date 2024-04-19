package happyMall.happyMall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Slf4j
@ToString
public class ProductOrder {

    @Id
    @GeneratedValue
    @Column(name = "productOrder_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "productOrder", cascade = CascadeType.ALL)
//    error원인 : 자식 요소에서 변경된것을 부모 요소에서도 저장이 되려면 cascade필수로 지정해주기(orderItem 내용물이 db에서 저장 안되는 문제 해결)
    private List<OrderItem> orderItems = new ArrayList<>();

    private String orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int orderQuantity;

    private int orderPrice;

    public void setMember(Member member) {
        this.member = member;
        member.getProductOrder().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {

        this.orderItems.add(orderItem);
        orderItem.setProductOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static ProductOrder createOrder(Member member, Delivery delivery, int quantity, OrderItem... orderItems) {

        ProductOrder order = new ProductOrder();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setOrderQuantity(quantity);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
            log.info("체크{}",orderItem);
        }


        order.setStatus(OrderStatus.ORDER);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(dateTimeFormatter);
        order.setOrderDate(dateTime);
        return order;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

//    총 주문 금액
    public int getTotalPrice() {

        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
