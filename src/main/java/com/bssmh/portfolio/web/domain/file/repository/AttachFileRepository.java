package com.bssmh.portfolio.web.domain.file.repository;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {
}
