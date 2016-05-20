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
import java.util.regex.Pattern;

public class RegexRule extends RuleImpl<String, RegexRule> {

    private String pattern;
    private int flags;

    public RegexRule pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public RegexRule flags(int flags) {
        this.flags = flags;
        return this;
    }

    @Override
    protected Predicate<String> getValidation() {
        return Pattern.compile(pattern, flags).asPredicate();
    }

    @Override
    protected String getMessage() {
        String superMessage = super.getMessage();
        if(superMessage != null) {
            return superMessage;
        } else {
            return "Pattern does not match given value";
        }
    }
}
