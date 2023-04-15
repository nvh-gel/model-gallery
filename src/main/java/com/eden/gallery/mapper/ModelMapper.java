package com.eden.gallery.mapper;

import com.eden.data.mapper.BaseMapper;
import com.eden.gallery.model.Model;
import com.eden.gallery.model.Nickname;
import com.eden.gallery.viewmodel.ModelVM;
import com.eden.gallery.viewmodel.NicknameVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelMapper extends BaseMapper<Model, ModelVM> {

    @Override
    @Mapping(target = "deleted", ignore = true)
    Model toModel(ModelVM modelVM);

    @Mapping(target = "model", ignore = true)
    NicknameVM toViewModel(Nickname nickname);

    @Mapping(target = "deleted", ignore = true)
    Nickname toModel(NicknameVM nicknameVM);
}
