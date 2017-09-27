package com.github.louchen.zoom.api.order.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.louchen.zoom.api.sku.model.Sku;
import com.github.louchen.zoom.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by louchen on 2017/9/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "OrderItem")
public class OrderItem extends BaseEntity<Long> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer shippedQuantity;

    @Column(nullable = false)
    private Integer returnedQuantity;

    /**
     * SKU
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Sku sku;

    /**
     * 订单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private Order order;

}
