package bkd.src.salus.Report.Domain.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class MongoConnection extends DbConnection {

    @Autowired
    public MongoConnection(@Value("${spring.data.mongodb.url}") String url,
                           @Value("${spring.data.mongodb.username}") String user,
                           @Value("${spring.data.mongodb.password}") String password){
        super(url, user, password);
    }
}
