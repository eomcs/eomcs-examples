package bitcamp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@ComponentScan(
    value="bitcamp",
    excludeFilters = {
        @Filter(type = FilterType.REGEX, pattern = "bitcamp.web.*")
    })
public class RootConfig {

}
