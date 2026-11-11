package com.monitor;

public class PowerSupplyMonitor {
    private final double minOutputVoltage;
    private final double maxOutputVoltage;
    private final double maxCurrent;

    public PowerSupplyMonitor(double minOutputVoltage, double maxOutputVoltage, double maxCurrent) {
        this.minOutputVoltage = minOutputVoltage;
        this.maxOutputVoltage = maxOutputVoltage;
        this.maxCurrent = maxCurrent;
    }

    public MonitorResult evaluate(PowerSupplyData data) {
        boolean underVoltage = data.getOutputVoltage() < minOutputVoltage;
        boolean overVoltage = data.getOutputVoltage() > maxOutputVoltage;
        boolean overCurrent = data.getCurrent() > maxCurrent;

        int faultCount = (underVoltage ? 1 : 0) + (overVoltage ? 1 : 0) + (overCurrent ? 1 : 0);

        if (faultCount > 1) {
            return new MonitorResult(data, Status.MULTIPLE_FAULTS, "Multiple threshold violations detected.");
        } else if (underVoltage) {
            return new MonitorResult(data, Status.UNDER_VOLTAGE, 
                String.format("Output voltage %.2fV is below minimum limit %.2fV", data.getOutputVoltage(), minOutputVoltage));
        } else if (overVoltage) {
            return new MonitorResult(data, Status.OVER_VOLTAGE, 
                String.format("Output voltage %.2fV exceeds maximum limit %.2fV", data.getOutputVoltage(), maxOutputVoltage));
        } else if (overCurrent) {
            return new MonitorResult(data, Status.OVER_CURRENT, 
                String.format("Current %.2fA exceeds safety threshold %.2fA", data.getCurrent(), maxCurrent));
        } else {
            return new MonitorResult(data, Status.NORMAL, "Operating within safe thresholds.");
        }
    }
}