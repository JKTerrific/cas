package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.UniqueTicketIdGeneratorConfigurer;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is {@link CasCoreTicketIdGeneratorsConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.1.0
 */
@Configuration("casCoreTicketIdGeneratorsConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@Slf4j
public class CasCoreTicketIdGeneratorsConfiguration {

    @Bean
    @Autowired
    public Map<String, UniqueTicketIdGenerator> uniqueIdGeneratorsMap(final List<UniqueTicketIdGeneratorConfigurer> configurers) {
        val map = new HashMap<String, UniqueTicketIdGenerator>();
        if (configurers != null) {
            configurers.forEach(c -> {
                val pair = c.buildUniqueTicketIdGenerators();
                pair.forEach(p -> map.put(p.getKey(), p.getValue()));
            });
        }
        return map;
    }
}
