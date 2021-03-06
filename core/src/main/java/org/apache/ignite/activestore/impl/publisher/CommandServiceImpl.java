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

package org.apache.ignite.activestore.impl.publisher;

import java.util.UUID;

import javax.inject.Inject;
import org.apache.ignite.activestore.commons.injection.ActiveStoreService;
import org.apache.ignite.activestore.impl.config.ReplicaConfig;
import org.apache.ignite.activestore.publisher.CommandService;
import org.apache.ignite.services.ServiceContext;

/**
 * @author Evgeniy_Ignatiev
 * @since 2/1/2017 4:46 PM
 */
public class CommandServiceImpl extends ActiveStoreService implements CommandService {
    @Inject
    private transient CommanderService commander;

    @Override public void cancel(ServiceContext ctx) {
        commander.cancel();
    }

    @Override public void execute(ServiceContext ctx) throws Exception {
        commander.execute();
    }

    @Override public void register(UUID replicaId, ReplicaConfig replicaConfig, boolean isMain) {
        commander.register(replicaId, replicaConfig, isMain);
    }

    @Override public void registerAll(UUID[] ids, ReplicaConfig[] configs, UUID mainClusterId) {
        commander.registerAll(ids, configs, mainClusterId);
    }

    @Override public void notifyNodeUnsubscribedFromReplica(UUID replicaId) {
        commander.notifyNodeUnsubscribedFromReplica(replicaId);
    }
}
