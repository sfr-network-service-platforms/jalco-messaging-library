/*
 * Copyright 2015 matthieu.chaplin@sfr.com.
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
package net.sfr.tv.messaging.api;

/**
 * A property bound to a message. (Distinct from the body part)
 * 
 * @author matthieu.chaplin@sfr.com
 */
public class MessageProperty {

    public enum Type {
        STRING,
        LONG
    }
    
    public final Type type;
    
    public final String name;
    
    public final Object value;
    
    public MessageProperty(final Type type, final String name, final Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}
