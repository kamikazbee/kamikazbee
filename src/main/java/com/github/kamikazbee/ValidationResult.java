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

package com.github.kamikazbee;

import java.util.*;

class ValidationResult {

    private Map<String, Object> errors = new HashMap<>();

    Map<String, Object> getErrors() {
        return errors;
    }

    boolean isValid() {
        return errors.isEmpty();
    }

    void add(ValidationResult nested, String field) {
        errors.compute(field, (s, o) -> o == null ? nested.getErrors() : combine(o, nested.getErrors()));
    }

    void add(RuleResponse response, String field) {
        errors.compute(field, (s, o) -> {
            if (o == null) {
                List<String> l = new ArrayList<>(1);
                l.add(response.getMessage());
                return l;
            } else {
                ((List) o).add(response.getMessage());
                return o;
            }
        });
    }

    private Map<String, Object> combine(Object existing, Map<String, Object> toBeMerged) {
        if (existing == null) {
            return toBeMerged;
        } else if (existing instanceof List) {
            return merge(toList(existing), toBeMerged);
        } else {
            return merge(toMap(existing), toBeMerged);
        }
    }

    private List<String> merge(List<String> l1, List<String> l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }

        Set<String> merged = new HashSet<>(l1);
        merged.addAll(l2);
        return new ArrayList<>(merged);
    }

    private Map<String, Object> merge(Map<String, Object> m1, Map<String, Object> m2) {
        if (m1 == null) {
            return m2;
        } else if (m2 == null) {
            return m1;
        }

        Map<String, Object> merged = new HashMap<>();

        Set<String> keys = new HashSet<>(m1.keySet());
        keys.addAll(m2.keySet());

        keys.forEach(key -> {
            Object value1 = m1.get(key);
            Object value2 = m2.get(key);

            if (value1 instanceof List && value2 instanceof Map) {
                merged.put(key, merge(toList(value1), toMap(value2)));
            } else if (value1 instanceof Map && value2 instanceof List) {
                merged.put(key, merge(toList(value2), toMap(value1)));
            } else if (value1 instanceof List || value2 instanceof List) {
                merged.put(key, merge(toList(value1), toList(value2)));
            } else {
                merged.put(key, merge(toMap(value1), toMap(value2)));
            }
        });

        return merged;
    }

    private Map<String, Object> merge(List<String> list, Map<String, Object> map) {
        Map<String, Object> merged = new HashMap<>();
        if (map == null) {
            merged.put("base", list);
        } else {
            merged.putAll(map);
            if (list != null) {
                merged.compute("base", (key, value) -> merge(toList(value), list));
            }
        }
        return merged;
    }

    private List<String> toList(Object obj) {
        return Optional.ofNullable(obj).map(o -> (List<String>) o).orElse(null);
    }

    private Map<String, Object> toMap(Object obj) {
        return Optional.ofNullable(obj).map(o -> (Map<String, Object>) o).orElse(null);
    }

}
