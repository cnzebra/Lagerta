/*
 * Copyright (c) 2017. EPAM Systems.
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

import com.epam.lagerta.base.jdbc.JDBCUtil;
import com.epam.lagerta.base.jdbc.common.PrimitivesHolder;
import com.epam.lagerta.capturer.DataCapturerLoader;
import com.epam.lagerta.resources.DBResource;
import com.epam.lagerta.resources.FullClusterResource;
import com.epam.lagerta.subscriber.Committer;
import org.apache.ignite.Ignite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import javax.sql.DataSource;
import java.sql.SQLException;
import org.testng.annotations.DataProvider;

public abstract class BaseIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIntegrationTest.class);
    private static final String DB_NAME = "testDB";
    private static final long TX_WAIT_TIME = 10_000;

    private static final DBResource DB_RESOURCE = new DBResource(DB_NAME);
    protected static final FullClusterResource ALL_RESOURCES = new FullClusterResource(DB_RESOURCE);
    protected static final String PRIMITIVES_CACHE_NAMES_PROVIDER = "primitivesCacheNamesProvider";


    private static int TEST_NUMBER = 0;

    protected DataSource dataSource;

    @SuppressWarnings("unused")
    private static Committer getJDBCCommitter() {
        return JDBCUtil.getJDBCCommitter(DB_RESOURCE.getDataSource());
    }

    @SuppressWarnings("unused")
    private static DataCapturerLoader getJDBCDataCapturerLoader() {
        return JDBCUtil.getJDBCDataCapturerLoader(DB_RESOURCE.getDataSource());
    }

    @DataProvider(name = PRIMITIVES_CACHE_NAMES_PROVIDER)
    public static Object[][] providePrimitivesCacheName() {
        return new Object[][] {
            new Object[] {PrimitivesHolder.CACHE},
            new Object[] {PrimitivesHolder.BINARY_KEEPING_CACHE}
        };
    }

    public static String adjustTopicNameForTest(String topic) {
        return topic + "_" + TEST_NUMBER;
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        ALL_RESOURCES.tearDown();
    }

    @BeforeMethod
    public void initializeResources() throws SQLException {
        DB_RESOURCE.initState(JDBCUtil.CREATE_TABLE_SQL_RESOURCE);
        dataSource = DB_RESOURCE.getDataSource();
    }

    @AfterMethod
    public void cleanupResources() throws SQLException {
        TEST_NUMBER++;
        ALL_RESOURCES.cleanUpClusters();
        DB_RESOURCE.clearState(JDBCUtil.DROP_TABLE_SQL_RESOURCE);
    }

    public static Ignite ignite() {
        return ALL_RESOURCES.igniteCluster().ignite();
    }

    public void awaitTransactions() throws InterruptedException {
        Thread.sleep(TX_WAIT_TIME);
        LOGGER.debug("[T] SLEPT {}", TX_WAIT_TIME);
    }
}
