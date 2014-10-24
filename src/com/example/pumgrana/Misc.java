package com.example.pumgrana;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

// TODO: Auto-generated Javadoc
/**
 * Misc class contains global variables.
 */
public class Misc {
	
	/** The m context. */
	Context mContext;
    
    /** The ip. */
    private String ip = "163.5.84.222";
	
	/**
	 * Instantiates a new misc.
	 *
	 * @param cont the cont
	 */
	Misc(Context cont) {
		this.mContext = cont;
	}
	
    /**
     * Checks if is online.
     *
     * @return true, if is online
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    
    /**
     * Gets the ip.
     *
     * @return the ip
     */
    public String getIp() {
    	return this.ip;
    }
}
