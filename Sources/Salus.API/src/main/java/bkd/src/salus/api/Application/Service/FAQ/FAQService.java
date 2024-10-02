package bkd.src.salus.api.Application.Service.FAQ;

import bkd.src.salus.api.Domain.DTO.FAQ.DetailingFaqDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import bkd.src.salus.api.Domain.Entity.FAQ.FAQ;
import bkd.src.salus.api.Domain.Entity.FAQ.GroupFAQ;
import bkd.src.salus.api.Domain.Interface.Application.FAQ.IFAQService;
import bkd.src.salus.api.Repository.FAQ.IFAQRepositoryJPA;
import bkd.src.salus.api.Repository.FAQ.IGroupFAQRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQService implements IFAQService {

    private final IFAQRepositoryJPA faqRepository;
    private final IGroupFAQRepositoryJPA groupFAQRepository;

    @Autowired
    public FAQService(IFAQRepositoryJPA faqRepository, IGroupFAQRepositoryJPA groupFAQRepository) {
        this.faqRepository = faqRepository;
        this.groupFAQRepository = groupFAQRepository;
    }

    @Override
    public List<DetailingFaqDTO> GetGroups(){
        List<GroupFAQ> groups = groupFAQRepository.findAll();
        return (List<DetailingFaqDTO>) groups.stream().map(DetailingFaqDTO::new);
    }

    @Override
    public List<DetailingFaqDTO> GetFAQs(int groupId){
        List<FAQ> faqs = faqRepository.findFAQByGroupId(groupId);
        return (List<DetailingFaqDTO>) faqs.stream().map(DetailingFaqDTO::new);
    }
}
