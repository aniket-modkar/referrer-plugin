package io.aniket.referrer;

import android.os.RemoteException;
import android.util.Log;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.getcapacitor.JSObject;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Referrer")
public class ReferrerPlugin extends Plugin {

    private Referrer implementation = new Referrer();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void getReferrerDetails(PluginCall call) {
        InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(getContext()).build();
        referrerClient.startConnection(
            new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    switch (responseCode) {
                        case InstallReferrerClient.InstallReferrerResponse.OK:
                            try {
                                ReferrerDetails response = referrerClient.getInstallReferrer();
                                String referrerUrl = response.getInstallReferrer();
                                long referrerClickTime = response.getReferrerClickTimestampSeconds();
                                long appInstallTime = response.getInstallBeginTimestampSeconds();
                                boolean instantExperienceLaunched = response.getGooglePlayInstantParam();
                                JSObject ret = new JSObject();
                                Log.d("Plugin", referrerUrl);
                                ret.put("referrerUrl", referrerUrl);
                                ret.put("referrerClickTime", referrerClickTime);
                                ret.put("appInstallTime", appInstallTime);
                                ret.put("instantExperienceLaunched", instantExperienceLaunched);
                                call.resolve(ret);
                            } catch (RemoteException e) {
                                Log.d("Plugin", "Cannot get ReferrerDetails");

                                call.reject("Cannot get ReferrerDetails");
                            }
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                            Log.d("Plugin", "FEATURE_NOT_SUPPORTED");

                            call.reject("Cannot get ReferrerDetails: FEATURE_NOT_SUPPORTED");
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                            Log.d("Plugin", "SERVICE_UNAVAILABLE");

                            call.reject("Cannot get ReferrerDetails: SERVICE_UNAVAILABLE");
                            break;
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                }
            }
        );
    }
}
