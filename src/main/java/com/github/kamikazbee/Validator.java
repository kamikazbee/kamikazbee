/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.kamikazbee;

import com.github.kamikazbee.rules.Rule;

import java.util.*;
import java.util.function.Function;

public class Validator<T> {

    private List<Validation> validations = new ArrayList<>();

    public <R> Validator<T> addRule(Rule<R, ? extends Rule> r, Function<T, R> f, String field) {
        validations.add(new Validation(r, f, field));
        return this;
    }

    public <R> Validator<T> addRule(Rule<R, ? extends Rule> r, Function<T, R> f) {
        validations.add(new Validation(r, f));
        return this;
    }

    public <R> Validator<T> addNestedValidator(Validator<R> v, Function<T, R> f, String field) {
        validations.add(new Validation(v, f, field));
        return this;
    }

    public <R> Validator<T> addListValidator(Validator<R> v, Function<T, Collection<R>> f, String field) {
        validations.add(new Validation(v, f, field));
        return this;
    }

    public ValidationResult validate(T value) {
        ValidationResult result = new ValidationResult();

        for (Validation validation : validations) {

            Rule rule = validation.getRule();
            Function valueProvider = validation.getValueProvider();

            if (validation.isNested()) {
                validateNested(validation, value, result);
            } else {
                RuleResponse response = rule.validate(valueProvider.apply(value));

                if (!response.isSuccess()) {
                    result.add(response, validation.getField());
                    if (validation.getRule().isBlocking()) {
                        break;
                    }
                }
            }

        }
        return result;
    }

    private void validateNested(Validation validation , T value, ValidationResult result) {
        Object toBeValidated = validation.getValueProvider().apply(value);
        if (toBeValidated != null) {
            if (toBeValidated instanceof Collection) {
                Object[] x = ((Collection) toBeValidated).toArray();
                for (int i = 0; i < x.length; i++) {
                    ValidationResult nestedResult = validation.getValidator().validate(x[i]);
                    if (!nestedResult.isValid()) {
                        result.add(nestedResult, validation.getField() + "[" + i + "]");
                    }
                }
            } else {
                ValidationResult nestedResult = validation.getValidator().validate(toBeValidated);
                if (!nestedResult.isValid()) {
                    result.add(nestedResult, validation.getField());
                }
            }
        }
    }

}
