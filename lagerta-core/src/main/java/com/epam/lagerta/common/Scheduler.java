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
package com.epam.lagerta.common;

import org.apache.ignite.IgniteInterruptedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BooleanSupplier;

public class Scheduler {

    private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final List<Runnable> rules = new ArrayList<>();

    private volatile boolean running = false;

    /**
     * thread-safe operation
     *
     * @param task - closure to run once
     */
    public void pushTask(Runnable task) {
        tasks.add(task);
    }

    /**
     * non thread-safe operation
     *
     * @param rule closure to run every cycle
     */
    public void registerRule(Runnable rule) {
        rules.add(rule);
    }

    /**
     * non thread-safe operation per timeInterval
     *
     * @param timeInterval time interval to run rule
     */
    public PeriodicRule.Builder per(long timeInterval) {
        return new PeriodicRule.Builder(this, timeInterval);
    }

    /**
     * non thread-safe operation when predicate return true
     *
     * @param predicate condition
     */
    public PredicateRule.Builder when(BooleanSupplier predicate) {
        return new PredicateRule.Builder(this, predicate);
    }

    public void stop() {
        this.running = false;
    }

    public void execute() {
        running = true;
        while (running) {
            try {
                int size = tasks.size();
                for (int i = 0; i < size; i++) {
                    tasks.poll().run();
                }
                rules.forEach(Runnable::run);
            } catch (IgniteInterruptedException e) {
                running = false;
            } catch (Exception e) {
                LOG.error("Error while running a bunch of tasks", e);
            }
        }
    }
}
