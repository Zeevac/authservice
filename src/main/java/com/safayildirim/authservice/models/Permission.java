package com.safayildirim.authservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Permission {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String api;
    @NonNull
    private long userId;

}
