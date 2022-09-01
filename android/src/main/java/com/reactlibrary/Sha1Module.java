package com.sha1lib;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.*;

import java.util.UUID;

public class Sha1Module extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public Sha1Module(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "sha1Lib";
  }

  @ReactMethod
  public void sha1(final String toHash, Promise promise) {

 MessageDigest digest = null;
      try {
          MessageDigest md = MessageDigest.getInstance("SHA-1");
          byte[] textBytes = toHash.getBytes("UTF-8");
          md.update(textBytes, 0, textBytes.length);
          byte[] sha1hash = md.digest();
         
          StringBuilder buf = new StringBuilder();
            for (byte b : sha1hash) {
                int halfbyte = (b >>> 4) & 0x0F;
                int two_halfs = 0;
                do {
                    buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                    halfbyte = b & 0x0F;
                } while (two_halfs++ < 1);
            }
           
          promise.resolve(buf.toString());

      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
          promise.reject("sha1", e.getMessage());
      }catch (UnsupportedEncodingException e) {
          e.printStackTrace();
          promise.reject("sha1", e.getMessage());
      }
  }

}
