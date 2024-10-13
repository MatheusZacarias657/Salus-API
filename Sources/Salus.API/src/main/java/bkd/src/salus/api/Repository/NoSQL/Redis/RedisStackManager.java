package bkd.src.salus.api.Repository.NoSQL.Redis;

import bkd.src.salus.api.Domain.Interface.Repository.IRedisStackManager;
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

    @Override
    public String CatchFirst(String key){
        return redisTemplate.opsForList().index(key, 0);
    }

    @Override
    public void AddObjectToList(String key, Object value) {
        String jsonValue = objectMap.toJson(value);
        redisTemplate.opsForList().rightPush(key, jsonValue);
    }

    @Override
    public boolean KeyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public boolean DoesValueExistInList(String key, String value) {
        List<String> list = GetAllValues(key);
        PushAllValues(key, list);
        return list != null && list.stream().anyMatch(item -> item.contains(value));
    }

    @Override
    public void DeleteList(String key){
        redisTemplate.delete(key);
    }

    @Override
    public String PopFirst(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public List<String> GetAllValues(String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public void PushAllValues(String key, List<String> values){
        redisTemplate.opsForList().rightPushAll(key, values);
    }
}
