package com.fmonorchio.springutils.validation;

/**
 * Interface to be implement for custom validation
 */
public interface Validator {

    /**
     *
     * @param validable
     * @return true if this validator support the current class to be validated
     */
    boolean supports(Class<? extends Validable> validable);

    /**
     *
     * @param arg the value to validate
     * @return true if validation passed
     */
    boolean isValid(Object arg);

}