package com.openclassroom.ChaTop.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 255)
    @Email
    private String email;

    @NonNull
    @Size(max = 255)
    private String name;

    @NonNull
    @Size(max = 255)
    private String password;


    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", length = 8)
    private LocalDateTime updated_at;
}
