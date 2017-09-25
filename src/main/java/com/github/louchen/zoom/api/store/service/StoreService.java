package com.github.louchen.zoom.api.store.service;

import com.github.louchen.zoom.api.store.domain.StoreEcharts;
import com.github.louchen.zoom.api.store.model.Store;
import com.github.louchen.zoom.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by louchen on 2017/9/21.
 */
public interface StoreService extends BaseService<Store, Long> {

    Page<Store> findByNameLike(final @Param("name") String name, final Pageable pageable);

    List<StoreEcharts> findTopTen();

}
