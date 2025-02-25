package br.com.TriagemCheck.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CorProtocolo {
    VERMELHO,
    LARANJA,
    AMARELA,
    VERDE,
    AZUL;

    @JsonCreator
    public static CorProtocolo fromValue(String value) {
        for (CorProtocolo cor : CorProtocolo.values()) {
            if (cor.name().equalsIgnoreCase(value)) {
                return cor;
            }
        }
        throw new IllegalArgumentException("Valor inválido para CorProtocolo: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

}
