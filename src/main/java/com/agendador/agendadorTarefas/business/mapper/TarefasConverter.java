package com.agendador.agendadorTarefas.business.mapper;

import com.agendador.agendadorTarefas.business.dto.TarefasDTO;
import com.agendador.agendadorTarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);
    TarefasDTO paraTarefaDTO(TarefasEntity entity);
}
