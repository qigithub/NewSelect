package com.qi.newselect.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.qi.newselect.R;


public class BaseDialog extends Dialog implements OnClickListener {
	private OnBaseDialogListener mDialogListener;
	private View mView;
	private Context mContext;
	private LayoutInflater mInflater;
	private Button btConfirm,btCancel;
    private TextView tvTitle,tvContent;

    /**
     * 需要调用show方法
     * @param ctx
     * @return
     */
    public static BaseDialog getInstance(Context ctx) {
        BaseDialog baseDialog = new BaseDialog(ctx);
        return baseDialog;
    }

	public BaseDialog(Context context) {
		this(context, 0);
	}

	public BaseDialog(Context context, int contentView) {
		super(context, R.style.FullScreenDialog);
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mView = mInflater.inflate(R.layout.dialog_my_base, null);
        btConfirm = (Button) mView.findViewById(R.id.btConfirm);
        btCancel = (Button) mView.findViewById(R.id.btCancel);
        tvTitle = (TextView) mView.findViewById(R.id.tvTitle);
        tvTitle.setText("提示");
        tvContent = (TextView) mView.findViewById(R.id.tvContent);
        btConfirm.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        setContentView(mView);
		this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
	}

	public BaseDialog setOnDialogListener(OnBaseDialogListener dialogListener) {
		this.mDialogListener = dialogListener;
        return this;
	}

	public BaseDialog setButtonConfirmText(String text) {
        if (text == null) {
            return this;
        }
		btConfirm.setText(text);
        return this;
	}
	public BaseDialog setButtonConfirmText(int text) {
        if (text == 0) {
            return this;
        }
        btConfirm.setText(text);
        return this;
	}

    public BaseDialog setButtonCancelText(String text) {
        if (text == null) {
            return this;
        }
        btCancel.setText(text);
        return this;
    }
    public BaseDialog setButtonCancelText(int text) {
        if (text == 0) {
            return this;
        }
        btCancel.setText(text);
        return this;
    }

    public BaseDialog setTitleText(String text) {
        if (text == null) {
            tvTitle.setVisibility(View.GONE);
            return this;
        }
        tvTitle.setText(text);
        return this;
    }
    public BaseDialog setTitleText(int text) {
        if (text == 0) {
            tvTitle.setVisibility(View.GONE);
            return this;
        }
        tvTitle.setText(text);
        return this;
    }

    public BaseDialog setContentText(String text) {
        if (text == null) {
            tvContent.setVisibility(View.GONE);
            return this;
        }
        tvContent.setText(text);
        return this;
    }
    public BaseDialog setContentText(int text) {
        if (text == 0) {
            tvContent.setVisibility(View.GONE);
            return this;
        }
        tvContent.setText(text);
        return this;
    }

    public BaseDialog setTypeFace(Typeface tf) {
        if (tf == null)
            return this;
        tvTitle.setTypeface(tf);
        tvContent.setTypeface(tf);
        btConfirm.setTypeface(tf);
        btCancel.setTypeface(tf);
        return  this;
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btConfirm && mDialogListener != null) {
			mDialogListener.onConfirmClick();
        } else if (v.getId() == R.id.btCancel && mDialogListener != null) {
            mDialogListener.onCancelClick();
        }
        cancel();
	}

    public interface OnBaseDialogListener{
        void onConfirmClick();
        void onCancelClick();
    }

}
