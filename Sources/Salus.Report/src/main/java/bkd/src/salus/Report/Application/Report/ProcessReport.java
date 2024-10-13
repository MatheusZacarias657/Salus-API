package bkd.src.salus.Report.Application.Report;

import bkd.src.salus.Report.Domain.Common.DbConnection;
import bkd.src.salus.Report.Domain.Common.DbConnectionData;
import bkd.src.salus.Report.Domain.DTO.ExecutionReportDTO;
import bkd.src.salus.Report.Domain.DTO.RequestReportDTO;
import bkd.src.salus.Report.Domain.Entity.SQL.Report.Report;
import bkd.src.salus.Report.Domain.Interface.IExecuteReport;
import bkd.src.salus.Report.Domain.Interface.IMessageSender;
import bkd.src.salus.Report.Domain.Interface.IProcessReport;
import bkd.src.salus.Report.Repository.SQL.IReportRepositoryJPA;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ProcessReport implements IProcessReport {

    private final IReportRepositoryJPA reportRepositoryJPA;
    private final DbConnectionData dbConnectionData;
    private final IExecuteReport executeReport;
    private final IMessageSender messageSender;

    @Autowired
    public ProcessReport(IReportRepositoryJPA reportRepositoryJPA, DbConnectionData dbConnectionData, IExecuteReport executeReport, IMessageSender messageSender) {
        this.reportRepositoryJPA = reportRepositoryJPA;
        this.dbConnectionData = dbConnectionData;
        this.executeReport = executeReport;
        this.messageSender = messageSender;
    }

    @Override
    public void RequestReport(RequestReportDTO requestReportDTO) throws Exception {
        Report reportEntity = reportRepositoryJPA.findByReportByName(requestReportDTO.getReport());
        DbConnection dbConnection = this.dbConnectionData.CapturaConnectionString(reportEntity.getDBConnection());
        ExecutionReportDTO executionReportDTO = new ExecutionReportDTO(reportEntity.getPath(), dbConnection, reportEntity.getDBConnection(), requestReportDTO.getParams());
        JasperPrint reportView = executeReport.Generate(executionReportDTO);
        File reportExport = executeReport.ExportFile(reportView, requestReportDTO.getExportFormat());
        //TODO: comunica na fila pra enviar por email
        messageSender.SendMessageOnExchange("aaa", "report-response-exchange");
    }
}
