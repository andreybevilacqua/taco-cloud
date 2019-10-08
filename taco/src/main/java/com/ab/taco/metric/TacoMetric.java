package com.ab.taco.metric;

import com.ab.taco.model.Ingredient;
import com.ab.taco.model.Taco;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TacoMetric extends AbstractRepositoryEventListener<Taco> {

  private MeterRegistry meterRegistry;

  @Autowired
  public TacoMetric(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  protected void onAfterCreate(Taco taco) {
    List<Ingredient> ingredients = taco.getIngredients();
    for (Ingredient ingredient : ingredients) {
      meterRegistry.counter("tacocloud", ingredient.getName()).increment();
    }
  }

}
