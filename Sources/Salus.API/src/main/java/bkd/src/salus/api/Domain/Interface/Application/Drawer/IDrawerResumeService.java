package bkd.src.salus.api.Domain.Interface.Application.Drawer;

import bkd.src.salus.api.Domain.DTO.Drawer.DrawerStatusDTO;

import java.util.List;

public interface IDrawerResumeService {
    List<DrawerStatusDTO> CaptureStatus(int userId);
}
