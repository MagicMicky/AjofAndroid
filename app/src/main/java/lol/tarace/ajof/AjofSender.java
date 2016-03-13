package lol.tarace.ajof;

import android.app.Activity;
import android.net.Uri;

import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;

/**
 * Created by magicmicky on 13/03/16.
 */
public class AjofSender {
    private final Activity mContext;
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;

    public AjofSender(Activity context) {
        this.mContext = context;
    }


    public void send(String ajofAssetName) {
        String mimeType = "audio/mpeg";
        Uri contentUri = Uri.parse("android.resource://lol.tarace.ajof/raw/"+ajofAssetName);
        // contentUri points to the content being shared to Messenger
        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams.newBuilder(contentUri, mimeType)
                        .build();

        // Sharing from an Activity
        MessengerUtils.shareToMessenger(
                mContext,
                REQUEST_CODE_SHARE_TO_MESSENGER,
                shareToMessengerParams);

    }

}
