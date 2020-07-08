package com.view;

import com.tools.INumber;
import com.tools.OnlyLetter;
import com.tools.PersianOnly;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class GroupUpdateDto {
    @NotNull
    private long groupId;

    @Length(min = 5, max = 20, message = "برای نام گروه بین {min} و {max} حرف وارد کن")
    @Pattern(regexp = PersianOnly.PATTERN, message = "برای نام گروه تنها از حروف فارسی استفاده نمایید")
    private String groupName;

    @Length(max = 255, message = "برای توضیحات گروه حداکثر {max} حرف وارد کن")
    @Pattern(regexp = PersianOnly.PATTERN, message = "برای توضیحات گروه تنها از حروف فارسی استفاده نمایید")
    private String groupAbout;

    @Length(max = 5)
    @Pattern(regexp = INumber.PATTERN, message = "نوع داده وارد شده برای عکس گروه باید عددی باشه")
    private String groupAvatar;

    @Pattern(regexp = OnlyLetter.PATTERN, message = "برای نام کاربریت علائم ,.(),،_: رو به جز حرف های معمول قبول داریم")
    @Length(max = 20, message = "برای نام خودت حداکثر {max} حرف وارد کن")
    private String userNickName;

    @Length(max = 5)
    @Pattern(regexp = INumber.PATTERN, message = "نوع داده وارد شده برای عکس کاربریت باید عددی باشد")
    private String userAvatar;

    private Long admin;

    private List<UsersAndRolesDto> userRoles;

    public GroupUpdateDto() {
    }

    public GroupUpdateDto(long groupId, String userNickName, String userAvatar) {
        this.groupId = groupId;
        this.userNickName = userNickName;
        this.userAvatar = userAvatar;
    }

    public GroupUpdateDto(String userNickName, String userAvatar, long groupId, String groupName, String groupAvatar, String groupAbout, List<UsersAndRolesDto> userRoles) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAbout = groupAbout;
        this.groupAvatar = groupAvatar;
        this.userNickName = userNickName;
        this.userAvatar = userAvatar;
        this.userRoles = userRoles;
    }
}
