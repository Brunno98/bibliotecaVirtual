package br.com.brunno.bibliotecaVirtual.compartilhado;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    
    private final EntityManager entityManager;
    
    public UniqueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    private Class<?> klass;
    private String fieldName;
    
    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.klass = constraintAnnotation.domain();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        List<?> resultList = entityManager.createQuery("SELECT o FROM " + klass.getName() + " o " +
                        "WHERE o." + fieldName + " = :value")
                .setParameter("value", o)
                .getResultList();
        return resultList.isEmpty();
    }
}
