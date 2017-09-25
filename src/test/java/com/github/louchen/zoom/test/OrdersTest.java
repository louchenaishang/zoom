package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.member.model.Member;
import com.github.louchen.zoom.api.member.repository.MemberRepository;
import com.github.louchen.zoom.api.order.model.Orders;
import com.github.louchen.zoom.api.order.repository.OrdersRepository;
import com.github.louchen.zoom.api.store.model.Store;
import com.github.louchen.zoom.api.store.repository.StoreRepository;
import com.test.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by louchen on 2017/7/5.
 */
@Slf4j
public class OrdersTest extends BaseTest {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void init() {
        int sn = 200;
        int s = 800;
        int m = 1000;
        for (int i = 0; i < 10000; i++) {

            Store store = storeRepository.findOne(Long.valueOf(RandomUtils.nextInt(s)));
            Member member = memberRepository.findOne(Long.valueOf(RandomUtils.nextInt(m)));
            if(member!=null&&store!=null){
                Orders orders = new Orders();
                orders.setSn((sn++)+"");
                orders.setAmount(new BigDecimal(RandomUtils.nextInt(20)));
                orders.setStore(store);
                orders.setMember(member);
                ordersRepository.save(orders);

                member.setAmount(member.getAmount().add(orders.getAmount()));
                memberRepository.save(member);
            }
        }
    }

}
