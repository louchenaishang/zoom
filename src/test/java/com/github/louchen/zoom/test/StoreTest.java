package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.store.model.Store;
import com.github.louchen.zoom.api.store.repository.StoreRepository;
import com.test.base.BaseTest;
import com.test.base.RandomMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by louchen on 2017/7/5.
 */
@Slf4j
public class StoreTest extends BaseTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void init() {
        for (int i = 0; i < 1000; i++) {
            Map<String, String> data = RandomMock.getData();
            Store store = new Store();
            String road = data.get("road");
            if(road.contains("路")){
                store.setName(road.substring(0,road.indexOf("路")+1)+"店");
                store.setAddress(road.substring(0,road.indexOf("路")+1));
                storeRepository.save(store);
            }
        }
    }

}
