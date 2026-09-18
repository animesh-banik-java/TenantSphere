package com.banik.user_service.entity;

import com.banik.user_service.basepackage.audit.AbstractAuditableModel;
import com.banik.user_service.basepackage.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET status = 'DELETED' WHERE id=?")
@SQLRestriction("status <> 'DELETED'")
public class User extends AbstractAuditableModel {

    private String name;

    private String email;

    private String phoneNo;

    private Date dateOfBirth;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

}
