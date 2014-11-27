/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.benchmark.core;

import org.jboss.weld.benchmark.core.event.SimpleStaticObserverBenchmark;

/*
 * To profile:
 * 1) Change #getBenchmark() so that it runs the benchmark you want to profile
 * 2) Run mvn clean install -PWeld -Dprofile
 * 3) Attach JMC
 */
public class Profile {

    private static AbstractBenchmark<?> getBenchmark() {
        return new SimpleStaticObserverBenchmark();
    }

    public static void main(String[] args) {
        final Container container = new Container();
        container.setup();

        final AbstractBenchmark<?> benchmark = getBenchmark();
        benchmark.setup(container);

        try {
            while (true) {
                benchmark.run();
            }
        } finally {
            benchmark.tearDown();
            container.tearDown();
        }
    }
}
