package org.elsys.ufg;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateService {
    private Map<String, Pattern> patterns;

    public CreateService(){
        patterns = new HashMap<>();
        patterns.put("burnerDrill", new Pattern(BurnerDrill.class, 60, 60, 3));
    }

    public Map<String, Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(Map<String, Pattern> patterns) {
        this.patterns = patterns;
    }

    public GameObject create(String objectType, int startX, int startY, int endX, int endY) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException  {
        return patterns.get(objectType).getPatternClass().getConstructor(Integer.class, Integer.class, Integer.class, Integer.class).newInstance(startX, startY, endX, endY);
    }

    public Pattern getPattern(String pattern){
        return patterns.get(pattern);
    }
}
