package com.agendamais.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Schedules")
public class schedule_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schedule;

    @Column(nullable = false)
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private employee_model employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public employee_model getEmployee() {
        return employee;
    }

    public void setEmployee(employee_model employee) {
        this.employee = employee;
    }
}
