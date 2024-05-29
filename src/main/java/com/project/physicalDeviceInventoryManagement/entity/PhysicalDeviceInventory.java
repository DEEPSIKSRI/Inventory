package com.project.physicalDeviceInventoryManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
public class PhysicalDeviceInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String imei;
    private String deviceType;
    private String model;
    private String simType;
    private String currentHolder;
    private String previousHolder;
    private Timestamp entryCreatedDate;
    private Timestamp lastUpdatedDate;
    private String remarks;
    private String businessNeeded;

}