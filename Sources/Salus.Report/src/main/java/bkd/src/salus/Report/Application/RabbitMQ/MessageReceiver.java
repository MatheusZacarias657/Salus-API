package bkd.src.salus.Report.Application.RabbitMQ;

import bkd.src.salus.Report.Domain.DTO.RequestReportDTO;
import bkd.src.salus.Report.Domain.Interface.IObjectJsonConverter;
import bkd.src.salus.Report.Domain.Interface.IProcessReport;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    private final Gson objectMap;
    private final IProcessReport processReport;

    @Autowired
    public MessageReceiver(IObjectJsonConverter objectJsonConverter, IProcessReport processReport) {
        objectMap = objectJsonConverter.GetConverter();
        this.processReport = processReport;
    }

    @RabbitListener(queues = { "request-report-queue" })
    public void receiveReportRequest(@Payload Message message) throws Exception {
        String obj = new String(message.getBody());
        System.out.println("Received message on RabbitMQ: " + obj);
        RequestReportDTO requestReportDTO = objectMap.fromJson(obj, RequestReportDTO.class);
        processReport.RequestReport(requestReportDTO);
    }
}
