package bkd.src.salus.Report.Domain.Interface;

import bkd.src.salus.Report.Domain.DTO.ExecutionReportDTO;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.File;

public interface IExecuteReport {
    JasperPrint Generate(ExecutionReportDTO execute) throws Exception;

    File ExportFile(JasperPrint print, String format) throws Exception;
}
