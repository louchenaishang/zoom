package com.github.louchen.zoom.api.sku.repository;


import com.github.louchen.zoom.api.sku.model.Sku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by louchen on 2017/9/21.
 */
@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {

    @Query("select sku from Sku sku where sku.name like CONCAT('%',:name,'%')")
    Page<Sku> findByNameLike(final @Param("name") String name, final Pageable pageable);

}
