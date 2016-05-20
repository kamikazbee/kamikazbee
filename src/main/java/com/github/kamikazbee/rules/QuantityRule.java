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

import java.util.Objects;
import java.util.function.Predicate;

public class QuantityRule extends RuleImpl<Number, QuantityRule> {

    private Number exact;
    private Number min;
    private Number max;

    public QuantityRule min(Number min) {
        this.min = min;
        return this;
    }

    public QuantityRule max(Number max) {
        this.max = max;
        return this;
    }

    public QuantityRule exact(Number exact) {
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

        if (exact != null || min != null || max != null) {
            b.append("Quantity");
        }

        if (exact != null) {
            b.append(" must be exactly ").append(exact);
        } else {
            if (min != null) {
                b.append(" cannot be less than ").append(min);
                if (max != null) {
                    b.append(" and more than ").append(max);
                }
            } else if (max != null) {
                b.append(" cannot be more than ").append(max);
            }
        }

        return b.toString();
    }

    @Override
    protected Predicate<Number> getValidation() {
        return d -> {
            boolean valid = true;

            if (exact != null) {
                valid = Objects.equals(d, exact);
            } else {
                if (min != null) {
                    valid = d.doubleValue() >= min.doubleValue();
                }

                if (max != null) {
                    valid = valid && d.doubleValue() <= max.doubleValue();
                }
            }

            return valid;
        };
    }
}
