package com.github.louchen.zoom.api.order.service;

import com.github.louchen.zoom.api.order.model.Order;
import com.github.louchen.zoom.api.order.repository.OrderRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Page<Order> findByPage(Pageable pageable) {
        return orderRepository.findByPage(pageable);
    }

}
