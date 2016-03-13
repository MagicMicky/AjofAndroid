package lol.tarace.ajof;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.List;

/**
 * Created by magicmicky on 13/03/16.
 */
public class AjofPlayer {
    private final List<String> mAjofData;
    private final Context mContext;
    private MediaPlayer mPlayer;

    public AjofPlayer(List<String> dataset, Context context) {
        this.mAjofData = dataset;
        this.mContext = context;
    }

    public void initialize() {
        this.mPlayer = MediaPlayer.create(mContext, R.raw.a_30_wololo);
        mPlayer.start();
    }

    public void stop() {
        mPlayer.stop();
    }
    public void play(int itemClicked) throws IOException {
        int resID = mContext.getResources().getIdentifier(mAjofData.get(itemClicked), "raw", mContext.getPackageName());
        AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(resID);
        if(afd==null) return;
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mPlayer.prepare();
            mPlayer.start();
        }

    }
}
