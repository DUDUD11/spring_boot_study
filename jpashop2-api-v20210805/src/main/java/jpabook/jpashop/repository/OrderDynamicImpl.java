package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.validation.constraints.Null;
import java.util.List;

import static jpabook.jpashop.domain.QDelivery.*;
import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.*;
import static org.springframework.util.StringUtils.hasText;

public class OrderDynamicImpl implements OrderDynamic {

    private final JPAQueryFactory jpaQueryFactory;


    public OrderDynamicImpl(EntityManager em) {
        jpaQueryFactory=new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findCurOrder(OrderSearch orderSearch) {
        return jpaQueryFactory.
                selectFrom(order).leftJoin(order.member,member)
                .where(usernameEq(orderSearch.getMemberName()),
                        orderStatusEq(orderSearch.getOrderStatus()))
                .fetch();
    }


    public List<Order> findAllWithMemberDelivery(int offset, int limit) {

        return jpaQueryFactory.
                selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .offset(offset)
                .limit(limit)
                .fetch();
    }


    private BooleanExpression usernameEq(String username)
    {
        return hasText(username) ? member.name.eq(username):null;
    }

    private BooleanExpression orderStatusEq(OrderStatus status)
    {
        return status==null? null: order.status.eq(status);
    }

}
