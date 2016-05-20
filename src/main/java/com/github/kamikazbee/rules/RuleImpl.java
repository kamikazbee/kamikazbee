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

package com.github.kamikazbee.rules;

import com.github.kamikazbee.results.RuleResponse;

import java.util.function.Predicate;

abstract class RuleImpl<T, R extends Rule> implements Rule<T, R> {

    private String message;
    private Predicate<T> validation;
    private boolean blocking = false;
    private boolean optional = true;

    @Override
    public R message(String message) {
        this.message = message;
        return (R) this;
    }

    protected R validation(Predicate<T> validation) {
        this.validation = validation;
        return (R) this;
    }

    @Override
    public R blocking(boolean blocking) {
        this.blocking = blocking;
        return (R) this;
    }

    @Override
    public R optional(boolean optional) {
        this.optional = optional;
        return (R) this;
    }

    @Override
    public RuleResponse validate(T value) {
        if (skipValidation(value) || getValidation().test(value)) {
            return RuleResponse.success();
        } else {
            return RuleResponse.failure(getMessage());
        }
    }

    protected boolean skipValidation(T value) {
        return optional && value == null;
    }

    public boolean isBlocking() {
        return blocking;
    }

    protected String getMessage() {
        return message;
    }

    protected Predicate<T> getValidation() {
        return validation;
    }
}
