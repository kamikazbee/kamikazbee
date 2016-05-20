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

public class RuleResponse {

    private final String message;

    public RuleResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public RuleResponse() {
        this.message = null;
    }

    public boolean isSuccess() {
        return message == null;
    }

    public static RuleResponse success() {
        return new RuleResponse();
    }

    public static RuleResponse failure(String message) {
        return new RuleResponse(message);
    }

}