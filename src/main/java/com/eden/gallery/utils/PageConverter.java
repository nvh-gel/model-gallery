package com.eden.gallery.utils;

import com.eden.common.utils.Paging;
import com.eden.common.utils.ResponseModel;
import com.eden.common.viewmodel.BaseVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * Converter for paged data.
 */
public class PageConverter {

    /**
     * Convert a page of data to response model.
     *
     * @param iterable data to convert
     * @return response model
     */
    public ResponseModel toResponse(Iterable<? extends BaseVM> iterable) {
        if (iterable instanceof Page<? extends BaseVM> pagingData) {
            ResponseModel responseModel = ResponseModel.ok(pagingData.stream().toList());
            Paging paging = toPaging(pagingData);
            responseModel.setExtra(paging);
            return responseModel;
        }
        return ResponseModel.ok(iterable);
    }

    /**
     * Convert a page to paging information.
     *
     * @param pagingData page of data
     * @return paging information
     */
    private Paging toPaging(Page<? extends BaseVM> pagingData) {
        Sort.Order order = pagingData.getSort().stream().findFirst().orElse(null);
        return new Paging(
                pagingData.getNumber() + 1,
                pagingData.getSize(),
                (int) pagingData.getTotalElements(),
                pagingData.getTotalPages(),
                null == order ? null : order.getProperty(),
                null == order ? null : order.getDirection().name()
        );
    }
}
