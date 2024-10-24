package bkd.src.salus.api.Domain.Interface.Application.User;

import bkd.src.salus.api.Domain.DTO.User.Preference.UserPreferenceDTO;
import bkd.src.salus.api.Domain.DTO.User.Preference.UserPreferenceResponseDetailing;
import org.springframework.transaction.annotation.Transactional;

public interface IUserPreferenceService {
    @Transactional
    UserPreferenceResponseDetailing UpdatePreferences(UserPreferenceDTO userPreference, int id);
    UserPreferenceResponseDetailing GetPreferences(int id);
}
