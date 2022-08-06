## Spring Velocity Views

Retrofit support for Apache Velocity views into modern Spring and Spring Boot releases.

## Motivation

We have a number of long running projects which are heavily invested in Apache Velocity templating.

The deprecation of Velocity support in modern Spring and Spring Boot releases meant we had to roll our own view resolver for our Velocity templates.

This is the minimalist implementation of `AbstractTemplateViewResolver` which seems to work for us; your mileage may vary.


## Example usage

We define these 2 beans in our `Main.java`:

```
@Bean
public VelocityViewResolver velocityViewResolver() {
    final VelocityViewResolver viewResolver = new VelocityViewResolver();
    viewResolver.setCache(true);
    viewResolver.setSuffix(".vm");
    viewResolver.setContentType("text/html;charset=UTF-8");
    
    // We can add view attributes here
    final Map<String, Object> attributes = Maps.newHashMap();
    viewResolver.setAttributesMap(attributes);
    
    return viewResolver;
}

@Bean("velocityEngine")
public VelocityEngineFactoryBean velocityEngineFactoryBean() {
    VelocityEngineFactoryBean velocityEngineFactory= new VelocityEngineFactoryBean();
    
    // Control of Velocity config is available here
    Properties vp = new Properties();
    vp.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
    vp.setProperty(Velocity.EVENTHANDLER_REFERENCEINSERTION, "org.apache.velocity.app.event.implement.EscapeHtmlReference");
    vp.setProperty("resource.loader", "class");
    vp.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

    vp.setProperty("resource.loader.class.cache", "true");
    // When resource.manager.cache.default_size is set to 0, then the default implementation uses the standard Java ConcurrentHashMap.
    vp.setProperty("resource.manager.cache.default_size", "0");

    vp.setProperty("velocimacro.library", "spring.vm");
    velocityEngineFactory.setVelocityProperties(vp);
    
    return velocityEngineFactory;
}
```


