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
