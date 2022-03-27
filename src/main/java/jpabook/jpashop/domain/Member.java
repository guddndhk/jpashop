package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 없어도 되지만 식별성을 위해서
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    /*
    컬렉션은 필드에서 초기화 해주자 생성자를 통해 할수도 있지만..
    비교적 안전하고 null 문제에서 안전하다. 하이버네이트는 엔티티를 영속화 할 때, 컬렉션을 감싸서 하이버네이트가 제공하는
    내장 컬렉션으로 변경한다. 만약 getOrder() 처럼 임의의 메서드에서 컬렉션을 잘 못 생성하면 하이버네이트 내부 메커니즘에 문제가
    발생할 수 있다. 그래서 필드레벨에서 생성하는 것이 가장 안정하고 코드의 간결함도 챙길수 있다.
     */
}
