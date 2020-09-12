package guru.qa.github.configHelpers;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:default.properties"
})
public interface ServiceConfig extends Config {

    @Key("service.base.url")
    String baseUrl();

    @Key("service.api.url")
    String apiUrl();
}
