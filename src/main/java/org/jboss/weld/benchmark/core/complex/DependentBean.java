package org.jboss.weld.benchmark.core.complex;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.weld.benchmark.core.SimpleDependentBean;

@Dependent
public class DependentBean {

    ApplicationScopedBean applicationScopedBean;
    SimpleDependentBean simpleDependentBean;

    @Inject
    public DependentBean(SimpleDependentBean dependentBean) {
        this.simpleDependentBean = dependentBean;
    }

    @Inject
    public void createDependentBean(ApplicationScopedBean bean) {
        this.applicationScopedBean = bean;
        // to call decorator
        bean.getResult();
    }

    public void ping() {
    }

}
