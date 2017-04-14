package com.patrickgrimard.examples;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * json处理类
 *
 * @author motorfu
 */
public class JsonUtil {
  private final static Logger LOGGER = LoggerUtil.get();
  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final ObjectMapper mapper;

  public ObjectMapper getMapper () {
    return mapper;
  }

  static {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//    mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
//        mapper.setDateFormat(dateFormat);
    mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
      @Override
      public Object findSerializer (Annotated a) {

        if(a instanceof AnnotatedMethod) {
          AnnotatedElement m = a.getAnnotated();
          DateTimeFormat an = m.getAnnotation(DateTimeFormat.class);
          if(an != null) {
//                        if (!DEFAULT_DATE_FORMAT.equals(an.pattern())) {
//                            return new JsonDateSerializer(an.pattern());
//                        }
            LOGGER.info("转化Date 为时间戳");
//            return new JsonDateForLongSerializer();
          }
        } else if(a instanceof AnnotatedClass) {
          AnnotatedElement m = a.getAnnotated();
          DateTimeFormat an = m.getAnnotation(DateTimeFormat.class);
          if(an != null) {
            LOGGER.info("转化Date 为时间戳");
//            return new JsonDateForLongSerializer();
          }
        }
        return super.findSerializer(a);
      }


    });

  }

  public static String toJson (Object obj) {
    try {
      if(StringUtils.isEmpty(obj)) return null;
      //强制将Date类型转化为时间戳
      SimpleModule module = new SimpleModule();
      module.addSerializer(Date.class, new JsonDateForLongSerializer());
      mapper.registerModule(module);
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
//      throw new RuntimeException("转换json字符失败!");
      LOGGER.error("1转换json字符失败!obj={}\nmessage={}", obj, e.getMessage());
      return null;
    }
  }


  /**
   * jackson 解析成对象,json中属性必须与对象中的变量一致
   *
   * @param json
   * @param classze
   * @param <T>
   * @return
   */
  public static <T> T parseJsonForJackson (String json, Class<T> classze) {
    try {
      if(StringUtils.isEmpty(json)) {
        return null;
      }
      return mapper.readValue(json, classze);
    } catch (Exception e) {
      LOGGER.error("4json转换对象失败!");
      return null;
    }
  }

  public <T> T toObject (String json, Class<T> clazz) {
    try {
      if(StringUtils.isEmpty(json)) {
        return null;
      }
      return mapper.readValue(json, clazz);
    } catch (IOException e) {
      LOGGER.error("将json字符转换为对象时失败!");
      return null;
    }
  }

  /**
   * 转化指定格式的Date
   */
  public static class JsonDateSerializer extends JsonSerializer<Date> {
    private SimpleDateFormat dateFormat;

    public JsonDateSerializer (String format) {
      dateFormat = new SimpleDateFormat(format);
    }

    @Override
    public void serialize (Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
      String value = dateFormat.format(date);
      gen.writeString(value);
    }
  }

  public static class JsonDateForLongSerializer extends JsonSerializer<Date> {

    public JsonDateForLongSerializer () {
    }

    @Override
    public void serialize (Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
      LOGGER.debug("将Date 转时间戳");
      Timestamp t = new Timestamp(date.getTime());
      gen.writeNumber(t.getTime());
    }
  }


  static ParameterizedType type (final Class raw, final Type... args) {
    return new ParameterizedType() {
      public Type getRawType () {
        return raw;
      }

      public Type[] getActualTypeArguments () {
        return args;
      }

      public Type getOwnerType () {
        return null;
      }
    };
  }
}  