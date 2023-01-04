package com.bssmh.portfolio.web.domain.file.service;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.domain.file.repository.AttachFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachFileService {

    private final AttachFileRepository attachFileRepository;

    public void save(AttachFileDto attachFileDto) {
        AttachFile attachFile = AttachFile.create(
                attachFileDto.getFileUid(),
                attachFileDto.getFilePath(),
                attachFileDto.getFileName(),
                attachFileDto.getFileSize());

        attachFileRepository.save(attachFile);
    }
}
