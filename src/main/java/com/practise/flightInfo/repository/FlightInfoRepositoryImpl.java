package com.practise.flightInfo.repository;

import com.practise.flightInfo.model.entity.FlightInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FlightInfoRepositoryImpl implements FlightInfoRepository {
    private static final Logger logger = LoggerFactory.getLogger(FlightInfoRepositoryImpl.class);

    private final RedisTemplate redisTemplate;

    @Autowired
    public FlightInfoRepositoryImpl(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String key, FlightInfo flightInfo) {
        redisTemplate.opsForHash().put("FLIGHTS", key, flightInfo);
    }

    @Override
    public List<FlightInfo> getAll() {
        return (List<FlightInfo>) redisTemplate.opsForHash().values("FLIGHTS");
    }

    @Override
    public Optional<FlightInfo> getFlightByKey(String key) {
        logger.info("repository: searching key: " + key);
        Object result = redisTemplate.opsForHash().get("FLIGHTS", key);
        if (result == null) return Optional.empty();

        return Optional.of((FlightInfo) result);
    }
}
