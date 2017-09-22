package com.github.louchen.zoom.api.order.model;

import com.github.louchen.zoom.api.member.model.Member;
import com.github.louchen.zoom.api.store.model.Store;
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
@Table(name = "Orders")
public class Orders extends BaseEntity<Long> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sn;

    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;


}

