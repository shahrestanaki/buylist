package com.view;

import com.tools.EnglishAndNumber;
import com.tools.Mobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class confirmSingupDto {
    @Mobile
    @NotNull
    private String mobile;

    @Pattern(regexp = EnglishAndNumber.PATTERN, message = "تنها حروف و اعداد انگلیسی برای کد مجاز هست")
    @Length(min = 4, max = 20, message = "لطفا برای کد بین {min} و {max} حرف وارد نمایید")
    private String code;
}
