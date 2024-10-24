package bkd.src.salus.api.Application.Validator;

import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ITokenRead;
import bkd.src.salus.api.Domain.Interface.Application.LogAnswerableLastAccess.ILogAnswerableLastAccess;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CheckVisibilite implements ICheckVisibilite {

    private final ITokenRead tokenSevice;
    private final IUserRepositoryJPA repository;
    private final ILogAnswerableLastAccess logAnswerableLastAccess;

    @Autowired
    public CheckVisibilite(ITokenRead tokenSevice, IUserRepositoryJPA repository, ILogAnswerableLastAccess logAnswerableLastAccess) {
        this.tokenSevice = tokenSevice;
        this.repository = repository;
        this.logAnswerableLastAccess = logAnswerableLastAccess;
    }

    @Override
    public int CheckAccess(String authHeader, int targetId){

        int ownId = ExtractIdFromToken(authHeader);
        int answerableId = repository.getAnswerableIdById(targetId);

        if (answerableId != ownId){
            throw new AccessDeniedException("Cannot Access this user");
        }

        logAnswerableLastAccess.LogAccess(ownId, targetId);

        return targetId;
    }

    @Override
    public int ExtractIdFromToken(String authHeader){
        String claim = tokenSevice.GetClaim(authHeader, "id");

        return (claim != null) ? Integer.parseInt(claim) : 0;
    }
}
