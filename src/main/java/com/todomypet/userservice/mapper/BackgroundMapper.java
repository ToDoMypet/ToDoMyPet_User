package com.todomypet.userservice.mapper;

import com.todomypet.userservice.domain.node.Background;
import com.todomypet.userservice.dto.background.BackgroundResDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BackgroundMapper {
    BackgroundResDTO backgroundToBackgroundResDTO(Background background);
}
