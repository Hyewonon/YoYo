package com.yoyo.common.kafka;

public class KafkaUtils {
    @SafeVarargs
    public static String getJsonTypeMappingInfo(Class<? extends KafkaJson> ...classes) {
        StringBuilder sb = new StringBuilder();
        for (Class<?> c : classes) {
            sb.append(c.getSimpleName());
            sb.append(":");
            sb.append(c.getName());
            sb.append(",");
        }

        sb.setLength(sb.length()-1);

        return sb.toString();
    }
}
