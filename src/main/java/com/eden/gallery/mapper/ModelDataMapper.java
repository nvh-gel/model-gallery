package com.eden.gallery.mapper;

import com.eden.gallery.model.ModelData;
import com.eden.gallery.viewmodel.ModelDataVM;
import com.eden.mapper.BaseDocMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Data mapper for crawled model.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelDataMapper extends BaseDocMapper<ModelDataVM, ModelData> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "skip", ignore = true)
    @Mapping(target = "needCrawl", ignore = true)
    @Mapping(target = "id", source = "objectId", qualifiedByName = "mapStringToObjectId")
    @Mapping(target = "deleted", ignore = true)
    ModelData toDocument(ModelDataVM modelDataVM);
}
