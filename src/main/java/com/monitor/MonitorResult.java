package com.monitor;

public class MonitorResult {
    private final PowerSupplyData data;
    private final Status status;
    private final String details;

    public MonitorResult(PowerSupplyData data, Status status, String details) {
        this.data = data;
        this.status = status;
        this.details = details;
    }

    public PowerSupplyData getData() { return data; }
    public Status getStatus() { return status; }
    public String getDetails() { return details; }
}