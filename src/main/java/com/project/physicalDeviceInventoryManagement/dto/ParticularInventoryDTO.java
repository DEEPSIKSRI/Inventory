package com.project.physicalDeviceInventoryManagement.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParticularInventoryDTO {
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