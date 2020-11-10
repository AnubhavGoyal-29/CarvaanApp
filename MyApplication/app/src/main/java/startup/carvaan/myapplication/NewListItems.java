package startup.carvaan.myapplication;

import android.widget.TextView;

public class NewListItems {
    String mBtnText;
    String mTextViewText;

    public NewListItems(String mTextViewText,String mBtnText) {
        this.mBtnText = mBtnText;
        this.mTextViewText = mTextViewText;
    }


    public String getmBtnText() {
        return mBtnText;
    }

    public String getmTextViewText() {
        return mTextViewText;
    }
}
