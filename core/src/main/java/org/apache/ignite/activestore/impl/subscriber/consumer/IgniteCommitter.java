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

package org.apache.ignite.activestore.impl.subscriber.consumer;

import javax.inject.Inject;
import org.apache.ignite.Ignite;
import org.apache.ignite.activestore.subscriber.Committer;
import org.apache.ignite.activestore.subscriber.TransactionSupplier;
import org.apache.ignite.lang.IgniteInClosure;
import org.eclipse.collections.api.iterator.LongIterator;
import org.eclipse.collections.api.list.primitive.LongList;

/**
 * @author Aleksandr_Meterko
 * @since 11/30/2016
 */
public class IgniteCommitter extends AbstractIgniteCommitter implements Committer {

    @Inject
    public IgniteCommitter(Ignite ignite) {
        super(ignite);
    }

    @Override public void commitAsync(
            LongList txIds,
            TransactionSupplier txSupplier,
            IgniteInClosure<Long> onSingleCommit, IgniteInClosure<LongList> onFullCommit
    ) {
        for (LongIterator it = txIds.longIterator(); it.hasNext(); ) {
            singleCommit(txSupplier, onSingleCommit, it.next());
        }
        onFullCommit.apply(txIds);
    }
}
