package bkd.src.salus.api.Domain.Interface.Application.Utils;

import java.util.List;
import java.util.Map;

public interface IFilterList {
    <T> List<T> ProcessList(List<T> items, Map<String, String> filterParams, String sortBy);
}
