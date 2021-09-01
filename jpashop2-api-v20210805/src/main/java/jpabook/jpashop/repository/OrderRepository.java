package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>,OrderDynamic {

    @Query("select o from Order o where o.id=:id")
    public Order findOne(@Param("id") Long id);


    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    public List<Order> findAllWithMemberDelivery();


    @Query( "select distinct o from Order o" +   " join fetch o.member m" +  " join fetch o.delivery d" +   " join fetch o.orderItems oi" + " join fetch oi.item i")
    public List<Order> findAllWithItem();
    


}

