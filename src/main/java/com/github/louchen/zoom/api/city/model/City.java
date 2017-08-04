package com.github.louchen.zoom.api.city.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by louchen on 2017/6/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "City")
public class City {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Date createTime = new Date();

}

