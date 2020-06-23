package com.service.mapper;

import com.model.Users;
import com.view.UsersView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UsersView map(Users user);

    default List<UsersView> listMap(List<Users> usersList) {
        if (usersList == null || usersList.size() == 0) {
            return null;
        }

        List<UsersView> list = new ArrayList<>();

        for (Users user : usersList) {
            list.add(map(user));
        }

        return list;
    }
}
