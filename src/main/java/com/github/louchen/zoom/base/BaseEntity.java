package com.github.louchen.zoom.base;

import com.github.louchen.zoom.utils.ReflectionUtils;
import lombok.Getter;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Resolution;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - 基类
 *
 * @author louchen
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    /**
     * "ID"属性名称
     */
    public static final String ID_PROPERTY_NAME = "id";

    /**
     * "创建日期"属性名称
     */
    public static final String CREATE_TIME_PROPERTY_NAME = "createTime";

    /**
     * "最后修改日期"属性名称
     */
    public static final String UPDATE_TIME_PROPERTY_NAME = "updateTime";

    /**
     * "版本"属性名称
     */
    public static final String VERSION_PROPERTY_NAME = "version";

    @Version
    @Column
    private long version = 0;

    @Column
    @Field
    @DateBridge(resolution = Resolution.SECOND)
    @Getter
    private Date createTime = new Date();

    @Column
    @Field
    @DateBridge(resolution = Resolution.SECOND)
    @Getter
    private Date updateTime;

    @PrePersist
    public void prePersist() {
        createTime = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updateTime = new Date();
    }

    /**
     * 判断是否为新建对象
     *
     * @return 是否为新建对象
     */
    @Transient
    public boolean isNew() {
        return ReflectionUtils.getFieldValue(this, "id") == null;
    }


}
