package com.forest.forest_server.User;

import com.forest.forest_server.UserData.UserData;
import com.forest.forest_server.UserFam.UserFam;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "forest_user")
@Getter @Setter
public class ForestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Column(name = "aphasia_type")
    private String aphasiaType;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_fam_id", referencedColumnName = "user_fam_id", foreignKey = @ForeignKey(name = "fk_user_fam"), nullable = true)
    private UserFam userFam;  // nullable: 보호자 정보는 선택 사항

    @OneToOne
    @JoinColumn(name = "user_data_id", referencedColumnName = "user_data_id", foreignKey = @ForeignKey(name = "fk_user_data"), nullable = false)
    private UserData userData; // 사용자의 데이터를 저장하는 외래 키

    @Column(name = "hash_value", nullable = false)
    private String hashValue;

    public ForestUser(){}
}
