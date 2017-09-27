package com.github.louchen.zoom.api.order.repository;


import com.github.louchen.zoom.api.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by louchen on 2017/9/21.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where 1=1")
    Page<Order> findByPage(final Pageable pageable);

}
