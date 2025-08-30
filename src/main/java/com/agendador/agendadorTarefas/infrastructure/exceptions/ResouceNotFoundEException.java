package com.agendador.agendadorTarefas.infrastructure.exceptions;

public class ResouceNotFoundEException extends RuntimeException{

    public  ResouceNotFoundEException(String mensagem){
        super(mensagem);
    }

    public ResouceNotFoundEException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }

}
