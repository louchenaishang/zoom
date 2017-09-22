package com.github.louchen.zoom.api.store.repository;


import com.github.louchen.zoom.api.store.model.Store;
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
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select store from Store store where store.name like CONCAT('%',:name,'%')")
    Page<Store> findByNameLike(final @Param("name") String name, final Pageable pageable);

}
