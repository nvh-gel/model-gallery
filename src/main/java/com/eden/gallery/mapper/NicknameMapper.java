package com.eden.gallery.mapper;

import com.eden.data.mapper.BaseMapper;
import com.eden.gallery.model.Model;
import com.eden.gallery.model.Nickname;
import com.eden.gallery.viewmodel.NicknameVM;
import org.mapstruct.*;

import static com.eden.gallery.utils.Strings.SPRING;

/**
 * Data mapper for nickname.
 */
@Mapper(componentModel = SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NicknameMapper extends BaseMapper<Nickname, NicknameVM> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "model", source = "modelId", qualifiedByName = "modelIdToModel")
    Nickname toModel(NicknameVM nicknameVM);

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "modelId", source = "model", qualifiedByName = "modelToModelId")
    NicknameVM toViewModel(Nickname nickname);

    /**
     * Map related model to model id.
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

    /**
     * Map modelId to related model data.
     *
     * @param modelId model id
     * @return model data
     */
    @Named(value = "modelIdToModel")
    default Model modelIdToModel(Long modelId) {
        if (null == modelId) {
            return null;
        }
        Model model = new Model();
        model.setId(modelId);
        return model;
    }
}
