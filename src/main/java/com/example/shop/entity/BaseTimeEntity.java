package com.example.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // apply Auditing
@MappedSuperclass // abstract class
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate //  Automatically stores time when entity is created
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate // Automatically stores time when entity is modified
    private LocalDateTime updateTime;
}
