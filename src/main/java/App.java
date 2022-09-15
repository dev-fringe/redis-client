import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@Import(RedisConfig.class)
public class App implements InitializingBean {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(App.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> m = new HashMap<>();
		m.put("aa", "dd");
		redisTemplate.opsForValue().set("test1", m);
		Map<String, Object> m2 = (Map<String, Object>) redisTemplate.opsForValue().get("test1");
		System.out.println(m2);

	}
}
