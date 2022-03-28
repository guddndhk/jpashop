package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    // 테스트 코드 작성시 테스트 메서드명을 한글로 만드는편이다. 테스트안에서는 크게 문제되지 않고 보기 편하다..
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("jo");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("jo");

        Member member2 = new Member();
        member2.setName("jo");

        //when
        memberService.join(member1);
        memberService.join(member2);// 같은 이름으로 2개가 들어갔으니 예외가 나와야한다.

        //then
        fail("예외가 발생 하여야함");
    }

}