package com.github.louchen.zoom.api.order.model;

import com.github.louchen.zoom.api.member.model.Member;
import com.github.louchen.zoom.api.store.model.Store;
import com.github.louchen.zoom.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by louchen on 2017/9/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Orders extends BaseEntity<Long> {


    /**
     * 状态
     */
    public enum Status {

        /**
         * 等待付款
         */
        pendingPayment,

        /**
         * 等待审核
         */
        pendingReview,

        /**
         * 等待发货
         */
        pendingShipment,

        /**
         * 已发货
         */
        shipped,

        /**
         * 已收货
         */
        received,

        /**
         * 已完成
         */
        completed,

        /**
         * 已失败
         */
        failed,

        /**
         * 已取消
         */
        canceled,

        /**
         * 已拒绝
         */
        denied
    }

    /**
     * 售后状态组
     */
    public static final Status[] AFTER_SALE_STATUS = {Status.shipped,Status.received,Status.completed};



    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Orders.Status status;


    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal amountPaid;

    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal refundAmount;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer shippedQuantity;

    @Column(nullable = false)
    private Integer returnedQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    /**
     * 订单项
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("id asc")
    private List<OrderItem> orderItems = new ArrayList<>();

}

