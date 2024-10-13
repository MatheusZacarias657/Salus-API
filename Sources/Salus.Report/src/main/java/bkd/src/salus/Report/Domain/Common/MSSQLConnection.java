package bkd.src.salus.Report.Domain.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class MSSQLConnection extends DbConnection {

    @Autowired
    public MSSQLConnection(@Value("${spring.datasource.url}") String url,
                           @Value("${spring.datasource.username}") String user,
                           @Value("${spring.datasource.password}") String password){
        super(url, user, password);
    }
}
