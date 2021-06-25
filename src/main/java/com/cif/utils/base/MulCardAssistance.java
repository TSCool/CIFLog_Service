package com.cif.utils.base;

import java.io.InputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author xsj
 */
public interface MulCardAssistance {

    ImageIcon getCardIcon();

    String getSuffix();

    String getTypeDescription();

    void openCard(String cardName, InputStream cardInputStream, String dataPthString, Object[] args);

    boolean isAddToDataManager();
}