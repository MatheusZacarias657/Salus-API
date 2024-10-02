package bkd.src.salus.api.Domain.Interface.Application.Auth;

public interface ICheckVisibilite {
    int CheckAccess(String authHeader, int targetId);

    int ExtractIdFromToken(String authHeader);
}
