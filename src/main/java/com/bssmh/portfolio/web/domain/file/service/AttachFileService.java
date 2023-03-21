package com.bssmh.portfolio.web.domain.file.service;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.domain.enums.FileType;
import com.bssmh.portfolio.web.domain.file.repository.AttachFileRepository;
import com.bssmh.portfolio.web.exception.NoSuchAttachFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachFileService {

    private final AttachFileRepository attachFileRepository;

    public AttachFile findByFileUidOrElseThrow(String fileUid) {
        if (Objects.isNull(fileUid)) {
            throw new NoSuchAttachFileException();
        }

        return attachFileRepository.findByFileUid(fileUid)
                .orElseThrow(NoSuchAttachFileException::new);
    }

    public AttachFile findByFileUidOrElseNull(String fileUid) {
        if (Objects.isNull(fileUid)) {
            return null;
        }

        return attachFileRepository.findByFileUid(fileUid)
                .orElse(null);
    }

    public void save(AttachFileDto attachFileDto, FileType fileType) {
        AttachFile attachFile = AttachFile.create(
                attachFileDto.getFileUid(),
                attachFileDto.getFilePath(),
                attachFileDto.getFileName(),
                attachFileDto.getFileSize(),
                fileType
        );

        attachFileRepository.save(attachFile);
    }
}
