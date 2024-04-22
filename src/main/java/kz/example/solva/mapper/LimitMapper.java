package kz.example.solva.mapper;

import kz.example.solva.data.dto.LimitDto;
import kz.example.solva.data.entity.Limit;
import kz.example.solva.rest.request.LimitRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LimitMapper {
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "sum",expression = "java(limitRequest.getScaleSum())")
    Limit mapLimitRequestToLimit(LimitRequest limitRequest);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "created", expression = "java(limit.getCreated().toString())")
    @Mapping(target = "sum", expression = "java(limit.getScaleSum())")
    LimitDto mapLimitToLimitDto(Limit limit);
}