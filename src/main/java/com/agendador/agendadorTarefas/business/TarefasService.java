package com.agendador.agendadorTarefas.business;

import com.agendador.agendadorTarefas.business.dto.TarefasDTO;
import com.agendador.agendadorTarefas.business.mapper.TarefasConverter;
import com.agendador.agendadorTarefas.infrastructure.entity.TarefasEntity;
import com.agendador.agendadorTarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.agendador.agendadorTarefas.infrastructure.repository.TarefasRepository;
import com.agendador.agendadorTarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);
        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }

}
