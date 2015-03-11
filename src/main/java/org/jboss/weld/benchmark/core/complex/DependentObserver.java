package org.jboss.weld.benchmark.core.complex;

import javax.enterprise.event.Observes;

import org.jboss.weld.benchmark.core.DummyEvent;

public class DependentObserver {

    public void observesDependent(@Observes DummyEvent event, DependentBean dependentBean) {
        dependentBean.ping();
    }

}
