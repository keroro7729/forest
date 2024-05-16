package com.forest.forest_server.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository){
        this.recordRepository = recordRepository;
    }

    public List<Record> getAllBy(Long userId){
        return recordRepository.findByUserIdOrderByTimeStampAsc(userId);
    }
}
