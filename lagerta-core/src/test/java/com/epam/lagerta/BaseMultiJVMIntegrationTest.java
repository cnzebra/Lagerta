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

package com.epam.lagerta;

import com.epam.lagerta.cluster.DifferentJVMClusterManager;
import com.epam.lagerta.cluster.IgniteClusterManager;
import com.epam.lagerta.resources.FullClusterResource;
import com.google.common.util.concurrent.Uninterruptibles;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public abstract class BaseMultiJVMIntegrationTest extends BaseIntegrationTest {

    private static final DifferentJVMClusterManager JVM_CLUSTER_MANAGER = new DifferentJVMClusterManager();

    @BeforeSuite
    public void setUp() throws Exception {
        ALL_RESOURCES.setClusterManager(JVM_CLUSTER_MANAGER);
        ALL_RESOURCES.setUp();
    }

    public void stopNodeWithService(String serviceName) {
        JVM_CLUSTER_MANAGER.getIgniteStopper().stopServerNodesWithService(serviceName);
    }

    public void waitShutdownOneNode() {
        do {
            Uninterruptibles.sleepUninterruptibly(IgniteClusterManager.AWAIT_TIME, TimeUnit.MILLISECONDS);
        } while (FullClusterResource.CLUSTER_SIZE - 1 != JVM_CLUSTER_MANAGER.getCountAliveServerNodes());
    }
}
