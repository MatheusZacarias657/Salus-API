package bkd.src.salus.api.Application.Service.User;

import bkd.src.salus.api.Domain.DTO.User.Preference.UserPreferenceDTO;
import bkd.src.salus.api.Domain.DTO.User.Preference.UserPreferenceResponseDetailing;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserPreference;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserPreferenceService;
import bkd.src.salus.api.Repository.SQL.User.IUserPreferencesRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceService implements IUserPreferenceService {

    private final IUserPreferencesRepositoryJPA repository;

    @Autowired
    public UserPreferenceService(IUserPreferencesRepositoryJPA repository){
        this.repository = repository;
    }

    @Override
    public UserPreferenceResponseDetailing UpdatePreferences(UserPreferenceDTO userPreference, int id){
        UserPreference preference = repository.getUserPreferenceByUserId(id);
        preference.UpdateData(userPreference);

        return new UserPreferenceResponseDetailing(preference.getUser().getLogin(), preference.getTypography(), preference.isEnableStatistics());
    }

    @Override
    public UserPreferenceResponseDetailing GetPreferences(int id){
        UserPreference preference = repository.getUserPreferenceByUserId(id);

        return new UserPreferenceResponseDetailing(preference.getUser().getLogin(), preference.getTypography(), preference.isEnableStatistics());
    }
}
