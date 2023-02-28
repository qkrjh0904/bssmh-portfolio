package com.bssmh.portfolio.web.path;

public class ApiPath {

    // error
    public static final String ERROR_AUTH = "/error/auth";

    // auth
    public static final String BSM_OAUTH = "/api/oauth/bsm";

    // oauth2
    public static final String LOGIN_OAUTH2 = "/api/login/oauth2";
    public static final String REFRESH_TOKEN = "/api/refresh-token";

    // member
    public static final String MEMBER = "/api/member";
    public static final String MEMBER_SIGNUP = "/api/member/signup";
    public static final String MEMBER_SELF = "/api/member/self";
    public static final String MEMBER_ID = "/api/member/{member-id}";
    public static final String MEMBER_NAME = "/api/member/name";
    public static final String MEMBER_ID_MATCHER = "/api/member/{member-id:\\d+}";

    // portfolio
    public static final String PORTFOLIO = "/api/portfolio";
    public static final String PORTFOLIO_SEQUENCE = "/api/portfolio/sequence";
    public static final String PORTFOLIO_VIEWS_ADD = "/api/portfolio/views/add";
    public static final String PORTFOLIO_BOOKMARK = "/api/portfolio/bookmark";
    public static final String PORTFOLIO_ID = "/api/portfolio/{portfolio-id}";
    public static final String PORTFOLIO_ID_MATCHER = "/api/portfolio/{portfolio-id:\\d+}";
    public static final String PORTFOLIO_SEARCH = "/api/portfolio/search";
    public static final String PORTFOLIO_MEMBER_ID = "/api/portfolio/member/{member-id}";
    public static final String PORTFOLIO_SELF = "/api/portfolio/self";

    // file
    public static final String FILE_UPLOAD = "/api/file/upload";
    public static final String FILE_DOWNLOAD = "/api/file/download/{file-uid}";

    // comment
    public static final String COMMENT = "/api/comment";
    public static final String COMMENT_PORTFOLIO_ID = "/api/comment/{portfolio-id}";
    public static final String COMMENT_PORTFOLIO_ID_MATCHER = "/api/comment/{portfolio-id:\\d+}";

    // skill
    public static final String SKILL = "/api/skill";

    // follow
    public static final String FOLLOW = "/api/follow";
    public static final String UNFOLLOW = "/api/follow/unfollow";
    public static final String FOLLOWER_SELF = "/api/follow/follower";
    public static final String FOLLOWING_SELF = "/api/follow/following";
    public static final String FOLLOWER_MEMBER_ID = "/api/follow/follower/{member-id}";
    public static final String FOLLOWING_MEMBER_ID = "/api/follow/following/{member-id}";
}
