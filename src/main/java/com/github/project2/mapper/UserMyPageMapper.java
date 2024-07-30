//package com.github.project2.mapper;
//
//import com.github.project2.dto.mypage.UserMyPageResponse;
//import com.github.project2.entity.user.UserEntity;
//import com.github.project2.entity.user.enums.Gender;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface UserMyPageMapper {
//	UserMyPageMapper INSTANCE = Mappers.getMapper(UserMyPageMapper.class);
//
//	@Mapping(target = "gender", source = "gender")
//	UserMyPageResponse toUserResponse(UserEntity user);
//
//	default String mapGender(Gender gender) {
//		return gender != null ? gender.name() : null;
//	}
//}
