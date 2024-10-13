package bkd.src.salus.Report.Application.MongoFill;

import bkd.src.salus.Report.Domain.Interface.IExtractMongoData;
import bkd.src.salus.Report.Domain.Entity.NoSQL.MedicineConsumeLog;
import bkd.src.salus.Report.Domain.Interface.IObjectJsonConverter;
import bkd.src.salus.Report.Repository.NoSQL.IMedicineConsumeLogRepositoryMR;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
public class ExtractMongoData implements IExtractMongoData {

    private final IMedicineConsumeLogRepositoryMR consumeLogRepositoryMR;
    private final Gson objectMapper;

    @Autowired
    public ExtractMongoData(IMedicineConsumeLogRepositoryMR consumeLogRepositoryMR, IObjectJsonConverter objectJsonConverter) {
        this.consumeLogRepositoryMR = consumeLogRepositoryMR;
        this.objectMapper = objectJsonConverter.GetConverter();
    }

    @Override
    public ByteArrayInputStream CaptureData(String report, HashMap<String, Object> params){
        String jsonObject = "";
        Path reportPath = Paths.get(report);
        String reportName = reportPath.getFileName().toString().toLowerCase();

        switch (reportName){
            case "consumemedicine.jrxml":
                String username = params.get("Username").toString();
                List<MedicineConsumeLog> medicineConsumeLogs = consumeLogRepositoryMR.findByNameUsingQuery(username);
                jsonObject = objectMapper.toJson(medicineConsumeLogs);
                break;
            default:
                break;
        }

        return new ByteArrayInputStream(jsonObject.getBytes());
    }
}
