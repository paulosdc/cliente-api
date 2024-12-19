package challenge.omie.clientes.utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.stereotype.Service;

import challenge.omie.clientes.domain.categoria.CategoriaDTO;
import challenge.omie.clientes.domain.cliente.ClienteDTO;
import challenge.omie.clientes.domain.email.EmailDTO;

@Service
public class ValidationUtils {
    private boolean isValidCpfOrCnpj(String inscricao) {
        return inscricao != null && (isValidCpf(inscricao) || isValidCnpj(inscricao));
    }

    public static boolean isValidCpf(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) {
                firstDigit = 0;
            }

            if (firstDigit != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) {
                secondDigit = 0;
            }

            return secondDigit == Character.getNumericValue(cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static boolean isValidCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
            }
            int firstDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

            if (firstDigit != Character.getNumericValue(cnpj.charAt(12))) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
            }
            int secondDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

            return secondDigit == Character.getNumericValue(cnpj.charAt(13));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public List<String> isValidDTO(ClienteDTO clienteDTO){
        List<String> erros = new ArrayList<>();

        if (!isValidCpfOrCnpj(clienteDTO.getInscricao())) {
            erros.add("Inscrição inválida. Deve ser um CPF ou CNPJ válido.");
        }

        if(clienteDTO.getNome().isEmpty() || !clienteDTO.getNome().matches("[a-zA-Z]+")){
            erros.add("O nome está vazio ou possui caracteres inválidos.");
        }

        if(clienteDTO.getUrlFoto().isEmpty()){
            erros.add("Preencha o email.");
        }

        if(clienteDTO.getStatus() == null){
            erros.add("Defina o status");
        }
        
        return erros;
    }

    public List<String> isValidDTO(CategoriaDTO categoriaDTO){
        List<String> erros = new ArrayList<>();

        if(categoriaDTO.getNome().isEmpty()){
            erros.add("Defina o nome da categoria.");
        }

        return erros;
    }

    public List<String> isValidDTO(EmailDTO emailDTO){
        List<String> erros = new ArrayList<>();

        if(emailDTO.getNome().isEmpty()){
            erros.add("Defina o nome do dono do email.");
        }

        if(emailDTO.getEmail().isEmpty()){
            erros.add("Defina o email.");
        }

        if(emailDTO.getCategoria() == null){
            erros.add("Defina a categoria do email.");
        }

        return erros;
    }
}
