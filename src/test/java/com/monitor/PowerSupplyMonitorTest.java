package com.monitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PowerSupplyMonitorTest {
    private PowerSupplyMonitor monitor;

    @BeforeEach
    void setUp() {
        // Nominal limits: 11.4V to 12.6V, Max Current: 5.0A
        monitor = new PowerSupplyMonitor(11.4, 12.6, 5.0);
    }

    @ParameterizedTest
    @CsvSource({
        "DS01, 230.0, 12.0, 2.5, NORMAL",
        "DS02, 230.0, 10.5, 2.0, UNDER_VOLTAGE",
        "DS03, 230.0, 13.2, 1.5, OVER_VOLTAGE",
        "DS04, 230.0, 12.1, 6.2, OVER_CURRENT"
    })
    void testDatasetEvaluations(String id, double inputV, double outputV, double current, String expectedStatus) {
        PowerSupplyData data = new PowerSupplyData(id, inputV, outputV, current);
        MonitorResult result = monitor.evaluate(data);
        assertEquals(Status.valueOf(expectedStatus), result.getStatus());
    }

    @Test
    void testPowerCalculation() {
        PowerSupplyData data = new PowerSupplyData("DS05", 220.0, 12.0, 3.0);
        assertEquals(36.0, data.getPower(), 0.001);
    }

    @Test
    void testHtmlReportGeneration() throws IOException {
        List<MonitorResult> results = new ArrayList<>();
        PowerSupplyData d1 = new PowerSupplyData("DS-TEST-1", 230.0, 12.0, 2.0);
        results.add(monitor.evaluate(d1));

        String reportPath = "target/reports/power_supply_report.html";
        ReportGenerator.generateHtmlReport(results, reportPath);

        File reportFile = new File(reportPath);
        assertTrue(reportFile.exists(), "HTML report file should be generated.");
    }
}