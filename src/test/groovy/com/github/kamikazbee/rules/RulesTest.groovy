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

import spock.lang.Shared
import spock.lang.Specification

class RulesTest extends Specification {

    @Shared rules = new Rules()

    def "Length"() {
        Rule rule = rules.length()

        expect:
        rule instanceof LengthRule
        rule.min == null
        rule.max == null
        rule.exact == null
        !rule.isBlocking()
        rule.skipValidation(null)
        !rule.skipValidation("")
    }

    def "Empty"() {
        Rule rule = rules.empty()

        expect:
        rule instanceof EmptyRule
        !rule.isBlocking()
        !rule.skipValidation(null)
        !rule.skipValidation("")
    }

    def "NonNull"() {
        Rule rule = rules.nonNull()

        expect:
        rule instanceof NonNullRule
        !rule.isBlocking()
        !rule.skipValidation(null)
        !rule.skipValidation("")
    }

    def "Quantity"() {
        Rule rule = rules.quantity()

        expect:
        rule instanceof QuantityRule
        rule.min == null
        rule.max == null
        rule.exact == null
        !rule.isBlocking()
        rule.skipValidation(null)
        !rule.skipValidation(0)
    }

    def "Pattern"() {
        Rule rule = rules.pattern()

        expect:
        rule instanceof RegexRule
        !rule.isBlocking()
        rule.skipValidation(null)
        !rule.skipValidation("")
    }

    def "Email"() {
        Rule rule = rules.email()

        expect:
        rule instanceof EmailRule
        !rule.isBlocking()
        rule.skipValidation(null)
        !rule.skipValidation("")
    }
}
