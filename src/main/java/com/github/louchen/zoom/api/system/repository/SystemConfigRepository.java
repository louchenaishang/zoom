package com.github.louchen.zoom.api.system.repository;


import com.github.louchen.zoom.api.system.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by louchen on 2017/9/15.
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long>{

    /**
     * 返回数据库唯一的记录

     * @return 系统配置，若不存在则返回null
     */
    @Query("select sc from SystemConfig sc order by sc.id asc")
    SystemConfig findSystemConfig();

}
