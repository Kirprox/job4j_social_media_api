package ru.job4j.socialmediaapi.dto;

import lombok.Data;

@Data
public class FileDto {
    private String name;
    private byte[] content;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
}
