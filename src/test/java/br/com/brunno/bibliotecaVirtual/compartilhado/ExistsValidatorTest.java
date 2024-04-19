package br.com.brunno.bibliotecaVirtual.compartilhado;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExistsValidatorTest {

    ExistsValidator validator = new ExistsValidator();

    @DisplayName("Quando o valor for nulo, deve ignorar a validacao e considerar valido")
    @Test
    void test() {
        Assertions.assertTrue(validator.isValid(null, null));
    }

}