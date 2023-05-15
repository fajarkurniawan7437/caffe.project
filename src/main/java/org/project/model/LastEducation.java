package org.project.model;

import org.hibernate.annotations.GenericGenerator;
import org.project.model.base.CreatedBase;

import javax.persistence.*;

@Entity
@Table(name = "Last_education")
public class LastEducation extends CreatedBase {
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    public LastEducation() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
