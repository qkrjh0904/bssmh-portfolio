package com.bssmh.portfolio.db.entity.attachfile;

import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private Long fileSize;

}
