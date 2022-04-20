package kz.camundademo.camundademo.domain;

import org.camunda.bpm.engine.cdi.annotation.BusinessProcessScoped;

import javax.inject.Named;

@BusinessProcessScoped
@Named("tipaBean")
public class CamundaBean {

    public void someMethod(String value) {
        System.out.println(value);
    }

}
