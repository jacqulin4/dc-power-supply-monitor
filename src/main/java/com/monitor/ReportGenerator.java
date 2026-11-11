package com.monitor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReportGenerator {
    public static void generateHtmlReport(List<MonitorResult> results, String outputPath) throws IOException {
        if (Paths.get(outputPath).getParent() != null) {
            Files.createDirectories(Paths.get(outputPath).getParent());
        }
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("<!DOCTYPE html><html><head><title>Power Supply Monitoring Report</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; }");
            writer.write("table { border-collapse: collapse; width: 100%; }");
            writer.write("th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
            writer.write("th { background-color: #004085; color: white; }");
            writer.write(".NORMAL { background-color: #d4edda; color: #155724; }");
            writer.write(".UNDER_VOLTAGE { background-color: #fff3cd; color: #856404; }");
            writer.write(".OVER_VOLTAGE { background-color: #f8d7da; color: #721c24; }");
            writer.write(".OVER_CURRENT { background-color: #f8d7da; color: #721c24; }");
            writer.write("</style></head><body>");

            writer.write("<h2>DC Power Supply Monitoring System - Test Results</h2>");
            writer.write("<table>");
            writer.write("<tr><th>Dataset ID</th><th>Input (V)</th><th>Output (V)</th><th>Current (A)</th><th>Power (W)</th><th>Status</th><th>Details</th></tr>");

            for (MonitorResult res : results) {
                PowerSupplyData d = res.getData();
                writer.write(String.format("<tr class='%s'><td>%s</td><td>%.2f</td><td>%.2f</td><td>%.2f</td><td>%.2f</td><td><b>%s</b></td><td>%s</td></tr>",
                        res.getStatus().name(), d.getDatasetId(), d.getInputVoltage(), d.getOutputVoltage(), d.getCurrent(), d.getPower(), res.getStatus(), res.getDetails()));
            }

            writer.write("</table></body></html>");
        }
    }
}