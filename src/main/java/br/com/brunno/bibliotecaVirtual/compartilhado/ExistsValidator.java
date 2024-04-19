package br.com.brunno.bibliotecaVirtual.compartilhado;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ExistsValidator implements ConstraintValidator<Exists, Object> {

    @Autowired
    private EntityManager entityManager;

    private String domainField;
    private Class<?> klass;

    @Override
    public void initialize(Exists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.klass = constraintAnnotation.domain();
        this.domainField = constraintAnnotation.domainField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(o)) return true;
        List resultList = entityManager.createQuery("SELECT o FROM " + klass.getName() + " o WHERE " + domainField + " = :value")
                .setParameter("value", o)
                .getResultList();
        return !resultList.isEmpty();
    }
}
