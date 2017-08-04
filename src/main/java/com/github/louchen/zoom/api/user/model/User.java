package com.github.louchen.zoom.api.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by louchen on 2017/7/05.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
@Indexed
public class User {

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

    @Column
    @Field
    @DateBridge(resolution = Resolution.SECOND)
    private Date createTime = new Date();

    @Column
    @Field
    @DateBridge(resolution = Resolution.SECOND)
    private Date lastPasswordResetDate;

    @PrePersist
    public void prePersist() {
        createTime = new Date();
    }

    @PreUpdate
    public void preUpdate() {

    }

}

