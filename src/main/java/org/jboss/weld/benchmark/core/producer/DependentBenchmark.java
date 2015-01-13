/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.weld.benchmark.core.producer;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.weld.benchmark.core.AbstractBenchmark;
import org.jboss.weld.benchmark.core.BeanUnderTest;
import org.jboss.weld.benchmark.core.DummyQualifier;
import org.jboss.weld.benchmark.core.producer.DependentBenchmark.DependentConsumer;

/**
 * @author Kirill Gaevskii
 */
public class DependentBenchmark extends AbstractBenchmark<DependentConsumer> {

    @Override
    protected Class<DependentConsumer> getBeanClass() {
        return DependentConsumer.class;
    }

    public static class DependentConsumer implements BeanUnderTest {

        @Inject
        @DummyQualifier("DependentProducer")
        private Instance<SimpleBean> instance;

        @Override
        public boolean getResult() {
            SimpleBean sb = instance.get();
            instance.destroy(sb);
            return true;
        }
    }
}
