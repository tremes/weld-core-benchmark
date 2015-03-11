package org.jboss.weld.benchmark.core.complex;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.jboss.weld.benchmark.core.DummyQualifier;
import org.jboss.weld.benchmark.core.SimpleDependentBean;

public class ProducerBean {

    @Produces
    @DummyQualifier("test")
    public DependentBean produceDependentBean(SimpleDependentBean simpleDependentBean){
        return new DependentBean(simpleDependentBean);
    }

    public void dispose (@Disposes @DummyQualifier("test") DependentBean dependentBean){
    }
}
