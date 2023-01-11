package com.bssmh.portfolio.web.domain.file.repository;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {
    Optional<AttachFile> findByFileUid(String fileUid);
}
