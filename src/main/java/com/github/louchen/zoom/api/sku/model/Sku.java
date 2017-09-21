package com.github.louchen.zoom.api.sku.model;

import com.github.louchen.zoom.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
 * Created by louchen on 2017/9/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Sku")
public class Sku extends BaseEntity<Long> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal price;

}

