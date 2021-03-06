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

import com.epam.lagerta.cluster.AppContextOneProcessClusterManager;
import org.testng.annotations.BeforeSuite;

import static com.epam.lagerta.resources.FullClusterResource.CONFIG_XML;

public abstract class BaseSingleJVMIntegrationTest extends BaseIntegrationTest {

    private AppContextOneProcessClusterManager clusterManager;

    @BeforeSuite
    public void setUp() throws Exception {
        clusterManager = new AppContextOneProcessClusterManager(CONFIG_XML);
        ALL_RESOURCES.setClusterManager(clusterManager);
        ALL_RESOURCES.setUp();
    }

    protected <T> T getBean(Class<T> clazz) {
        return clusterManager.getBean(clazz);
    }
}
