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

package com.github.kamikazbee.rules

import spock.lang.Specification

class QuantityRuleTest extends Specification {

    QuantityRule rule = new QuantityRule()

    def "rule cases [min]"() {
        expect:
        rule.min(min).validate(value).isSuccess() == isValid

        where:
        value | min   || isValid
        0     |  null || true
        0     |     0 || true
        0     |    -1 || true
        0     |     1 || false
        1L    |     0 || true
        1L    |    1L || true
        1L    |    2L || false
        0.005 | 0.004 || true
        0.005 | 0.005 || true
        0.005 | 0.006 || false
    }

    def "rule cases [max]"() {
        expect:
        rule.max(max).validate(value).isSuccess() == isValid

        where:
        value | max   || isValid
        0     |  null || true
        0     |     0 || true
        0     |    -1 || false
        0     |     1 || true
        1L    |     0 || false
        1L    |    1L || true
        1L    |    2L || true
        0.005 | 0.004 || false
        0.005 | 0.005 || true
        0.005 | 0.006 || true
    }

    def "rule cases [exact]"() {
        expect:
        rule.exact(exact).validate(value).isSuccess() == isValid

        where:
        value | exact || isValid
        0     |  null || true
        0     |     0 || true
        0     |    -1 || false
        0     |     1 || false
        1L    |     0 || false
        1L    |    1L || true
        1L    |    2L || false
        0.005 | 0.004 || false
        0.005 | 0.005 || true
        0.005 | 0.006 || false
    }

    def "rule cases [min - max]"() {
        expect:
        rule.min(min).max(max).validate(value).isSuccess() == isValid

        where:
        value | min  | max  || isValid
        0     | null | null || true
        0     |    0 |    0 || true
        0     |   -1 |    0 || true
        0     |   -1 |    1 || true
        0     |    0 |    1 || true
        0     |    1 |    0 || false
        0     |    1 |    1 || false
    }

    def "rule message"() {
        expect:
        rule.min(min).max(max).exact(exact).message(message).message.equals(expected)

        where:
        message | min  | max  | exact || expected
        null    | null | null | null  || ""
        ""      | null | null | null  || ""
        "test"  | null | null | null  || "test"
        "test"  |    0 |    5 | null  || "test"
        "test"  | null | null |   10  || "test"
        null    |    5 | null | null  || "Quantity cannot be less than 5"
        null    | null |    5 | null  || "Quantity cannot be more than 5"
        null    |    3 |    5 | null  || "Quantity cannot be less than 3 and more than 5"
        null    | null | null |   10  || "Quantity must be exactly 10"
        null    |    3 |    5 |   10  || "Quantity must be exactly 10"

    }

}
