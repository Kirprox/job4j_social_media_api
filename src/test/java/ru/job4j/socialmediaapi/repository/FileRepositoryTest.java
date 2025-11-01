package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.File;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileRepositoryTest {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void cleanDb() {
        postRepository.deleteAll();
        fileRepository.deleteAll();
    }

    @Test
    public void whenSaveFileThenFindById() {
        var file = new File();
        file.setName("picture1");
        file.setPath("/one");
        fileRepository.save(file);
        var foundFile = fileRepository.findById(file.getId());
        assertThat(foundFile).isPresent();
        assertThat(foundFile.get().getName()).isEqualTo("picture1");
    }

    @Test
    public void whenSaveFileThenUpdateFileHasSameFile() {
        var file = new File();
        file.setName("picture1");
        file.setPath("/one");
        fileRepository.save(file);
        file.setName("picture2");
        fileRepository.save(file);
        var foundFile = fileRepository.findById(file.getId());
        assertThat(foundFile).isPresent();
        assertThat(foundFile.get().getName()).isEqualTo("picture2");
    }

    @Test
    public void whenFindAllThenReturnAllFiles() {
        var file1 = new File();
        file1.setName("picture1");
        file1.setPath("/one");
        var file2 = new File();
        file2.setName("picture2");
        file2.setPath("/two");
        fileRepository.save(file1);
        fileRepository.save(file2);
        var files = fileRepository.findAll();
        assertThat(files).hasSize(2);
        assertThat(files).extracting(File::getName).contains("picture1", "picture2");
    }
}