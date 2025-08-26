package com.paperless.api.core.data.database;

import com.paperless.api.notification.presentation.listener.NotificationListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {
    private final NotificationListener notificationListener;

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.ssl.enabled:false}")
    private boolean sslEnabled;

    @Value("${spring.data.redis.cluster.nodes:#{null}}")
    private List<String> clusterNodes;

    @Value("${spring.data.redis.cluster.max-redirects:3}")
    private int maxRedirects;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        try {
            LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder = LettuceClientConfiguration.builder();

            if (sslEnabled) clientConfigBuilder.useSsl();

            LettuceClientConfiguration clientConfig = clientConfigBuilder.build();
            LettuceConnectionFactory factory;

            if (hasClusterConfiguration()) {
                log.info("Configuring Redis in CLUSTER mode with nodes: {}", clusterNodes);
                factory = createClusterConnectionFactory(clientConfig);
            } else {
                log.info("Configuring Redis in STANDALONE mode with host: {}:{}", host, port);
                factory = createStandaloneConnectionFactory(clientConfig);
            }

            factory.setValidateConnection(true);
            factory.afterPropertiesSet();
            return factory;

        } catch (Exception e) {
            log.error("Failed to configure Redis connection", e);
            throw new RuntimeException("Redis configuration failed", e);
        }
    }

    private boolean hasClusterConfiguration() {
        return clusterNodes != null && !clusterNodes.isEmpty();
    }

    private LettuceConnectionFactory createClusterConnectionFactory(LettuceClientConfiguration clientConfig) {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();

        for (String node : clusterNodes) {
            String[] hostPort = node.trim().split(":");
            String nodeHost = hostPort[0];
            int nodePort = hostPort.length > 1 ? Integer.parseInt(hostPort[1]) : 6379;
            clusterConfig.clusterNode(nodeHost, nodePort);
        }

        clusterConfig.setMaxRedirects(maxRedirects);
        return new LettuceConnectionFactory(clusterConfig, clientConfig);
    }

    private LettuceConnectionFactory createStandaloneConnectionFactory(LettuceClientConfiguration clientConfig) {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(host);
        standaloneConfig.setPort(port);
        return new LettuceConnectionFactory(standaloneConfig, clientConfig);
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());

        container.addMessageListener(notificationListener, new PatternTopic("member:*:notification"));
        return container;
    }
}
