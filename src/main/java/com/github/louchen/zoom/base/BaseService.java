package com.github.louchen.zoom.base;


import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

/**
 * Service - 基类
 *
 * @author louchen
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    /**
     * 查找实体对象
     *
     * @param id ID
     * @return 实体对象，若不存在则返回null
     */
    T find(ID id);

    /**
     * 查找实体对象集合
     *
     * @param ids ID
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    List<T> findList(ID... ids);

    /**
     * 判断实体对象是否存在
     *
     * @param id ID
     * @return 实体对象是否存在
     */
    boolean exists(ID id);

    /**
     * 保存实体对象
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    T save(T entity);

    /**
     * 更新实体对象
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    T update(T entity);

    /**
     * 更新实体对象
     *
     * @param entity           实体对象
     * @param ignoreProperties 忽略属性
     * @return 实体对象
     */
    T update(T entity, String... ignoreProperties);

    /**
     * 删除实体对象
     *
     * @param id ID
     */
    void delete(ID id);

    /**
     * 删除实体对象
     *
     * @param ids ID
     */
    @SuppressWarnings("unchecked")
    void delete(ID... ids);

    /**
     * 删除实体对象
     *
     * @param entity 实体对象
     */
    void delete(T entity);

    /**
     * 刷新实体对象
     *
     * @param entity
     *            实体对象
     */
    void refresh(T entity);

    /**
     * 刷新实体对象
     *
     * @param entity
     *            实体对象
     * @param lockModeType
     *            锁定方式
     */
    void refresh(T entity, LockModeType lockModeType);

    /**
     * 获取实体对象ID
     *
     * @param entity
     *            实体对象
     * @return 实体对象ID
     */
    ID getIdentifier(T entity);

    /**
     * 判断实体对象是否已加载
     *
     * @param entity
     *            实体对象
     * @return 实体对象是否已加载
     */
    boolean isLoaded(T entity);

    /**
     * 判断实体对象属性是否已加载
     *
     * @param entity
     *            实体对象
     * @param attributeName
     *            属性名称
     * @return 实体对象属性是否已加载
     */
    boolean isLoaded(T entity, String attributeName);

    /**
     * 判断是否为托管状态
     *
     * @param entity
     *            实体对象
     * @return 是否为托管状态
     */
    boolean isManaged(T entity);

    /**
     * 设置为游离状态
     *
     * @param entity
     *            实体对象
     */
    void detach(T entity);

    /**
     * 获取锁定方式
     *
     * @param entity
     *            实体对象
     * @return 锁定方式
     */
    LockModeType getLockMode(T entity);

    /**
     * 锁定实体对象
     *
     * @param entity
     *            实体对象
     * @param lockModeType
     *            锁定方式
     */
    void lock(T entity, LockModeType lockModeType);

    /**
     * 清除缓存
     */
    void clear();

    /**
     * 同步数据
     */
    void flush();

}