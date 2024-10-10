<<<<<<<< HEAD:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/domain/sms/repository/SmsCertificationRepository.java
package com.yoyo.member.domain.sms.repository;
========
package com.yoyo.member.adapter.out.persistence;
>>>>>>>> 223e6a81c079b9f463bcdfe0cd8f9d5c1690a949:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/adapter/out/persistence/SmsCertificationRepository.java

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class SmsCertificationRepository {

    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 5 * 60;

    private final StringRedisTemplate stringRedisTemplate;

    public void createSmsCertification(String phone, String certificationNumber) {
        stringRedisTemplate.opsForValue()
                           .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getSmsCertification(String phone) {
        return stringRedisTemplate.opsForValue().get(PREFIX + phone);
    }

    public void deleteSmsCertification(String phone) {
        stringRedisTemplate.delete(PREFIX + phone);
    }

    public Boolean hasKey(String phone) {
        return stringRedisTemplate.hasKey(PREFIX + phone);
    }
}