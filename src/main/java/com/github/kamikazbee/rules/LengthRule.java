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

import java.util.function.Predicate;

public class LengthRule extends RuleImpl<String, LengthRule> {

    private Number min;
    private Number max;
    private Number exact;

    public LengthRule min(Number min) {
        this.min = min;
        return this;
    }

    public LengthRule max(Number max) {
        this.max = max;
        return this;
    }

    public LengthRule exact(Number exact) {
        this.exact = exact;
        return this;
    }

    @Override
    protected String getMessage() {
        String superMessage = super.getMessage();
        if(superMessage != null) {
            return superMessage;
        }

        StringBuilder b = new StringBuilder();

        if (exact != null) {
            b.append("Must be exactly ").append(exact);
        } else {
            if (min != null) {
                b.append("Cannot be less than ").append(min);
                if (max != null) {
                    b.append(" or more than ").append(max);
                }
            } else if (max != null) {
                b.append("Cannot be more than ").append(max);
            }
        }
        if (exact != null || min != null || max != null) {
            b.append(" characters long");
        }

        return b.toString();
    }

    @Override
    protected Predicate<String> getValidation() {
        return s -> {
            boolean valid = true;

            if (exact != null) {
                valid = s.length() == exact.doubleValue();
            } else {
                if (min != null) {
                    valid = s.length() >= min.doubleValue();
                }

                if (max != null) {
                    valid = valid && s.length() <= max.doubleValue();
                }
            }

            return valid;
        };
    }
}
