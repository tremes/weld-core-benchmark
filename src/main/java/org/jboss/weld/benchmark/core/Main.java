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

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Main {

    private static final int FORKS = 5;
    private static final int ITERATIONS = 5;
    private static final int BATCH_SIZE = 1 << 15;

    public static void main(String[] args) throws RunnerException {

        String containerName = System.getProperty("container.name", "unknown");
        String containerVersion = System.getProperty("container.version", "unknown");


        Options opt = new OptionsBuilder().include("org.jboss.weld.benchmark.core")
                .forks(FORKS)
                .threads(1)
                .warmupBatchSize(BATCH_SIZE)
                .warmupIterations(ITERATIONS)
                .measurementBatchSize(BATCH_SIZE)
                .measurementIterations(ITERATIONS)
                .resultFormat(ResultFormatType.CSV)
                .result(containerName + "-" + containerVersion + ".csv")
                .build();
        new Runner(opt).run();
    }
}
