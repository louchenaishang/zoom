package com.github.louchen.zoom.api.sku.service;

import com.github.louchen.zoom.api.sku.model.Sku;
import com.github.louchen.zoom.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * Created by louchen on 2017/9/21.
 */
public interface SkuService extends BaseService<Sku, Long> {

    Page<Sku> findByNameLike(final @Param("name") String name, final Pageable pageable);

}
