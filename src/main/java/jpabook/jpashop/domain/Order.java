package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // cascade 가 없으면 order, delivery 의 persist 를 각각 해주어야한다.
    // 하지만 cascade 가 있으면  값을 셋팅해 놓고 order 만 persist 해두면 같이 persist 호출이된다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 하이버네이트 기존 구현 : 엔티티의 필드명을 그대로 테이블 명으로 사용하였다. SpringPhysicalNamingStrategy
    // 스프링부트 신규 설정 엔티티(필트) > 테이블(칼럼)
    // 카멜 케이스 에서 > 언더스코어(스네이크케이스) 로 바뀜 orderDate > order_date
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery =delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
}
