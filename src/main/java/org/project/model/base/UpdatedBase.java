package org.project.model.base;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;


@MappedSuperclass
public class UpdatedBase extends CreatedBase {
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
}
