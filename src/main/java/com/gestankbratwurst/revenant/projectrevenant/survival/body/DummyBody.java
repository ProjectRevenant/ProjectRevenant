package com.gestankbratwurst.revenant.projectrevenant.survival.body;

public class DummyBody extends Body {

  public DummyBody() {
    super();
    for(String identifier : BodyAttribute.getValues()) {
      this.getAttribute(identifier).setMaxValue(1.0);
      this.getAttribute(identifier).setCurrentValue(1.0);
      this.getAttribute(identifier).setMinValue(0.0);
    }
    // new RuntimeException("Creating dummy body.").printStackTrace();
  }
}
