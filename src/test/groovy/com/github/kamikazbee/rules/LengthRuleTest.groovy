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

class LengthRuleTest extends Specification {

    LengthRule rule = new LengthRule()

    def "rule cases [min]"() {
        expect:
        rule.min(min).validate(value).isSuccess() == isValid

        where:
        value |  min || isValid
        ""    | null || true
        ""    |    0 || true
        ""    |   -1 || true
        ""    |    1 || false
        "abc" | null || true
        "abc" |   -1 || true
        "abc" |    3 || true
        "abc" |    4 || false
    }

    def "rule cases [max]"() {
        expect:
        rule.max(max).validate(value).isSuccess() == isValid

        where:
        value |  max || isValid
        ""    | null || true
        ""    |    0 || true
        ""    |   -1 || false
        ""    |    1 || true
        "abc" | null || true
        "abc" |   -1 || false
        "abc" |    2 || false
        "abc" |    3 || true
    }

    def "rule cases [exact]"() {
        expect:
        rule.exact(exact).validate(value).isSuccess() == isValid

        where:
        value | exact || isValid
        ""    |  null || true
        ""    |     0 || true
        ""    |    -1 || false
        ""    |     1 || false
        "abc" |  null || true
        "abc" |     0 || false
        "abc" |    -1 || false
        "abc" |     3 || true
    }

    def "rule cases [min - max]"() {
        expect:
        rule.min(min).max(max).validate(value).isSuccess() == isValid

        where:
        value |  min |  max || isValid
        ""    | null | null || true
        ""    | null |    0 || true
        ""    | null |    1 || true
        ""    | null |   -1 || false
        ""    |    0 | null || true
        ""    |    1 | null || false
        ""    |   -1 | null || true
        ""    |    0 |    0 || true
        ""    |    0 |    1 || true
        ""    |    1 |    0 || false
        ""    |    1 |    2 || false
        "abc" | null | null || true
        "abc" |    0 |    2 || false
        "abc" |    0 |    3 || true
        "abc" |    3 |    3 || true
        "abc" |    3 |    4 || true
        "abc" |    4 |    3 || false
        "abc" |    4 |    4 || false
        "abc" |    3 |    2 || false
        "abc" |    4 |    5 || false
        "abc" | null |    2 || false
        "abc" | null |    3 || true
        "abc" | null |    4 || true
        "abc" |    2 | null || true
        "abc" |    3 | null || true
        "abc" |    4 | null || false
    }

    def "rule message"() {
        expect:
        rule.message(message).min(min).max(max).exact(exact).message.equals(expected)

        where:
        message | min  | max  | exact || expected
        null    | null | null |  null || ""
        ""      | null | null |  null || ""
        "test"  | null | null |  null || "test"
        "test"  |    0 |    5 |  null || "test"
        "test"  | null | null |     5 || "test"
        null    |    0 |    5 |  null || "Cannot be less than 0 or more than 5 characters long"
        null    |    0 | null |  null || "Cannot be less than 0 characters long"
        null    | null |    5 |  null || "Cannot be more than 5 characters long"
        null    | null | null |     6 || "Must be exactly 6 characters long"
        null    |    0 |    5 |     6 || "Must be exactly 6 characters long"
    }

}
