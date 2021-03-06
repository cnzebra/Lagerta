/*
 * Copyright (c) 2017. EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.activestore.impl.subscriber.lead;

import java.util.UUID;

import org.eclipse.collections.api.list.primitive.LongList;

/**
 * @author Evgeniy_Ignatiev
 * @since 12/14/2016 3:57 PM
 */
class UpdateInitialContextTask implements Runnable {
    private final UUID localLoaderId;
    private final LongList txIds;
    private final LeadContextLoader loader;

    UpdateInitialContextTask(UUID localLoaderId, LongList txIds, LeadContextLoader loader) {
        this.localLoaderId = localLoaderId;
        this.txIds = txIds;
        this.loader = loader;
    }

    @Override public void run() {
        loader.processCommittedTxs(localLoaderId, txIds);
    }
}
