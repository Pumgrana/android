package com.example.pumgrana;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Misc {
	Context mContext;
    private String ip = "163.5.84.222";
	
	Misc(Context cont) {
		this.mContext = cont;
	}
	
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    
    public String getIp() {
    	return this.ip;
    }
}
