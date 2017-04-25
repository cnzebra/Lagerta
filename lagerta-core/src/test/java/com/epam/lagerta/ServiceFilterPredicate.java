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

import com.epam.lagerta.cluster.IgniteClusterManager;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;

import static org.apache.ignite.internal.IgniteNodeAttributes.ATTR_GRID_NAME;

public class ServiceFilterPredicate implements IgnitePredicate<ClusterNode> {

    @Override
    public boolean apply(ClusterNode clusterNode) {
        return !IgniteClusterManager.CLIENT_GRID_NAME.equalsIgnoreCase(clusterNode.attribute(ATTR_GRID_NAME));
    }
}
