package com.minsait.api.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperUtil {

    private static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ObjectMapperUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outClass) {
        return entityList.stream().map(entity -> map(entityList, outClass)).collect(Collectors.toList());
    }

    public static <E, D> Page<D> mapAll(final Page<D> pageList, Class<D> outClass) {
        return pageList.map(entity -> map(entity, outClass));
    }

    public static <S, D> D map(final D source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
