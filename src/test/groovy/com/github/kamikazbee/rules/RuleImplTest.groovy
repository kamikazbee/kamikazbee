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
