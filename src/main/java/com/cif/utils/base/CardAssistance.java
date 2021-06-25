package com.cif.utils.base;

import java.io.InputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public interface CardAssistance {

    ImageIcon getCardIcon();

    String getSuffix();

    String getTypeDescription();

    void openCard(String cardName, InputStream cardInputStream, String dataPthString, Object[] args);

    boolean isAddToDataManager();
}
