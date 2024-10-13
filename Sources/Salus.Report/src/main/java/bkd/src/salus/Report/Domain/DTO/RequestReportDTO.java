package bkd.src.salus.Report.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestReportDTO {
    private String Report;
    private String ExportFormat;
    private HashMap<String, Object> Params;
}
