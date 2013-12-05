package com.niltonvasques.tictactoe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends AndroidApplication implements IAdRequestHandler{
	private static final int SHOW_ADS = 1;
	private static final int HIDE_ADS = 0;
    private AdView adView;
    
    private Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case SHOW_ADS:
				adView.setVisibility(View.VISIBLE);
				break;
			case HIDE_ADS:
				adView.setVisibility(View.GONE);
				break;
			default:
				break;
			}
    	};
    };

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        TicTacToeGame game = new TicTacToeGame();
        game.registerAdRequestHandler(this);
        
        // Create the libgdx View
        View gameView = initializeForView(game, false);

        // Create and setup the AdMob view
        adView = new AdView(this, AdSize.BANNER, "a1527f2ab6a9fe5"); // Put in your secret key here
        AdRequest request = new AdRequest();
//        Set<String> devices = new HashSet<String>();
//        devices.add(AdRequest.TEST_EMULATOR);
//        devices.add("93CB76627E24248FF7A79A9AB6B461A0");

//        request.setTestDevices(devices);
        adView.loadAd(request);

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
        	new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
        			RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);
        
//        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
//        cfg.useGL20 = false;
//        
//        initialize(new TicTacToeGame(), cfg);
    }

	@Override
	public void request(boolean show) {
		handler.sendEmptyMessage( show ? SHOW_ADS : HIDE_ADS);		
	}
}