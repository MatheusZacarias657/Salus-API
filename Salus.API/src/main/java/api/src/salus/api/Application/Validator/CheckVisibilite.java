package api.src.salus.api.Application.Validator;

import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.Auth.ITokenRead;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CheckVisibilite implements ICheckVisibilite {

    private final ITokenRead tokenSevice;
    private final IUserRepositoryJPA repository;

    @Autowired
    public CheckVisibilite(ITokenRead tokenSevice, IUserRepositoryJPA repository) {
        this.tokenSevice = tokenSevice;
        this.repository = repository;
    }

    @Override
    public void CheckAccess(String authHeader, int targetId){

        int ownId = ExtractIdFromToken(authHeader);
        int answerableId = repository.getAnswerableIdById(targetId);

        if (answerableId != ownId){
            throw new AccessDeniedException("Cannot Access this user");
        }
    }

    @Override
    public int ExtractIdFromToken(String authHeader){
        String claim = tokenSevice.GetClaim(authHeader, "id");

        return (claim != null) ? Integer.parseInt(claim) : 0;
    }
}
