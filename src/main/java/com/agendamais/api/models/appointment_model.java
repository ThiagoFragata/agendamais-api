package com.agendamais.api.models;

import com.agendamais.api.config.appointment_status_enum;
import jakarta.persistence.*;

@Entity
@Table(name = "Appointments")
public class appointment_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idSchedules", nullable = false)
    private schedule_model schedule;

    @ManyToOne
    @JoinColumn(name = "idUsers", nullable = false)
    private user_model user;

    @ManyToOne
    @JoinColumn(name = "idEmployees", nullable = false)
    private employee_model employee;

    @ManyToOne
    @JoinColumn(name = "idServices", nullable = false)
    private service_model service;

    @ManyToOne
    @JoinColumn(name = "idStores", nullable = false)
    private store_model store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private appointment_status_enum status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public schedule_model getSchedule() {
        return schedule;
    }

    public void setSchedule(schedule_model schedule) {
        this.schedule = schedule;
    }

    public user_model getUser() {
        return user;
    }

    public void setUser(user_model user) {
        this.user = user;
    }

    public employee_model getEmployee() {
        return employee;
    }

    public void setEmployee(employee_model employee) {
        this.employee = employee;
    }

    public service_model getService() {
        return service;
    }

    public void setService(service_model service) {
        this.service = service;
    }

    public store_model getStore() {
        return store;
    }

    public void setStore(store_model store) {
        this.store = store;
    }

    public appointment_status_enum getStatus() {
        return status;
    }

    public void setStatus(appointment_status_enum status) {
        this.status = status;
    }
}