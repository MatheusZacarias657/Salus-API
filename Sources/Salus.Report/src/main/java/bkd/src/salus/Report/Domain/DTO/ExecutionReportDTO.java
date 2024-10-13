package bkd.src.salus.Report.Domain.DTO;

import bkd.src.salus.Report.Domain.Common.DbConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionReportDTO {
    private String ReportPath;
    private DbConnection DBConnection;
    private int DBSelect;
    private HashMap<String, Object> params;
}
