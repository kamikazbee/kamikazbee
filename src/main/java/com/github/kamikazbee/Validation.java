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

import java.util.function.Function;

/**
 * Object denoting a specific validation that should take place
 */
public class Validation {

    private Rule rule;
    private Function valueProvider;
    private Validator validator;
    private String field = "base";

    /**
     * Create a new Validation for an object
     * @param rule Rule that should be applied
     * @param valueProvider Function that returns the value that should be validated
     * @param field the field in which the error will be assigned in Rule validation fails
     */
    public Validation(Rule rule, Function valueProvider, String field) {
        this.rule = rule;
        this.valueProvider = valueProvider;
        if (field != null) {
            this.field = field;
        }
    }

    /**
     * Create a new Validation for an object where the error (if any) will be assigned to the "base" field
     * @param rule Rule that should be applied
     * @param valueProvider Function that returns the value that should be validated
     */
    public Validation(Rule rule, Function valueProvider) {
        this.rule = rule;
        this.valueProvider = valueProvider;
    }

    /**
     * Create a new Validation that will be applied to a nested object of the object to be validated
     * @param validator Validator that will be used to validate the nested resource
     * @param valueProvider Function that returns the value that should be validated
     * @param field the field in which the error will be assigned in Rule validation fails
     */
    public Validation(Validator validator, Function valueProvider, String field) {
        this.validator = validator;
        this.valueProvider = valueProvider;
        this.field = field;
    }

    boolean isNested() {
        return validator != null;
    }

    Rule getRule() {
        return rule;
    }

    Function getValueProvider() {
        return valueProvider;
    }

    Validator getValidator() {
        return validator;
    }

    String getField() {
        return field;
    }

}
