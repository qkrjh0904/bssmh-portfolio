package com.bssmh.portfolio.web.path;

public class ApiPath {

    //auth
    public static final String BSM_OAUTH = "/api/oauth/bsm";

    // member
    public static final String MEMBER = "/api/member";
    public static final String MEMBER_SELF = "/api/member/self";
    public static final String MEMBER_ID = "/api/member/{member-id}";

    // portfolio
    public static final String PORTFOLIO = "/api/portfolio";
    public static final String PORTFOLIO_BOOKMARK = "/api/portfolio/bookmark";
    public static final String PORTFOLIO_ID = "/api/portfolio/{portfolio-id}";
    public static final String PORTFOLIO_SEARCH = "/api/portfolio/search";
    public static final String PORTFOLIO_MEMBER_ID = "/api/portfolio/member/{member-id}";
    public static final String PORTFOLIO_SELF = "/api/portfolio/self";

    // file
    public static final String FILE_UPLOAD = "/api/file/upload";
    public static final String FILE_DOWNLOAD = "/api/file/download/{file-uid}";

    // comment
    public static final String COMMENT = "/api/comment";
    public static final String COMMENT_PORTFOLIO_ID = "/api/comment/{portfolio-id}";

    // skill
    public static final String SKILL = "/api/skill";

    // follow
    public static final String FOLLOW = "/api/follow";
    public static final String UNFOLLOW = "/api/follow/{member-id}";
    public static final String FOLLOWER_SELF = "/api/follower";
    public static final String FOLLOWING_SELF = "/api/following";
    public static final String FOLLOWER_MEMBER_ID = "/api/follower/{member-id}";
    public static final String FOLLOWING_MEMBER_ID = "/api/following/{member-id}";
}
