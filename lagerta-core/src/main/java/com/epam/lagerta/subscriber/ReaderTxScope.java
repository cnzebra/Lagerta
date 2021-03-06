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
package com.epam.lagerta.subscriber;

import com.epam.lagerta.capturer.TransactionScope;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReaderTxScope extends TransactionScope {

    private final UUID readerId;

    private boolean orphan;
    private boolean inProgress;

    public ReaderTxScope(UUID readerId, long transactionId, List<Map.Entry<String, List>> scope) {
        super(transactionId, scope);
        this.readerId = readerId;
    }

    public UUID getReaderId() {
        return readerId;
    }

    public boolean isOrphan() {
        return orphan;
    }

    public void markOrphan() {
        orphan = true;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void markInProgress() {
        inProgress = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ReaderTxScope)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return getTransactionId() == ((ReaderTxScope) obj).getTransactionId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getTransactionId());
    }
}
