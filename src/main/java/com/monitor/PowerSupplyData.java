package com.monitor;

public class PowerSupplyData {
    private final String datasetId;
    private final double inputVoltage;  // Volts (V)
    private final double outputVoltage; // Volts (V)
    private final double current;       // Amperes (A)

    public PowerSupplyData(String datasetId, double inputVoltage, double outputVoltage, double current) {
        this.datasetId = datasetId;
        this.inputVoltage = inputVoltage;
        this.outputVoltage = outputVoltage;
        this.current = current;
    }

    public String getDatasetId() { return datasetId; }
    public double getInputVoltage() { return inputVoltage; }
    public double getOutputVoltage() { return outputVoltage; }
    public double getCurrent() { return current; }

    public double getPower() {
        return outputVoltage * current; // Power = V * I (Watts)
    }
}