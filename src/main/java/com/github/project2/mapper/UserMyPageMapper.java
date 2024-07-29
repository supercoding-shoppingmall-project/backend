package com.github.project2.mapper;

import com.github.project2.dto.mypage.UserMyPageResponse;
import com.github.project2.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMyPageMapper {
	UserMyPageMapper INSTANCE = Mappers.getMapper(UserMyPageMapper.class);

	@Mapping(source = "gender", target = "gender")
	UserMyPageResponse toUserResponse(UserEntity user);
}
