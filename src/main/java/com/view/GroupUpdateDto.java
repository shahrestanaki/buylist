package com.view;

import lombok.Data;

import java.util.List;

@Data
public class GroupUpdateDto {
    private long groupId;
    private String groupName;
    private String groupAbout;
    private String groupAvatar;

    private String userNickName;
    private String userAvatar;

    private Long admin;

    private List<UsersAndRolesDto> userRoles;

    public GroupUpdateDto(){}

    public GroupUpdateDto(long groupId,String userNickName, String userAvatar) {
        this.groupId = groupId;
        this.userNickName = userNickName;
        this.userAvatar = userAvatar;
    }

    public GroupUpdateDto(String userNickName, String userAvatar,long groupId,String groupName, String groupAvatar, String groupAbout, List<UsersAndRolesDto> userRoles) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAbout = groupAbout;
        this.groupAvatar = groupAvatar;
        this.userNickName = userNickName;
        this.userAvatar = userAvatar;
        this.userRoles = userRoles;
    }
}
