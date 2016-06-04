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

import java.util.function.Function

import static com.github.kamikazbee.rules.Rules.empty
import static com.github.kamikazbee.rules.Rules.nonNull

class ValidatorTest extends Specification {

    Validator validator = new Validator()

    def "validate no validations"() {
        expect:
        validator.validate(null).isValid()
    }

    def "validate base rule"() {
        ValidationResult result = validator
                .addRule(nonNull(), Function.identity())
                .validate(null)

        expect:
        !result.isValid()
        result.errors == [base: ['Cannot be null']]
    }

    def "validate base multiple rules"() {
        ValidationResult result = validator
                .addRule(nonNull(), Function.identity())
                .addRule(empty(), Function.identity())
                .validate(null)

        expect:
        !result.isValid()
        result.errors == [base: ['Cannot be null', 'Cannot be empty or null']]
    }

    def "validate multiple rules with blocking"() {
        ValidationResult result = validator
                .addRule(nonNull().blocking(true), Function.identity())
                .addRule(empty(), Function.identity())
                .validate(null)

        expect:
        !result.isValid()
        result.errors == [base: ['Cannot be null']]
    }

    def "validate rule with field"() {
        ValidationResult result = validator
                .addRule(nonNull(), Function.identity(), "foo")
                .validate(null)

        expect:
        !result.isValid()
        result.errors == [foo: ['Cannot be null']]
    }

    def "validate nested"() {
        Foo foo = new Foo(bar: new Bar())
        ValidationResult result = new Validator<Foo>()
                .addNestedValidator(new Validator<Bar>().addRule(nonNull(), {b -> b.name}, 'name'), {f -> f.bar}, 'bar')
                .validate(foo)

        expect:
        !result.isValid()
        result.errors == [bar: [name: ['Cannot be null']]]
    }

    def "validate list"() {
        Foo foo = new Foo(bars: [new Bar(), new Bar(name: 'test'), new Bar()])
        ValidationResult result = new Validator<Foo>()
                .addListValidator(new Validator<Bar>().addRule(nonNull(), {b -> b.name}, 'name'), {f -> f.bars}, 'bars')
                .validate(foo)

        expect:
        !result.isValid()
        result.errors == ['bars[0]': [name: ['Cannot be null']], 'bars[2]': [name: ['Cannot be null']]]
    }

    def "validate merge different rules"() {
        Foo foo = new Foo(bars: [new Bar(), new Bar(name: 'test', address: '2 test street')])
        ValidationResult result = new Validator<Foo>()
                .addListValidator(new Validator<Bar>().addRule(nonNull(), { b -> b.name }, 'name'), { f -> f.bars }, 'bars')
                .addListValidator(new Validator<Bar>().addRule(empty(), { b -> b.name }, 'name'), { f -> f.bars }, 'bars')
                .addListValidator(new Validator<Bar>().addRule(nonNull(), { b -> b.address }, 'address'), { f -> f.bars }, 'bars')
                .validate(foo)

        expect:
        !result.isValid()
        result.errors == ['bars[0]': [name: ['Cannot be null', 'Cannot be empty or null'], address: ['Cannot be null']]]
    }

    def "validate merge same rules"() {
        Foo foo = new Foo(bars: [new Bar(), new Bar(name: 'test')])
        ValidationResult result = new Validator<Foo>()
                .addListValidator(new Validator<Bar>().addRule(nonNull(), { b -> b.name }, 'name'), { f -> f.bars }, 'bars')
                .addListValidator(new Validator<Bar>().addRule(nonNull(), { b -> b.name }, 'name'), { f -> f.bars }, 'bars')
                .validate(foo)

        expect:
        !result.isValid()
        result.errors == ['bars[0]': [name: ['Cannot be null']]]
    }

    private class Foo {
        Bar bar
        List<Bar> bars
    }

    private class Bar {
        String name
        String address
    }

}
