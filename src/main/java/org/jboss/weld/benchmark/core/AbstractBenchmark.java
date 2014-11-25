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

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.apache.deltaspike.cdise.api.ContextControl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Thread)
public abstract class AbstractBenchmark<T extends BeanUnderTest> {

    private ContextControl contextControl;
    protected T instance;

    @Setup
    public void setup(Container container) {
        contextControl = container.getContainer().getContextControl();
        contextControl.startContext(RequestScoped.class);
        instance = getReference(container.getContainer().getBeanManager());
    }

    @TearDown
    public void tearDown() {
        contextControl.stopContext(RequestScoped.class);
    }

    @SuppressWarnings("unchecked")
    private T getReference(BeanManager manager) {
        Bean<?> bean = manager.resolve(manager.getBeans(getBeanClass()));
        return (T) manager.getReference(bean, getBeanClass(), manager.createCreationalContext(bean));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public boolean run() {
        return instance.getResult();
    }

    protected abstract Class<T> getBeanClass();
}
