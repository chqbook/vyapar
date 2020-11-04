package com.chqbook.vypaar;

public interface ChqbookVypaarCallback {

    void onSuccess(String code,String transactionId);

    void onFailed(String code,String error);

}
