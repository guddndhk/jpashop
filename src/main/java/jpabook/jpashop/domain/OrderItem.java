package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문당시 가격
    private int count; //주문당시 수량

    //@NoArgsConstructor(access = AccessLevel.PROTECTED) 사용도 가능
    // 오더 서비스에서 뉴 생성을 막아준다.
//    protected OrderItem() {
//    }

    //==생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //넘어온 재고를 - 해준다.
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==
    public void cancel() {
        //재고 수량 원상복구.
        getItem().addStock(count);
    }

    //==조회 로직
    //주문 상품 전체 가격 조회.
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
