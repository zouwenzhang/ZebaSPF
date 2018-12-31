package com.zeba.test;

import com.zeba.spf.BaseSpf;
import com.zeba.spf.annotation.SpfColumn;

public class BookSpf extends BaseSpf{

    private static BookSpf instance;

    public static BookSpf get(){
        if(instance==null){
            instance=new BookSpf();
        }
        return instance;
    }

    @SpfColumn("bookName")
    private String bookName;
    @SpfColumn("bookId")
    private Integer bookId;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
