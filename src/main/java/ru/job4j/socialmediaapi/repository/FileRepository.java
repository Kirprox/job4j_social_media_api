package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.File;

public interface FileRepository extends CrudRepository<File, Long> {
    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE FROM File file
            WHERE file.id = :id""")
    void deleteFileById(@Param("id") Long fileId);
}
