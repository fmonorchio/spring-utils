package com.fmonorchio.springutils.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Apply the validators to the parameters of the annotated method.
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
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface CheckParameters {
}