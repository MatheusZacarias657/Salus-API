package bkd.src.salus.api.Domain.Adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailBrevoPayload {
    private List<EmailContact> To;
    private int TemplateId;
    private Map<String, String> Params;
    private List<BrevoAttachment> Attachment;

    public EmailBrevoPayload(List<EmailContact> to, int templateId, Map<String, String> parameters) {
        this.To = to;
        this.TemplateId = templateId;
        this.Params = parameters;
    }
}
