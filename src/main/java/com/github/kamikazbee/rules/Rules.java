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

package com.github.kamikazbee.rules;

/**
 * Provides shorthands for the default library Rules
 */
public class Rules {

    /**
     * Shorthand to return a new {@link LengthRule} instance
     * @return a new instance of {@link LengthRule}
     */
    public static LengthRule length() {
        return new LengthRule();
    }

    /**
     * Shorthand to return a new {@link EmptyRule} instance
     * @return a new instance of {@link EmptyRule}
     */
    public static EmptyRule empty() {
        return new EmptyRule();
    }

    /**
     * Shorthand to return a new {@link NonNullRule} instance
     * @return a new instance of {@link NonNullRule}
     */
    public static NonNullRule nonNull() {
        return new NonNullRule();
    }

    /**
     * Shorthand to return a new {@link QuantityRule} instance
     * @return a new instance of {@link QuantityRule}
     */
    public static QuantityRule quantity() {
        return new QuantityRule();
    }

    /**
     * Shorthand to return a new {@link RegexRule} instance
     * @return a new instance of {@link RegexRule}
     */
    public static RegexRule pattern() {
        return new RegexRule();
    }

    /**
     * Shorthand to return a new {@link EmailRule} instance
     * @return a new instance of {@link EmailRule}
     */
    public static EmailRule email() {
        return new EmailRule();
    }

}
