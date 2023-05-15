package org.project.model.base;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public class CreatedBase extends PanacheEntityBase {
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
}
