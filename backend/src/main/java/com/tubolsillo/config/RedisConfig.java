package com.tubolsillo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Clase de configuración de Redis
 */
@Configuration
public class RedisConfig {

    /**
     * Define y configura el RedisTemplate.
     * Es crucial usar StringRedisSerializer para que las claves y valores
     * se vean legibles en la consola de Redis.
     *
     * @param connectionFactory La factoría de conexión autoconfigurada por Spring Boot.
     * @return RedisTemplate configurado para String (clave) y String (valor).
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Define los serializadores para las claves y valores
        // Esto es esencial para que tus prefijos ("BL:") y los tokens
        // se guarden como texto plano legible.
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
