package com.github.louchen.zoom.api.store.service;

import com.github.louchen.zoom.api.store.model.Store;
import com.github.louchen.zoom.api.store.repository.StoreRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Page<Store> findByNameLike(String name, Pageable pageable) {
        return storeRepository.findByNameLike(name, pageable);
    }

}
