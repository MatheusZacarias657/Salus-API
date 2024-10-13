package bkd.src.salus.Report.Application.Utils;

import bkd.src.salus.Report.Domain.Interface.IObjectJsonConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ObjectJsonConverter implements IObjectJsonConverter {

    private final Gson objectMap;

    @Autowired
    public ObjectJsonConverter() {
        objectMap = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @Override
    public Gson GetConverter(){
        return this.objectMap;
    }

}
