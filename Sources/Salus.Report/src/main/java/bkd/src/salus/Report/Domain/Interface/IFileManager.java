package bkd.src.salus.Report.Domain.Interface;

import java.io.File;

public interface IFileManager {
    String SaveFile(File file) throws Exception;

    void DeleteFile(String fileId);
}
