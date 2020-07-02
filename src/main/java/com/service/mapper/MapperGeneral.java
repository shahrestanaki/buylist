package com.service.mapper;

import com.model.BaseEntity;
import com.model.BaseEntityView;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperGeneral {
    public static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public static MapperFacade mapper(Class<?> m, Class<?> v) {
        mapperFactory.classMap(m, v).byDefault().register();
        return mapperFactory.getMapperFacade();
    }

    public static MapperFacade mapper(Class<?> m, Class<?> v, String fields) {
        mapperFactory.classMap(m, v).byDefault()
                .field(fields.split(",")[0], fields.split(",")[1]).register();
        return mapperFactory.getMapperFacade();
    }

}
