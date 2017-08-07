package com.github.louchen.zoom.base;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Service - 基类
 *
 * @author louchen
 */
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 更新忽略属性
     */
    private static final String[] UPDATE_IGNORE_PROPERTIES = new String[]{BaseEntity.CREATE_TIME_PROPERTY_NAME, BaseEntity.UPDATE_TIME_PROPERTY_NAME, BaseEntity.VERSION_PROPERTY_NAME};

    /**
     * JpaRepository
     */
    @Autowired
    private JpaRepository<T, ID> jpaRepository;

    /**
     * 实体管理器
     */
    @Autowired
    private EntityManager entityManager;

//    @Autowired
//    protected void setJpaRepository(JpaRepository<T, ID> jpaRepository) {
//        this.jpaRepository = jpaRepository;
//    }

    @Override
    @Transactional(readOnly = true)
    public T find(ID id) {
        return jpaRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findList(ID... ids) {
        List<T> result = new ArrayList<>();
        if (ids != null) {
            for (ID id : ids) {
                T entity = find(id);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(ID id) {
        return find(id) != null;
    }

    @Override
    @Transactional
    public T save(T entity) {
        Assert.notNull(entity);
        Assert.isTrue(entity.isNew());

        jpaRepository.save(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(T entity) {
        Assert.notNull(entity);
        Assert.isTrue(!entity.isNew());

        if (!isManaged(entity)) {
            T persistant = find(getIdentifier(entity));
            if (persistant != null) {
                copyProperties(entity, persistant, UPDATE_IGNORE_PROPERTIES);
            }
            return persistant;
        }
        return entity;
    }

    @Override
    @Transactional
    public T update(T entity, String... ignoreProperties) {
        Assert.notNull(entity);
        Assert.isTrue(!entity.isNew());
        Assert.isTrue(!isManaged(entity));

        T persistant = find(getIdentifier(entity));
        if (persistant != null) {
            copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
        }
        return update(persistant);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        delete(find(id));
    }

    @Override
    @Transactional
    public void delete(ID... ids) {
        if (ids != null) {
            for (ID id : ids) {
                delete(find(id));
            }
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        if (entity != null) {
            jpaRepository.delete(entity);
        }
    }

    @Override
    public void refresh(T entity) {
        if (entity != null) {
            entityManager.refresh(entity);
        }
    }

    @Override
    public void refresh(T entity, LockModeType lockModeType) {
        if (entity != null) {
            if (lockModeType != null) {
                entityManager.refresh(entity, lockModeType);
            } else {
                entityManager.refresh(entity);
            }
        }
    }

    @Override
    public ID getIdentifier(T entity) {
        Assert.notNull(entity);

        return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }

    @Override
    public boolean isLoaded(T entity) {
        Assert.notNull(entity);

        return entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entity);
    }

    @Override
    public boolean isLoaded(T entity, String attributeName) {
        Assert.notNull(entity);
        Assert.hasText(attributeName);

        return entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entity, attributeName);
    }

    @Override
    public boolean isManaged(T entity) {
        Assert.notNull(entity);

        return entityManager.contains(entity);
    }

    @Override
    public void detach(T entity) {
        if (entity != null) {
            entityManager.detach(entity);
        }
    }

    @Override
    public LockModeType getLockMode(T entity) {
        Assert.notNull(entity);

        return entityManager.getLockMode(entity);
    }

    @Override
    public void lock(T entity, LockModeType lockModeType) {
        if (entity != null && lockModeType != null) {
            entityManager.lock(entity, lockModeType);
        }
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    /**
     * 拷贝对象属性
     *
     * @param source           源
     * @param target           目标
     * @param ignoreProperties 忽略属性
     */
    protected void copyProperties(T source, T target, String... ignoreProperties) {
        Assert.notNull(source);
        Assert.notNull(target);

        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (ArrayUtils.contains(ignoreProperties, propertyName) || readMethod == null || writeMethod == null || !isLoaded(source, propertyName)) {
                continue;
            }
            try {
                Object sourceValue = readMethod.invoke(source);
                writeMethod.invoke(target, sourceValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

}