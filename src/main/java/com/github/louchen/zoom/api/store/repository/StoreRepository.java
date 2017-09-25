package com.github.louchen.zoom.api.store.repository;


import com.github.louchen.zoom.api.store.domain.StoreEcharts;
import com.github.louchen.zoom.api.store.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by louchen on 2017/9/21.
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select store from Store store where store.name like CONCAT('%',:name,'%')")
    Page<Store> findByNameLike(final @Param("name") String name, final Pageable pageable);


    @Query(value = "select a.amount,b.name from (select count(0) num,sum(amount) amount,store_id  from orders group by store_id having count(0)>1) a \n" +
            "left join store b on a.store_id = b.id\n" +
            "order by a.amount desc limit 0,10"
            ,nativeQuery = true)
    List<Object[]> findTopTen();

}
