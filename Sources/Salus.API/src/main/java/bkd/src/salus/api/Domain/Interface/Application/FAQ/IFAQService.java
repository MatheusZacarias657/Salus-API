package bkd.src.salus.api.Domain.Interface.Application.FAQ;

import bkd.src.salus.api.Domain.DTO.FAQ.DetailingFaqDTO;

import java.util.List;

public interface IFAQService {
    List<DetailingFaqDTO> GetGroups();

    List<DetailingFaqDTO> GetFAQs(int groupId);
}
