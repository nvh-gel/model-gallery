package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for nickname.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NicknameVM extends BaseVM {

    private String nick;
    private String url;
    private Long modelId;
}
