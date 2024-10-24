package bkd.src.salus.api.Domain.DTO.Answerable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerablePatientResponse {
    private String User;
    private List<AnswerablePatient> Patients;
}
