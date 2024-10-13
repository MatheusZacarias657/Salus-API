package bkd.src.salus.Report.Domain.Interface;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

public interface IExtractMongoData {
    ByteArrayInputStream CaptureData(String report, HashMap<String, Object> params);
}
