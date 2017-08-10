package com.github.louchen.zoom.utils;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Utils - XML
 *
 * @author louchen
 */
public final class XmlUtils {

    /**
     * XmlMapper
     */
    private static final XmlMapper XML_MAPPER;

    static {
        XML_MAPPER = new XmlMapper();
        XML_MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        XML_MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    /**
     * 不可实例化
     */
    private XmlUtils() {
    }

    /**
     * 将对象转换为XML字符串
     *
     * @param value 对象
     * @return XML字符串
     */
    public static String toXml(Object value) {
        Assert.notNull(value, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        try {
            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(true);
            XmlMapper xmlMapper = new XmlMapper(module);
            return xmlMapper.writer().withRootName("xml").writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将XML字符串转换为对象
     *
     * @param xml       XML字符串
     * @param valueType 类型
     * @return 对象
     */
    public static <T> T toObject(String xml, Class<T> valueType) {
        Assert.hasText(xml, SpringUtils.getMessage("common.error.paramMustNotBeNull"));
        Assert.notNull(valueType, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        try {
            return XML_MAPPER.readValue(xml, valueType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将XML字符串转换为对象
     *
     * @param xml           XML字符串
     * @param typeReference 类型
     * @return 对象
     */
    public static <T> T toObject(String xml, TypeReference<?> typeReference) {
        Assert.hasText(xml, SpringUtils.getMessage("common.error.paramMustNotBeNull"));
        Assert.notNull(typeReference, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        try {
            return XML_MAPPER.readValue(xml, typeReference);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将XML字符串转换为对象
     *
     * @param xml      XML字符串
     * @param javaType 类型
     * @return 对象
     */
    public static <T> T toObject(String xml, JavaType javaType) {
        Assert.hasText(xml, SpringUtils.getMessage("common.error.paramMustNotBeNull"));
        Assert.notNull(javaType, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        try {
            return XML_MAPPER.readValue(xml, javaType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将XML字符串转换为树
     *
     * @param xml XML字符串
     * @return 树
     */
    public static JsonNode toTree(String xml) {
        Assert.hasText(xml, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        try {
            return XML_MAPPER.readTree(xml);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换为XML流
     *
     * @param writer Writer
     * @param value  对象
     */
    public static void writeValue(Writer writer, Object value) {
        Assert.notNull(writer, SpringUtils.getMessage("common.error.paramMustNotBeNull"));
        Assert.notNull(value, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        try {
            XML_MAPPER.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 构造类型
     *
     * @param type 类型
     * @return 类型
     */
    public static JavaType constructType(Type type) {
        Assert.notNull(type, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        return TypeFactory.defaultInstance().constructType(type);
    }

    /**
     * 构造类型
     *
     * @param typeReference 类型
     * @return 类型
     */
    public static JavaType constructType(TypeReference<?> typeReference) {
        Assert.notNull(typeReference, SpringUtils.getMessage("common.error.paramMustNotBeNull"));

        return TypeFactory.defaultInstance().constructType(typeReference);
    }

}