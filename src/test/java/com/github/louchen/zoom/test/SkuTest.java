package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.sku.model.Sku;
import com.github.louchen.zoom.api.sku.repository.SkuRepository;
import com.test.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by louchen on 2017/9/21.
 */
@Slf4j
public class SkuTest extends BaseTest {

    @Autowired
    private SkuRepository skuRepository;

    @Test
    public void init() {
        for (int i = 0; i < 100; i++) {
            Sku sku = new Sku();
            sku.setName(i+"");
            sku.setPrice(BigDecimal.TEN);
            skuRepository.save(sku);
        }
    }

}
