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
package org.jboss.weld.benchmark.core.event;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.EventMetadata;
import javax.inject.Inject;

import org.jboss.weld.benchmark.core.AbstractBenchmark;
import org.jboss.weld.benchmark.core.DummyEvent;
import org.jboss.weld.benchmark.core.DummyQualifier;
import org.jboss.weld.benchmark.core.SimpleApplicationScopedBean;
import org.jboss.weld.benchmark.core.SimpleDependentBean;
import org.jboss.weld.benchmark.core.event.ComplexObserverBenchmark.Dispatcher;

/**
 * Dispatches an event that is delivered to {@link RequestScoped} observer that injects
 * {@link EventMetadata} plus two additional simple beans ( one {@link ApplicationScoped} and one {@link Dependent} )
 *
 * @author Jozef Hartinger
 */
public class ComplexObserverBenchmark extends AbstractBenchmark<Dispatcher> {

    private static final String MARKER = "complexObserver";

    @Override
    protected Class<Dispatcher> getBeanClass() {
        return Dispatcher.class;
    }

    public static class Dispatcher extends AbstractDispatcher {

        @Inject
        public Dispatcher(@DummyQualifier(MARKER) Event<DummyEvent> event) {
            super(event);
        }
    }

    @RequestScoped
    public static class Receiver {

        public static void dummyEventListener(@Observes @DummyQualifier(MARKER) DummyEvent dummyEvent, EventMetadata metadata, SimpleApplicationScopedBean application, SimpleDependentBean dependent) {
            if (!dummyEvent.value() || metadata.getType() == null) {
                throw new IllegalStateException();
            }
        }
    }
}
