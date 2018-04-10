package com.fmonorchio.springutils.validation;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.NoProviderFoundException;
import javax.validation.ValidationException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Data
@Aspect
@Component
public final class CheckParametersInterceptor {

    @Autowired(required = false)
    private List<Validator> validators;

    @PostConstruct
    public void init() {
        if (isNull(validators)) {
            validators = new LinkedList<>();
        }
    }

    @Before("@annotation(CheckParameters)")
    public void intercept(JoinPoint point) {

        Method method = ((MethodSignature) point.getSignature()).getMethod();

        Parameter[] params = method.getParameters();
        Object[] args = point.getArgs();

        boolean failed = IntStream.range(0, method.getParameterCount())
                .mapToObj(i -> Pair.of(params[i], args[i]))
                .filter(p -> p.getKey().isAnnotationPresent(Exists.class))
                .anyMatch(this::isCheckFailed);
        if (failed) {
            throw new ValidationException();
        }
    }

    private boolean isCheckFailed(Pair<Parameter, Object> pair) {
        Exists exists = pair.getKey().getAnnotation(Exists.class);
        Validator validator = getValidator(exists.as());
        return !validator.isValid(pair.getValue());
    }

    private Validator getValidator(Class<? extends Validable> validable) {
        return validators.stream()
                .filter(v -> v.supports(validable))
                .findFirst().orElseThrow(NoProviderFoundException::new);
    }

}