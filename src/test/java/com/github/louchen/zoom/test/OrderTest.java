package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.member.model.Member;
import com.github.louchen.zoom.api.member.repository.MemberRepository;
import com.github.louchen.zoom.api.order.model.Order;
import com.github.louchen.zoom.api.order.repository.OrderRepository;
import com.github.louchen.zoom.api.store.model.Store;
import com.github.louchen.zoom.api.store.repository.StoreRepository;
import com.test.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by louchen on 2017/7/5.
 */
@Slf4j
public class OrderTest extends BaseTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void init() {
        int sn = 200;
        int s = 800;
        int m = 1000;
        for (int i = 0; i < 100; i++) {

            Store store = storeRepository.findOne(Long.valueOf(RandomUtils.nextInt(s)));
            Member member = memberRepository.findOne(Long.valueOf(RandomUtils.nextInt(m)));
            if(member!=null&&store!=null){
                Order order = new Order();
                order.setSn((sn++)+"");
                order.setStatus(Order.Status.completed);
                order.setAmount(new BigDecimal(RandomUtils.nextInt(20)));
                order.setAmountPaid(order.getAmount());
                order.setRefundAmount(BigDecimal.ZERO);
                order.setQuantity(0);
                order.setShippedQuantity(0);
                order.setReturnedQuantity(0);
                order.setStore(store);
                order.setMember(member);
                orderRepository.save(order);

                member.setAmount(member.getAmount().add(order.getAmount()));
                memberRepository.save(member);
            }
        }
    }

}
