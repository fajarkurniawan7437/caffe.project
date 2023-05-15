package org.project.model;

import org.hibernate.annotations.GenericGenerator;
import org.project.model.base.CreatedBase;

import javax.persistence.*;

@Entity
@Table(name = "job_position")
public class JobPosition extends CreatedBase {
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "salary", nullable = false)
    private Double salary;

    public JobPosition() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
