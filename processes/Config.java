package processes;

import org.json.simple.JSONArray;

public interface Config<T> {
    void registrar (T args); // sobreescribe el hashmap desde el json
    JSONArray ToJSON();
}
