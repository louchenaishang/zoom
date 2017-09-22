package com.github.louchen.zoom.api.order.repository;


import com.github.louchen.zoom.api.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by louchen on 2017/9/21.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o where 1=1")
    Page<Orders> findByPage(final Pageable pageable);

}
