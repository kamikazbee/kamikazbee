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

package com.github.kamikazbee

import spock.lang.Specification

class ValidationResultTest extends Specification {

    private final static String ERROR_MESSAGE = "error message"

    def "add rule response when no errors for field"() {
        ValidationResult result = new ValidationResult()
        result.add(RuleResponse.failure(ERROR_MESSAGE), "foo")

        expect:
        !result.isValid()
        result.errors == [foo: [ERROR_MESSAGE]]
    }

    def "add rule response when already existing error for field"() {
        ValidationResult result = new ValidationResult(errors: [foo: [ERROR_MESSAGE]])
        result.add(RuleResponse.failure(ERROR_MESSAGE), "foo")

        expect:
        !result.isValid()
        result.errors == [foo: [ERROR_MESSAGE, ERROR_MESSAGE]]
    }

    def "add nested validation result when no errors for field"() {
        ValidationResult result = new ValidationResult()
        result.add(new ValidationResult(errors: [bar: [ERROR_MESSAGE]]), 'foo')

        expect:
        !result.isValid()
        result.errors == [foo: [bar: [ERROR_MESSAGE]]]
    }

}
