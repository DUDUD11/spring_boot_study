package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;

import java.util.List;

public interface OrderDynamic {

    public List<Order> findCurOrder(OrderSearch orderSearch);

    public List<Order> findAllWithMemberDelivery(int offset, int limit);
}
