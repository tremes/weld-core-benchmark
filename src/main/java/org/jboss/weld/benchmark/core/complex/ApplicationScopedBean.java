package org.jboss.weld.benchmark.core.complex;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.weld.benchmark.core.Dummy;
import org.jboss.weld.benchmark.core.DummyDecoratedInterface;

@ApplicationScoped
public class ApplicationScopedBean implements DummyDecoratedInterface {
    
    @Dummy
    public void ping(){
    }

    @Override
    public boolean getResult() {
        return false;
    }
}
