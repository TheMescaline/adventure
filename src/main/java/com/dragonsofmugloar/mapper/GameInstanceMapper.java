package com.dragonsofmugloar.mapper;

import com.dragonsofmugloar.domain.GameInstance;
import com.dragonsofmugloar.dto.GameInstanceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface GameInstanceMapper {

    GameInstance toEntity(GameInstanceDTO gameInstanceDTO);
}
