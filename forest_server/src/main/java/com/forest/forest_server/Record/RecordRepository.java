package com.forest.forest_server.Record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    //@Query("SELECT r FROM record r WHERE r.user_id = ?1 ORDER BY r.time_stamp ASC")
    List<Record> findByUserIdOrderByTimeStampAsc(Long userId);
}
