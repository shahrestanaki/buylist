package com.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

   /* UserView map(UsersUpdateView userInfo);

    default List<UserView> listMap(List<UsersUpdateView> userInfoa) {
        if (userInfoa == null) {
            return null;
        }

        List<UserView> list = new ArrayList<>();

        for (UsersUpdateView userInfo : userInfoa) {
            list.add(map(userInfo));
        }

        return list;
    }*/
}
