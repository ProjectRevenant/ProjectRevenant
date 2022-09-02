package com.gestankbratwurst.revenant.projectrevenant.survival.body.entity;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class LivingEntityBody extends Body {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final LivingEntityBody body = new LivingEntityBody();

    public Builder base(String attributeId, double min, double max, double current) {
      BodyAttribute attribute = body.getAttribute(attributeId);
      attribute.setMinValue(min);
      attribute.setMaxValue(max);
      attribute.setCurrentValue(current);
      return this;
    }

    public Builder mod(BodyAttributeModifier modifier) {
      body.getAttribute(modifier.getBodyAttribute()).addModifier(modifier);
      return this;
    }

    public LivingEntityBody create() {
      return body;
    }

  }

}
