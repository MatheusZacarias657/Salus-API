package bkd.src.salus.api.Domain.Interface.Application.FileManager;

import bkd.src.salus.api.Domain.DTO.File.FileResponseDTO;

public interface IFileManager {
    FileResponseDTO GetFile(String fileName);
}
