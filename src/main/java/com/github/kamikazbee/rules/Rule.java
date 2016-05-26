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

import com.github.kamikazbee.RuleResponse;

/**
 * The Rule is the base class of the validation library.
 * It is in simple words the check that will be applied in
 * order to determine whether a value is valid or not.
 *
 * @param <T> The Class of the value to be validated
 * @param <R> A Rule Class itself, used for generic purposes
 */
public interface Rule<T, R extends Rule> {

    /**
     * Define the custom error message added in the {@link RuleResponse} when the Rule validation fails.
     * @param message String with custom error message
     * @return the Rule
     */
    R message(String message);

    /**
     * Define whether the Rule is blocking or not.
     * <p>
     * When a blocking Rule is being processed in the Rules chain and fails,
     * the validation immediately stops for the given Object (at the given level)
     * and immediately returns without continuing to the next Rules.
     *
     * @param blocking boolean denoting whether Rule is blocking or null
     * @return the Rule
     */
    R blocking(boolean blocking);

    /**
     * Define whether the Rule is optional or not.
     * <p>
     * An optional Rule is being skipped when the value to be validated is null.
     *
     * @param optional boolean denoting whether Rule is optional
     * @return the Rule
     */
    R optional(boolean optional);

    /**
     * Validate a given value using this Rule
     *
     * @param value object to be validated
     * @return RuleResponse containing the result of the Rule application
     */
    RuleResponse validate(T value);

    /**
     * Check whether the Rule is {@link Rule#blocking(boolean) blocking} or not.
     *
     * @return boolean to determine whether Rule is blocking
     */
    boolean isBlocking();

}
