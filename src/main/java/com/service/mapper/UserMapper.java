package com.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

   /* UserView map(UserInfoView userInfo);

    default List<UserView> listMap(List<UserInfoView> userInfoa) {
        if (userInfoa == null) {
            return null;
        }

        List<UserView> list = new ArrayList<>();

        for (UserInfoView userInfo : userInfoa) {
            list.add(map(userInfo));
        }

        return list;
    }*/
}
