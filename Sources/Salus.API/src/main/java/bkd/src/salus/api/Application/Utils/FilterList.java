package bkd.src.salus.api.Application.Utils;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

import bkd.src.salus.api.Domain.Interface.Application.Utils.IFilterList;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class FilterList implements IFilterList {

    @Override
    public <T> List<T> ProcessList(List<T> items, Map<String, String> filterParams, String sortBy){
        if(filterParams.isEmpty()){
            return items;
        }

        List<T> filteredItems = FilterByParameters(items, filterParams);

        if (sortBy != null && !sortBy.isEmpty()) {
            filteredItems.sort((item1, item2) -> compareByField(item1, item2, sortBy));
        }

        return filteredItems;
    }

    private <T> List<T> FilterByParameters(List<T> items, Map<String, String> params){
        return items.stream()
                .filter(item -> filterByParams(item, params))
                .collect(Collectors.toList());
    }

    private <T> boolean filterByParams(T item, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String fieldName = entry.getKey();
            String expectedValue = entry.getValue();

            Field field = ReflectionUtils.findField(item.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(item);

                    if (!fieldValue.toString().contains(expectedValue)) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    return false;
                }
            }
        }

        return true;
    }

    private <T> int compareByField(T item1, T item2, String fieldName) {
        try {
            Field field1 = ReflectionUtils.findField(item1.getClass(), fieldName);
            Field field2 = ReflectionUtils.findField(item2.getClass(), fieldName);

            if (field1 != null && field2 != null) {
                field1.setAccessible(true);
                field2.setAccessible(true);

                Object value1 = field1.get(item1);
                Object value2 = field2.get(item2);

                // Assuming the field values are comparable (e.g., Strings, Numbers)
                if (value1 instanceof Comparable && value2 instanceof Comparable) {
                    return ((Comparable) value1).compareTo(value2);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if comparison is not possible (e.g., if field not found or not comparable)
    }
}
