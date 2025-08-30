package com.agendador.agendadorTarefas.business;

import com.agendador.agendadorTarefas.business.dto.TarefasDTO;
import com.agendador.agendadorTarefas.business.mapper.TarefaUpdateConverter;
import com.agendador.agendadorTarefas.business.mapper.TarefasConverter;
import com.agendador.agendadorTarefas.infrastructure.entity.TarefasEntity;
import com.agendador.agendadorTarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.agendador.agendadorTarefas.infrastructure.exceptions.ResouceNotFoundEException;
import com.agendador.agendadorTarefas.infrastructure.repository.TarefasRepository;
import com.agendador.agendadorTarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);
        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscarTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResouceNotFoundEException e) {
            throw new ResouceNotFoundEException("Erro ao deletar tarefa por id, id inexistente" + id, e.getCause());
        }

    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum staius, String id){

        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(()-> new ResouceNotFoundEException("Tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(staius);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        }catch (ResouceNotFoundEException e){
            throw new ResouceNotFoundEException("Errp ao alterar status da tarefa" + e.getCause());
        }

    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){

        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(()-> new ResouceNotFoundEException("Tarefa não encontrada" + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        }catch (ResouceNotFoundEException e){
            throw new ResouceNotFoundEException("Errp ao alterar status da tarefa" + e.getCause());
        }
    }

}
