package com.bssmh.portfolio.web.domain.member.controller.rq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateMemberRq {
    private String nickName;
    private String profileImageUid;
    private String description;
    private String phone;
}
