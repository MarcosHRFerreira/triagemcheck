package br.com.TriagemCheck.validations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.InputMismatchException;


public class ValidaCPF {
    private static final Logger logger = LogManager.getLogger(ValidaCPF.class);

    public static boolean isCPF(String CPF) {
        if (CPF == null || CPF.length() != 11 || CPF.matches("(\\d)\\1{10}")) {
            logger.warn("CPF inválido: {}", CPF);
            return false;
        }

        try {
            int[] digitos = new int[11];
            for (int i = 0; i < 11; i++) {
                digitos[i] = CPF.charAt(i) - '0';
            }

            if (digitos[9] == calculaDigitoVerificador(digitos, 10) &&
                    digitos[10] == calculaDigitoVerificador(digitos, 11)) {
                return true;
            } else {
                logger.warn("Dígitos verificadores inválidos para o CPF: {}", CPF);
                return false;
            }
        } catch (InputMismatchException | NumberFormatException e) {
            logger.error("Erro ao validar CPF: {}", CPF, e);
            return false;
        }
    }

    private static int calculaDigitoVerificador(int[] digitos, int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;
        for (int i = 0; i < pesoInicial - 1; i++) {
            soma += digitos[i] * peso--;
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public static String imprimeCPF(String CPF) {
        return CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, 11);
    }
}