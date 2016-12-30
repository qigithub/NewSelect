package com.qi.newselect.login;

import com.qi.newselect.base.IBaseVIew;

public interface ILogin extends IBaseVIew {

    String getUsernameText();

    String getPwdText();

    void setUsernameErrorText(String s);

    void setPwdErrorText(String s);

    void goActRegister();

    void goActMain();

    void setButtonText1(String text);

}