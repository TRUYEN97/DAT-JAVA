/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import com.alibaba.fastjson2.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 *
 * @author Administrator
 */
public class MyObjectMapper {

    private final ModelMapper mapper;

    private final ObjectMapper objectMapper;

    private MyObjectMapper() {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        objectMapper = new ObjectMapper();
        objectMapper.registerModules(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    }

    public static String prettyPrintJsonUsingDefaultPrettyPrinter(String uglyJsonString) throws JsonProcessingException {
        if (uglyJsonString == null) {
            return null;
        }
        MyObjectMapper objectMapper = new MyObjectMapper();
        Object jsonObject = objectMapper.objectMapper.readValue(uglyJsonString, Object.class);
        return objectMapper.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }

    public static <T> List<T> convertToList(JSONArray objects, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (objects == null || objects.isEmpty() || clazz == null) {
            return list;
        }
        for (Object object : objects) {
            list.add(convertValue(object, clazz));
        }
        return list;
    }

    public static <T> T convertValue(Object object, Class<T> clazz) {
        if (object == null || clazz == null) {
            return null;
        }
        MyObjectMapper objectMapper = new MyObjectMapper();
        return objectMapper.objectMapper.convertValue(object, clazz);
    }

    public static String writeValueAsString(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        MyObjectMapper objectMapper = new MyObjectMapper();
        return objectMapper.objectMapper.writeValueAsString(object);
    }

    public static void update(final Object source, Object target) {
        if (source == null || target == null || source.equals(target)) {
            return;
        }
        MyObjectMapper mapperUtil = new MyObjectMapper();
        mapperUtil.mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapperUtil.mapper.map(source, target);
    }

    public static void copy(final Object source, Object target) {
        if (source == null || target == null || source.equals(target)) {
            return;
        }
        MyObjectMapper mapperUtil = new MyObjectMapper();
        mapperUtil.mapper.map(source, target);
    }

    public static <T, E> List<T> mapAll(final Collection<E> listSource, Class<T> clazz) {
        return listSource.stream().map(source -> map(source, clazz)).collect(Collectors.toList());
    }

    public static <T> T map(final Object source, Class<T> clazz) {
        if (source == null || clazz == null) {
            return null;
        }
        return new MyObjectMapper().mapper.map(source, clazz);
    }

}
