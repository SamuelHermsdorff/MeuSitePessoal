package com.academia.service;

import java.util.regex.Pattern;

public class ValidacaoService {

    public static void validarAluno(
            String nome,
            String cpf,
            String email,
            String telefone
    ) {

        validarNome(nome);
        validarCPF(cpf);
        validarEmail(email);
        validarTelefone(telefone);
    }

    public static void validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome é obrigatório."
            );
        }

        nome = nome.trim();

        if (nome.length() < 3) {
            throw new IllegalArgumentException(
                    "Nome deve possuir no mínimo 3 caracteres."
            );
        }

        if (!Pattern.matches(
                "^[A-Za-zÀ-ÿ ]+$",
                nome
        )) {

            throw new IllegalArgumentException(
                    "Nome contém caracteres inválidos."
            );
        }
    }

    public static void validarEmail(String email) {

        if (email == null || email.isBlank()) {

            throw new IllegalArgumentException(
                    "Email é obrigatório."
            );
        }

        if (!Pattern.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
                email
        )) {

            throw new IllegalArgumentException(
                    "Email inválido."
            );
        }
    }

    public static void validarTelefone(
            String telefone
    ) {

        String numeros =
                telefone.replaceAll("\\D", "");

        if (numeros.length() < 10 ||
                numeros.length() > 11) {

            throw new IllegalArgumentException(
                    "Telefone inválido."
            );
        }
    }

    public static void validarCPF(
            String cpf
    ) {

        String numeros =
                cpf.replaceAll("\\D", "");

        if (numeros.length() != 11) {

            throw new IllegalArgumentException(
                    "CPF deve possuir 11 dígitos."
            );
        }
    }
}