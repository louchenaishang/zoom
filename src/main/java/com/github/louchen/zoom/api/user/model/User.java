package com.github.louchen.zoom.api.user.model;

import com.github.louchen.zoom.api.role.model.Role;
import com.github.louchen.zoom.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by louchen on 2017/7/05.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
@Indexed
public class User extends BaseEntity<Long> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Field
    private String username;

    @Column
    @Field
    private String tel;

    @Column
    private String password;

    @Column
    private String sex;

    @Column
    @Field
    private String email;

    @Column
    @Field
    private String road;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

}

