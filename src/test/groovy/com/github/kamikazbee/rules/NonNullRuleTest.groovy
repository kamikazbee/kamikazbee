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

class NonNullRuleTest extends Specification {

    NonNullRule rule = new NonNullRule()

    def "rule cases"() {
        expect:
        rule.validate(value).isSuccess() == isValid

        where:
        value           || isValid
        null            || false
        ""              || true
        0               || true
        new Object()    || true
    }

    def "rule message"() {
        expect:
        rule.message.equals("Cannot be null")
        rule.message("test").message.equals("test")
    }

}
