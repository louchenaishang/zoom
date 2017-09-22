package com.github.louchen.zoom.api.order.service;

import com.github.louchen.zoom.api.order.model.Orders;
import com.github.louchen.zoom.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by louchen on 2017/9/21.
 */
public interface OrdersService extends BaseService<Orders, Long> {

    Page<Orders> findByPage(final Pageable pageable);

}
