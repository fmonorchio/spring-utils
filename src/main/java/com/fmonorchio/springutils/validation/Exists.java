package com.fmonorchio.springutils.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Check if the annotated parameters exists.
 * The logic behind this is client dependant (need a custom type validator)
 * Useful for Spring Data entity validation by id.
 *
 * Example:
 *
 * <code>
 * @CheckParameters
 * @GetMapping("validation/worker/{id}")
 * public ResponseEntity<?> testValidationWorker(@PathVariable @Exists(as = Worker.class) String id) {
 *     return ResponseEntity.ok(id);
 * }
 * </code>
 *
 * */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Exists {

    /**
     * The class on which to apply validation
     * */
    Class<? extends Validable> as();

}