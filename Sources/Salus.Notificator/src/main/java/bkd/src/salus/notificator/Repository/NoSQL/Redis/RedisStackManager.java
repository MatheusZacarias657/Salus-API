package bkd.src.salus.notificator.Repository.NoSQL.Redis;

import bkd.src.salus.notificator.Domain.Interface.Repository.IRedisStackManager;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisStackManager implements IRedisStackManager {

    private final RedisTemplate<String, String> redisTemplate;
    private final Gson objectMap;

    @Autowired
    public RedisStackManager(Gson objectMap, RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMap = new Gson();
    }

    public String CatchFirst(String key){
        return redisTemplate.opsForList().index(key, 0);
    }

    public void AddObjectToList(String key, Object value) {
        String jsonValue = objectMap.toJson(value);
        redisTemplate.opsForList().rightPush(key, jsonValue);
    }

    public boolean KeyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean DoesValueExistInList(String key, String value) {
        List<String> list = GetAllValues(key);
        PushAllValues(key, list);
        return list != null && list.stream().anyMatch(item -> item.contains(value));
    }

    public void DeleteList(String key){
        redisTemplate.delete(key);
    }

    public String PopFirst(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    public List<String> GetAllValues(String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void PushAllValues(String key, List<String> values){
        redisTemplate.opsForList().rightPushAll(key, values);
    }
}
