package com.github.louchen.zoom.config.jackson;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * org.springframework.http.converter.json.MappingJackson2HttpMessageConverter的objectMapper属性
 * Created by louchen on 2016/12/10.
 */
public class JacksonObjectMapper extends ObjectMapper {

    private String ignoreProperty;

    private String dateFormatPattern;

    public String getIgnoreProperty() {
        return ignoreProperty;
    }

    public void setIgnoreProperty(String ignoreProperty) {
        this.ignoreProperty = ignoreProperty;
    }

    public String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    public void init() {
        // 排除值为空属性
        //setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 按字母顺序排序
        configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true);
        // 反序列化时忽略在JSON字符串中存在但Java对象实际没有的属性
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 反序列化时允许空字符串转换成null
        configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,false);
        // 反序列化时允许空数组转换成null
        configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,false);
        // 序列化空对象不要抛异常；
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        // 序列化进行缩进输出
        configure(SerializationFeature.INDENT_OUTPUT, true);
        // 序列化枚举是以toString()来输出，默认false，即默认以name()来输出
        configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING,false);
        // 序列化单元素数组时不以数组来输出，默认false
        configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,false);
        // 序列化Map时对key进行排序操作，默认false
        configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,true);
        //转义字符异常
        configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);

        // hibernate注解过滤
        hibernateMethod();
        // 进行属性过滤
        filterMethod();
        // 进行日期格式化
        formatDateMethod();

    }

    private void filterMethod() {
        if(StringUtils.isNotEmpty(ignoreProperty)){
            SimpleFilterProvider filterProvider = new SimpleFilterProvider();
            filterProvider.setDefaultFilter(SimpleBeanPropertyFilter.serializeAllExcept(ignoreProperty.split(",")));
            setFilterProvider(filterProvider);
        }
    }

    private void formatDateMethod(){
        if (StringUtils.isNotEmpty(dateFormatPattern)) {
            DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.CHINA);
            setDateFormat(dateFormat);
        }
    }

    private void hibernateMethod(){
        Hibernate5Module hm = new Hibernate5Module();
        hm.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        registerModule(hm);
    }

}
