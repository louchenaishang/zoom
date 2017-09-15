package com.github.louchen.zoom.api.system.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.louchen.zoom.api.system.domain.Setting;
import com.github.louchen.zoom.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


/**
 * Created by louchen on 2017/9/15.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "SystemConfig")
public class SystemConfig extends BaseEntity<Long> {

    public SystemConfig(Setting setting){
        setSetting(setting);
    }

    @JsonView(BaseView.class)
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 系统属性
     */
    @JsonView(BaseView.class)
    @Column(length = 20000)
    @Lob
    @Convert(converter = SettingConverter.class)
    private Setting setting = new Setting();

    /**
     * 类型转换
     *
     */
    @Converter
    public static class SettingConverter implements AttributeConverter<Setting, String> {

        @Override
        public String convertToDatabaseColumn(Setting attribute) {
            if (attribute == null) {
                return null;
            }

            return JSON.toJSONString(attribute);
        }

        @Override
        public Setting convertToEntityAttribute(String dbData) {
            if (StringUtils.isBlank(dbData)) {
                return null;
            }

            return JSON.parseObject(dbData,Setting.class);
        }

    }


}

