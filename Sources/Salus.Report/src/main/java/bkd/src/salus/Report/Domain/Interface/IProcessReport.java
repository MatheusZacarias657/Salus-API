package bkd.src.salus.Report.Domain.Interface;

import bkd.src.salus.Report.Domain.DTO.RequestReportDTO;

public interface IProcessReport {
    void RequestReport(RequestReportDTO requestReportDTO) throws Exception;
}
