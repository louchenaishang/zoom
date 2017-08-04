package com.github.louchen.zoom.api.role.model;

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
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by louchen on 2017/7/27.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Role")
@Indexed
public class Role {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    @Field
    private String name;

    @Column
    @Field
    @DateBridge(resolution = Resolution.SECOND)
    private Date createTime = new Date();

}

