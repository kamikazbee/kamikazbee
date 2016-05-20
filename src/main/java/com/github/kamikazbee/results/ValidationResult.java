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

package com.github.kamikazbee.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {

    private Map<String, Object> errors = new HashMap<>();

    public Map<String, Object> getErrors() {
        return errors;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public void add(ValidationResult nested, String field) {
        errors.compute(field, (s, o) -> {
           if (o == null) {
               return nested.getErrors();
           } else {
               return o;
               // TODO: Handle merging
           }
        });
    }

    public void add(com.github.kamikazbee.results.RuleResponse response, String field) {
        errors.compute(field, (s, o) -> {
            if (o == null) {
                List<String> l = new ArrayList<>(1);
                l.add(response.getMessage());
                return l;
            } else {
                ((List)o).add(response.getMessage());
                return o;
            }
        });
    }

}
