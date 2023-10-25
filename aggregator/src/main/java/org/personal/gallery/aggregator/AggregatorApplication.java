package org.personal.gallery.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "org.personal.gallery"
})
@EnableAsync
@EnableScheduling
public class AggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }

}


/***
 *
 *     1.read content from file -> apply map-reduce job to create map -> create a prefixmap <prefix, list<word with freq>> -> store in redis
 *     2. user need suggestion -> read from redis and return
 *
 */