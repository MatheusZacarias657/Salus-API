package bkd.src.salus.Report.Domain.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbConnectionData {

    private final MSSQLConnection mssqlConnection;
    private final MongoConnection mongoConnection;

    @Autowired
    public DbConnectionData(MSSQLConnection mssqlConnection, MongoConnection mongoConnection) {
        this.mssqlConnection = mssqlConnection;
        this.mongoConnection = mongoConnection;
    }

    public DbConnection CapturaConnectionString(int value){
        return switch (value) {
            case 1 -> this.mssqlConnection;
            case 2 -> this.mongoConnection;
            default -> null;
        };
    }
}
