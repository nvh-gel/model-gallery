package com.eden.gallery.mapper;

import com.eden.gallery.model.Model;
import com.eden.gallery.model.Nickname;
import com.eden.gallery.viewmodel.ModelVM;
import com.eden.gallery.viewmodel.NicknameVM;
import com.eden.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Data mapper for model.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelMapper extends BaseMapper<ModelVM, Model> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "deleted", ignore = true)
    Model toModel(ModelVM modelVM);

    /**
     * Map child nicknames.
     *
     * @param nickname nickname to map
     * @return nickname view model
     */
    @Mapping(target = "modelId", source = "model", qualifiedByName = "modelToModelId")
    @Mapping(target = "objectId", ignore = true)
    NicknameVM toViewModel(Nickname nickname);

    /**
     * Map child nickname vms.
     *
     * @param nicknameVM nickname view model to map
     * @return nickname data
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "model", ignore = true)
    Nickname toModel(NicknameVM nicknameVM);

    /**
     * Map model data to modelId for view model.
     *
     * @param model model data
     * @return model id
     */
    @Named(value = "modelToModelId")
    default Long modelToModelId(Model model) {
        if (null == model) {
            return null;
        }
        return model.getId();
    }
}
