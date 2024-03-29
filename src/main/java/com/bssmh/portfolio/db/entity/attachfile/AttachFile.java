package com.bssmh.portfolio.db.entity.attachfile;

import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.web.domain.enums.FileType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = @Index(name = "idx_file_uid", columnList = "fileUid")
)
public class AttachFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_file_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String fileUid;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    public static AttachFile create(String fileUid, String filePath, String fileName, Long fileSize, FileType fileType) {
        AttachFile attachFile = new AttachFile();
        attachFile.fileUid = fileUid;
        attachFile.filePath = filePath;
        attachFile.fileName = fileName;
        attachFile.fileSize = fileSize;
        attachFile.fileType = fileType;
        return attachFile;
    }
}
