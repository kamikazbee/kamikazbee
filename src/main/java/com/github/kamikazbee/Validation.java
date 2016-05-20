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

public class Validation {

    private Rule rule;
    private Function valueProvider;
    private Validator validator;
    private String field = "base";

    public Validation(Rule rule, Function valueProvider, String field) {
        this.rule = rule;
        this.valueProvider = valueProvider;
        if (field != null) {
            this.field = field;
        }
    }

    public Validation(Rule rule, Function valueProvider) {
        this.rule = rule;
        this.valueProvider = valueProvider;
    }

    public Validation(Validator validator, Function valueProvider, String field) {
        this.validator = validator;
        this.valueProvider = valueProvider;
        this.field = field;
    }

    public boolean isNested() {
        return validator != null;
    }

    public Rule getRule() {
        return rule;
    }

    public Function getValueProvider() {
        return valueProvider;
    }

    public Validator getValidator() {
        return validator;
    }

    public String getField() {
        return field;
    }

}
