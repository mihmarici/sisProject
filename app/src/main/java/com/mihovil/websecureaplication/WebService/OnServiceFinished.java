package com.mihovil.websecureaplication.WebService;

/**
 * Created by Mihovil on 10.1.2018..
 */

public interface OnServiceFinished {

    void onRequestArrived(ServiceResponse message);
    void onRequestFailed(String message);
}
