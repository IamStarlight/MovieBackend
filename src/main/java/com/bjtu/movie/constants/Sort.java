package com.bjtu.movie.constants;

public enum Sort {
    ORDER_BY_ASC("asc", "升序排列"),

    ODER_BY_DESC("desc", "降序排列"),

    /*
        ranking(算法排名）
         */
    SORT_BY_RATING("Rating", "按评分排列"),

    SORT_BY_NUMBER_OF_RATINGS("Number of ratings", "按评分人数排列"),

    SORT_BY_RELEASE_DATE("Release date", "按发布日期排列"),

    SORT_BY_POPULARITY("Popularity", "按受欢迎度排列"),

    SORT_BY_RUNTIME("Runtime", "按电影时长排列");

    private final String per;
    private final String desc;

    Sort(String per, String desc){
        this.per = per;
        this.desc = desc;
    }

    @Override
    public String toString(){
        return desc;
    }

    public String getValue() {
        return per;
    }
}
