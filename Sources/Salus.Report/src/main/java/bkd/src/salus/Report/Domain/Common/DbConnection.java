package bkd.src.salus.Report.Domain.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbConnection {
    private String url;
    private String user;
    private String password;
}
