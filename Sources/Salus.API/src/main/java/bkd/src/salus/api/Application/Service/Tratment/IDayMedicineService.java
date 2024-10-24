package bkd.src.salus.api.Application.Service.Tratment;

import bkd.src.salus.api.Domain.DTO.Medicine.MedicineCalendarResponse;

import java.time.LocalDateTime;

public interface IDayMedicineService {
    MedicineCalendarResponse FindByDay(int userId, LocalDateTime date);
}
