package bkd.src.salus.notificator.Domain.Interface.Repository;

import java.util.List;

public interface IRedisStackManager {
    String CatchFirst(String key);
    void AddObjectToList(String key, Object value);
    boolean KeyExists(String key);
    boolean DoesValueExistInList(String key, String value);
    void DeleteList(String key);
    String PopFirst(String key);
    List<String> GetAllValues(String key);
    void PushAllValues(String key, List<String> values);
}
