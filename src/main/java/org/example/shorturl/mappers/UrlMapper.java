package org.example.shorturl.mappers;

import org.example.shorturl.service.UrlCreateDto;
import org.example.shorturl.entity.Url;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UrlMapper {
    Url toEntity(UrlCreateDto urlCreateDto);


}