package com.practise.flightInfo.repository;

import com.practise.flightInfo.model.FlightInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FlightInfoRepositoryImpl implements FlightInfoRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    //private HashOperations hashOperations;

    @Override
    public void save(String key, FlightInfoDTO flightInfoDTO) {
        redisTemplate.opsForHash().put("FLIGHTS", key, flightInfoDTO);
    }

    @Override
    public List<FlightInfoDTO> getAll() {
        return (List<FlightInfoDTO>) redisTemplate.opsForHash().values("FLIGHTS");
    }

    @Override
    public Optional<FlightInfoDTO> getFlightByKey(String key) {
        Object result = redisTemplate.opsForHash().get("FLIGHTS", key);
        if (result == null) return Optional.empty();

        return Optional.of((FlightInfoDTO) result);
    }
}
