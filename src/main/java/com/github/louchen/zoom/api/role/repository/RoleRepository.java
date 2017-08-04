package com.github.louchen.zoom.api.role.repository;


import com.github.louchen.zoom.api.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by louchen on 2017/7/27.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>,RoleRepositoryCustom {

    Role findByName(String name);

}
