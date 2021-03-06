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

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.benchmark.core.AbstractBenchmark;
import org.jboss.weld.benchmark.core.DummyEvent;
import org.jboss.weld.benchmark.core.DummyQualifier;
import org.jboss.weld.benchmark.core.event.MultipleEventTypesDispatchedToSimpleStaticObserverBenchmark.Dispatcher;

/**
 * This benchmark uses the same Event instance to dispatch two event types. It verifies, that performance does not drop significantly
 * when simple caching cannot be used.
 *
 * @author Jozef Hartinger
 */
public class MultipleEventTypesDispatchedToSimpleStaticObserverBenchmark extends AbstractBenchmark<Dispatcher> {

    private static final String MARKER = "multipleEventTypesDispatchedToStatic";

    @Override
    protected Class<Dispatcher> getBeanClass() {
        return Dispatcher.class;
    }

    public static class ExtendedEvent extends DummyEvent {

        private static final ExtendedEvent INSTANCE = new ExtendedEvent();

        public ExtendedEvent() {
            super(true);
        }
    }

    public static class Dispatcher extends AbstractDispatcher {

        @Inject
        public Dispatcher(@DummyQualifier(MARKER) Event<DummyEvent> event) {
            super(event);
        }

        @Override
        public boolean getResult() {
            event.fire(EVENT);
            event.fire(ExtendedEvent.INSTANCE);
            return true;
        }

    }

    public static class Receiver {

        public static void dummyEventListener(@Observes @DummyQualifier(MARKER) DummyEvent dummyEvent) {
            if (!dummyEvent.value()) {
                throw new IllegalStateException();
            }
        }
    }
}
