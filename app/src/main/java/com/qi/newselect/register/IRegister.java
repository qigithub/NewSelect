package com.qi.newselect.register;

import java.io.File;

import com.qi.newselect.base.IBaseVIew;

/**
 * Created by dongqi on 2016/11/25.
 */
public interface IRegister extends IBaseVIew {

    String getNameText();

    String getPwdText();

    String getPwdText2();

    File getHeadFile();

    void successRegister();

}
