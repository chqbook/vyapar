package com.chqbook.vypaar;

import java.util.HashSet;


public class CookieData {

    private String cl_pg_txs;
    private String lvl_session;
    private HashSet<String> cookie;
    public static  CookieData sInstance = new CookieData();

    private CookieData() {

    }

    public static CookieData getInstance() {
        if (sInstance == null) {
            sInstance = new CookieData();
        }
        return sInstance;
    }

    public String getCl_pg_txs() {
        return cl_pg_txs;
    }

    public void setCl_pg_txs(String cl_pg_txs) {
        this.cl_pg_txs = cl_pg_txs;
    }

    public HashSet<String> getCookie(){
        return cookie;
    }

    public void setCookie(HashSet<String> cookie) {
        this.cookie = cookie;
    }

    public String getLvl_session() {
        return lvl_session;
    }

    public void setLvl_session(String lvl_session) {
        this.lvl_session = lvl_session;
    }

}
