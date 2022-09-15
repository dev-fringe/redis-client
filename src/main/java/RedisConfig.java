import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

@Configuration
public class RedisConfig {

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration c = new RedisStandaloneConfiguration("localhost", 6379);
		LettuceConnectionFactory f = new LettuceConnectionFactory(c);
		f.setShareNativeConnection(true);
		f.setValidateConnection(true);
		return f;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
	    RedisTemplate<String, Object> r = new RedisTemplate<>();
	    r.setConnectionFactory(lettuceConnectionFactory);
	    Jackson2JsonRedisSerializer<Object> j = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper m = new ObjectMapper();
        m.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        m.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        j.setObjectMapper(m);
        r.setKeySerializer(RedisSerializer.string());
        r.setHashKeySerializer(RedisSerializer.string());
        r.setValueSerializer(j);
        r.setHashValueSerializer(j);
	    return r;
	}
}
