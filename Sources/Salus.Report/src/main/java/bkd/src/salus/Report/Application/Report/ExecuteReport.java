package bkd.src.salus.Report.Application.Report;

import bkd.src.salus.Report.Domain.DTO.ExecutionReportDTO;
import bkd.src.salus.Report.Domain.Interface.IExecuteReport;
import bkd.src.salus.Report.Domain.Interface.IExtractMongoData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class ExecuteReport implements IExecuteReport {

    private final IExtractMongoData extractMongoData;

    @Autowired
    public ExecuteReport(IExtractMongoData extractMongoData) {
        this.extractMongoData = extractMongoData;
    }

    @Override
    public JasperPrint Generate(ExecutionReportDTO execute) throws Exception {
        JasperReport jasperReport = JasperCompileManager.compileReport(execute.getReportPath());
        return FillReport(jasperReport, execute);
    }

    private JasperPrint FillReport(JasperReport jasperReport, ExecutionReportDTO execute) throws Exception {

        switch(execute.getDBSelect()){
            case 1:
                ByteArrayInputStream jsonInputStream = extractMongoData.CaptureData(execute.getReportPath(), execute.getParams());
                JsonDataSource jsonDataSource = new JsonDataSource(jsonInputStream);
                return JasperFillManager.fillReport(jasperReport, execute.getParams(), jsonDataSource);

            case 2:
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(execute.getDBConnection().getUrl(),
                                                                    execute.getDBConnection().getUser(),
                                                                    execute.getDBConnection().getPassword());

                return JasperFillManager.fillReport(jasperReport, execute.getParams(), connection);

            default:
                return null;
        }
    }

    @Override
    public File ExportFile(JasperPrint print, String format) throws Exception {
        File reportFile = File.createTempFile("report", "." + format.toLowerCase());
        switch (format) {
            case "PDF":
                JasperExportManager.exportReportToPdfFile(print, reportFile.getAbsolutePath());
                break;
            case "CSV":
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(print));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(reportFile));
                csvExporter.exportReport();
                break;
            case "HTML":
                HtmlExporter exporter = new HtmlExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleHtmlExporterOutput(reportFile));
                exporter.exportReport();
                break;
            case "XLSX":
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(print));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportFile));
                xlsxExporter.exportReport();
                break;
            case "XML":
                JasperExportManager.exportReportToXmlFile(print, reportFile.getAbsolutePath(), true);
                break;
            case "ODT":
                JROdtExporter odtExporter = new JROdtExporter();
                odtExporter.setExporterInput(new SimpleExporterInput(print));
                odtExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportFile));
                odtExporter.exportReport();
                break;
            case "RTF":
                JRRtfExporter rtfExporter = new JRRtfExporter();
                rtfExporter.setExporterInput(new SimpleExporterInput(print));
                rtfExporter.setExporterOutput(new SimpleWriterExporterOutput(reportFile));
                rtfExporter.exportReport();
                break;
            default:
                throw new IllegalArgumentException("Unsupported format type: " + format);
        }

        return reportFile;
    }
}
