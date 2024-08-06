package com.forest.forest_server.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ForestUser { //forest_user

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
