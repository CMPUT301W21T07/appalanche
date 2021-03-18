package com.team007.appalanche.Experiment;

import com.team007.appalanche.barcode.Barcode;
import com.team007.appalanche.qrcode.QRCode;

public interface ExperimentInterface {
    public void obtainStatistics(); //change to lowercase o?
    public void obtainHistogram();
    public void obtainPlot();
    public void addQuestion();
    public void obtainMap();
    public QRCode generateQRCode();
}
