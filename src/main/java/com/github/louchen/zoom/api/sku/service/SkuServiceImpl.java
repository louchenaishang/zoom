package com.github.louchen.zoom.api.sku.service;

import com.github.louchen.zoom.api.sku.model.Sku;
import com.github.louchen.zoom.api.sku.repository.SkuRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SkuServiceImpl extends BaseServiceImpl<Sku, Long> implements SkuService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private SkuRepository skuRepository;

    @Override
    public Page<Sku> findByNameLike(String name, Pageable pageable) {
        return skuRepository.findByNameLike(name, pageable);
    }

}
