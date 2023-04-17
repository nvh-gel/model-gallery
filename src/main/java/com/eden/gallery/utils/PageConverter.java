package com.eden.gallery.utils;

import com.eden.common.utils.Paging;
import com.eden.common.utils.ResponseModel;
import com.eden.gallery.mapper.ModelMapper;
import com.eden.gallery.model.Model;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Converter for paged data.
 */
@Component
public class PageConverter {

    private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

    /**
     * Convert paging data to response model.
     *
     * @param pagingData paging data
     * @return response model
     */
    public ResponseModel toResponseFromPaging(Page<Model> pagingData) {

        ResponseModel responseModel = ResponseModel.ok(pagingData
                .stream()
                .map(modelMapper::toViewModel)
                .toList());
        Sort.Order order = pagingData.getSort().stream().findFirst().orElse(null);
        Paging paging = new Paging(
                pagingData.getNumber() + 1,
                pagingData.getSize(),
                (int) pagingData.getTotalElements(),
                pagingData.getTotalPages(),
                null == order ? null : order.getProperty(),
                null == order ? null : order.getDirection().name()
        );
        responseModel.setExtra(paging);
        return responseModel;
    }
}
