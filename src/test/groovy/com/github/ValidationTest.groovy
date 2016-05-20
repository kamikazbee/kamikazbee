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

package com.github

import com.github.kamikazbee.Validation
import com.github.kamikazbee.Validator
import spock.lang.Specification

class ValidationTest extends Specification {

    def "IsNested"() {
        setup:
        Validation validation = new Validation(validator, null, null)

        expect:
        validation.isNested() == isNested

        where:
        validator       || isNested
        null            || false
        new Validator() || true
    }

    def "field value"() {
        setup:
        Validation validation = new Validation(null, null, field)

        expect:
        validation.getField().equals(expected)

        where:
        field  || expected
        null   || "base"
        ""     || ""
        "test" || "test"
    }
}
