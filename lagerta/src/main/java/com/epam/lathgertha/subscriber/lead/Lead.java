/*
 *  Copyright 2017 EPAM Systems.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.epam.lathgertha.subscriber.lead;

import com.epam.lathgertha.capturer.TransactionScope;

import java.util.List;
import java.util.UUID;

public interface Lead {

    /**
     * notifies lead that transactions has been read
     *
     * @param consumerId id of consumer node
     * @param txScopes   sorted transaction scopes read by the consumer
     * @return transactions ids that can be committed
     */
    List<Long> notifyRead(UUID consumerId, List<TransactionScope> txScopes);

    /**
     * notifies lead that transactions has been committed
     *
     * @param consumerId id of consumer node
     * @param ids sorted list of committed ids
     */
    void notifyCommitted(UUID consumerId, List<Long> ids);

    void notifyFailed(Long id);

    void stop();

    void execute();

    long getLastDenseCommitted();
}
