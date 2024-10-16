package bkd.src.salus.Report.Application.FileManager;

import bkd.src.salus.Report.Domain.Interface.IFileManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class FileManager implements IFileManager {

    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public FileManager(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    @Override
    public String SaveFile(File file) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        ObjectId fileId = gridFsTemplate.store(inputStream, file.getName());

        return fileId.toHexString();
    }

    @Override
    public void DeleteFile(String fileId) {
        gridFsTemplate.delete(new org.springframework.data.mongodb.core.query.Query(
                Criteria.where("_id").is(fileId)
        ));
    }
}
