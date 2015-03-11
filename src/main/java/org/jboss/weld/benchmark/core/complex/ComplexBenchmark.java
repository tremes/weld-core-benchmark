package org.jboss.weld.benchmark.core.complex;

import org.jboss.weld.benchmark.core.AbstractBenchmark;

public class ComplexBenchmark extends AbstractBenchmark<RequestScopedBean>{
    
    @Override
    protected Class<RequestScopedBean> getBeanClass() {
        return RequestScopedBean.class;
    }
}
