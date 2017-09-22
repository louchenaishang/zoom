package com.github.louchen.zoom.api.order.service;

import com.github.louchen.zoom.api.order.model.Orders;
import com.github.louchen.zoom.api.order.repository.OrdersRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrdersServiceImpl extends BaseServiceImpl<Orders, Long> implements OrdersService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Page<Orders> findByPage(Pageable pageable) {
        return ordersRepository.findByPage(pageable);
    }

}
