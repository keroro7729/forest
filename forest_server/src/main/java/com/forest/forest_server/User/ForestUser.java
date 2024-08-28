package com.forest.forest_server.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(name = "forest_user")
@Getter
@Setter
@NoArgsConstructor
public class ForestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "birthdate", nullable = false)
    private String birthdate;

    @Column(name = "sex", nullable = false, length = 1)
    private String sex;

    @Column(name = "aphasia_type", nullable = false, length = 10)
    private String aphasiaType;

    @Column(name = "create_time", nullable = false, updatable = false)
    private String createTime;

    @Column(name = "hash", nullable = false, length = 64)
    private String hash;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now().toString();
        Integer rand = (int)(Math.random()*100000);
        String data = birthdate.toString() + sex + aphasiaType + createTime.toString() + rand.toString();
        this.hash = DigestUtils.sha256Hex(data);
    }
}
