import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class Main {

	public static void main(String[] args) {
		RedisClient client = RedisClient.create(RedisURI.create("localhost", 6379));	  

		StatefulRedisConnection<String, String> connection = client.connect(); 

		RedisCommands<String, String> commands = connection.sync();            

		System.out.println(commands.set("foo", "bla"));   
		
		String value = commands.get("foo");                                    

		System.out.println(value);
		connection.close();                                                    

		client.shutdown();        
	}
}
