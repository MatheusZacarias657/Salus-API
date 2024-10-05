package bkd.src.salus.api.Application.Service.User;

import bkd.src.salus.api.Domain.DTO.User.UserPreferenceDTO;
import bkd.src.salus.api.Domain.DTO.User.UserPreferenceResponseDetailing;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserPreference;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserPreferenceService;
import bkd.src.salus.api.Repository.SQL.User.IUserPreferencesRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPreferenceService implements IUserPreferenceService {

    private final IUserPreferencesRepositoryJPA repository;

    @Autowired
    public UserPreferenceService(IUserPreferencesRepositoryJPA repository){
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserPreferenceResponseDetailing UpdatePreferences(UserPreferenceDTO userPreference, int id){
        UserPreference preferece = repository.getUserPreferenceByUserId(id);
        preferece.UpdateData(userPreference);

        return new UserPreferenceResponseDetailing(preferece.getUser().getLogin(), preferece.getTypography(), preferece.isEnableStatistics());
    }
}
