package bkd.src.salus.api.Domain.Interface.Application.Drawer;

import bkd.src.salus.api.Domain.DTO.Drawer.DetailingDrawerDTO;
import bkd.src.salus.api.Domain.DTO.Drawer.RegisterDrawerDTO;

import java.util.List;

public interface IDrawerService {
    DetailingDrawerDTO Register(RegisterDrawerDTO register, int userId);
    List<DetailingDrawerDTO> FindByUserId(int userId);
}
