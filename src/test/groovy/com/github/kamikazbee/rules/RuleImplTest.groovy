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

import java.util.function.Predicate

class RuleImplTest extends Specification {

    private class TestRule extends RuleImpl<Object, TestRule> {
        @Override
        public boolean skipValidation(Object value) {
            return super.skipValidation(value)
        }
    }

    private rule = new TestRule();

    def "custom message"() {
        String message = "test message"
        rule.message(message)

        expect:
        rule.message.equals(message)
    }

    def "no message"() {
        expect:
        rule.message == null
    }

    def "custom validation"() {
        Predicate validation = { o -> true }
        rule.validation(validation)

        expect:
        rule.getValidation().equals(validation)
    }

    def "default blocking"() {
        expect:
        !rule.isBlocking()
    }

    def "custom blocking"() {
        expect:
        rule.blocking(blocking).isBlocking() == result

        where:
        blocking || result
        true     || true
        false    || false
    }

    def "skip validation"() {
        expect:
        rule.optional(optional).skipValidation(value) == result

        where:
        optional | value || result
        true     | null  || true
        false    | null  || false
    }
}
