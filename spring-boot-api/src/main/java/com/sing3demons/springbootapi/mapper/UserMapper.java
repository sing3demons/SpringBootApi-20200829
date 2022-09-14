package com.sing3demons.springbootapi.mapper;

import org.mapstruct.Mapper;

import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.model.RegisterResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterResponse registerResponse(User user);
}
