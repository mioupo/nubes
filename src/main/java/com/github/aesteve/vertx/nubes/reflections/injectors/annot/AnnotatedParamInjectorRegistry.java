package com.github.aesteve.vertx.nubes.reflections.injectors.annot;

import com.github.aesteve.vertx.nubes.annotations.auth.User;
import com.github.aesteve.vertx.nubes.annotations.cookies.CookieValue;
import com.github.aesteve.vertx.nubes.annotations.params.*;
import com.github.aesteve.vertx.nubes.marshallers.PayloadMarshaller;
import com.github.aesteve.vertx.nubes.reflections.adapters.ParameterAdapterRegistry;
import com.github.aesteve.vertx.nubes.reflections.injectors.annot.impl.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AnnotatedParamInjectorRegistry {

  private final Map<Class<?>, AnnotatedParamInjector<?>> map;

  public AnnotatedParamInjectorRegistry(Map<String, PayloadMarshaller> marshallers, ParameterAdapterRegistry adapters) {
    map = new HashMap<>();
    registerInjector(RequestBody.class, new RequestBodyParamInjector(marshallers));
    registerInjector(CookieValue.class, new CookieParamInjector());
    registerInjector(Header.class, new HeaderParamInjector(adapters));
    registerInjector(Param.class, new ParamInjector(adapters));
    registerInjector(Params.class, new ParamsInjector(adapters));
    registerInjector(User.class, new UserParamInjector());
    registerInjector(LocalMapValue.class, new LocalMapValueParamInjector());
    registerInjector(VertxLocalMap.class, new LocalMapParamInjector());
    registerInjector(ContextData.class, new ContextDataParamInjector());
    registerInjector(Headers.class, new HeadersParamInjector());
  }

  public <T extends Annotation> void registerInjector(Class<? extends T> clazz, AnnotatedParamInjector<T> injector) {
    map.put(clazz, injector);
  }

  @SuppressWarnings("unchecked")
  public <T extends Annotation> AnnotatedParamInjector<T> getInjector(Class<? extends T> clazz) {
    return (AnnotatedParamInjector<T>) map.get(clazz);
  }

}
