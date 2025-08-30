package com.agendador.agendadorTarefas.business.mapper;

import com.agendador.agendadorTarefas.business.dto.TarefasDTO;
import com.agendador.agendadorTarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {

    void updateTarefas(TarefasDTO dto,@MappingTarget TarefasEntity entity);
}
