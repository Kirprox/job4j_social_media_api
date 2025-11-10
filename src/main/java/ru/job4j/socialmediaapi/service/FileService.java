package ru.job4j.socialmediaapi.service;

import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.File;

import java.util.List;
import java.util.Optional;

public interface FileService {
    File save(FileDto fileDto);

    Optional<FileDto> getFileById(Long id);

    void deleteById(Long id);

    List<File> save(List<FileDto> dtos);

}
