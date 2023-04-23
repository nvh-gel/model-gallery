package com.eden.gallery.utils;

import com.eden.common.utils.Paging;
import com.eden.common.utils.ResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * Converter for paged data.
 */
public class PageConverter<T> {

    /**
     * Convert a page to response model.
     *
     * @param pagingData page to convert
     * @return response model
     */
    public ResponseModel toResponseFromPaging(Page<T> pagingData) {

        ResponseModel responseModel = ResponseModel.ok(pagingData.stream().toList());
        Paging paging = toPaging(pagingData);
        responseModel.setExtra(paging);
        return responseModel;
    }

    /**
     * Convert a page to paging information.
     *
     * @param pagingData page of data
     * @return paging information
     */
    private Paging toPaging(Page<T> pagingData) {
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

    /**
     * Check if iterable is a paged data and convert it to response model
     *
     * @param iter iterable of data
     * @return response model
     */
    public ResponseModel toResponseFromIterable(Iterable<T> iter) {

        if (iter instanceof Page) {
            return toResponseFromPaging((Page<T>) iter);
        }
        return ResponseModel.error("Error returning data.");
    }
}
