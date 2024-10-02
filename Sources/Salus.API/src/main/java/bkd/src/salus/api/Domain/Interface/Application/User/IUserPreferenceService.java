package bkd.src.salus.api.Domain.Interface.Application.User;

import bkd.src.salus.api.Domain.DTO.User.UserPreferenceDTO;
import bkd.src.salus.api.Domain.DTO.User.UserPreferenceResponseDetailing;
import org.springframework.transaction.annotation.Transactional;

public interface IUserPreferenceService {
    @Transactional
    UserPreferenceResponseDetailing UpdatePreferences(UserPreferenceDTO userPreference, int id);
}
