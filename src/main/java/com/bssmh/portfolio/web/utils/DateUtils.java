package com.bssmh.portfolio.web.utils;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class DateUtils {

    public Date now() {
        return new Date();
    }

    public Date addTime(Date date, long millisecond) {
        return new Date(date.getTime() + millisecond);
    }

}
