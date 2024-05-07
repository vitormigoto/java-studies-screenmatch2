package br.com.alura.screemmatch2.service;

public interface iConverteDados {
    <T> T obterDados(String json, Class <T> classe);
}
