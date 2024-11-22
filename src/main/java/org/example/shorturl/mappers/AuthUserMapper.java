package org.example.shorturl.mappers;

import org.example.shorturl.dtos.auth.AuthUserCreateDto;
import org.example.shorturl.entity.AuthUser;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthUserMapper {
    AuthUser toEntity(AuthUserCreateDto authUserCreateDto);

    /*@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AuthUser partialUpdate(AuthUserCreateDto authUserCreateDto, @MappingTarget AuthUser authUser);*/
}