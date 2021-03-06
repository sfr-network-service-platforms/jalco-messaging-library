/**
 * Copyright 2012,2013 - SFR (http://www.sfr.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sfr.tv.messaging.api;

/**
 * Consumer subscription attributes. Mostly inspired from the JMS model.
 * 
 * @author matthieu.chaplin@sfr.com
 */
public class SubscriptionDescriptor {

    private final String destination;
    
    private final boolean isTopicSubscription;
    
    private final boolean isDurableSubscription;
    
    private final String subscriptionName;
    
    private final String selector;
    
    public SubscriptionDescriptor(String destination, boolean isTopicSubscription, boolean isDurableSubscription, String subscriptionName, String selector) {
        this.destination = destination;
        this.isTopicSubscription = isTopicSubscription;
        this.isDurableSubscription = isDurableSubscription;
        this.subscriptionName = subscriptionName;
        this.selector = selector;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isIsTopicSubscription() {
        return isTopicSubscription;
    }

    public boolean isIsDurableSubscription() {
        return isDurableSubscription;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public String getSelector() {
        return selector;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("destination : ").append(destination)
                .append(", topic ? ").append(isTopicSubscription)
                .append(", durable subscription ? ").append(isDurableSubscription)
                .append(", subscription base name : ").append(subscriptionName)
                .append(selector != null && selector.trim().length() > 0 ? ", selector : ".concat(selector) : "");
        
        return sb.toString();
        
    }   
}