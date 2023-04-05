package com.statkevich.receipttask.printer;


import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.util.PrepareStringToPrintUtil;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Described class implement file output of {@link ReceiptDto}.
 */
public class FilePrinter implements Printer {
    @Override
    public void print(ReceiptDto receiptDto) {
        String receipt = PrepareStringToPrintUtil.prepareReceipt(receiptDto);
        try (FileWriter writer = new FileWriter("receipt.txt", false)) {
            writer.write(receipt);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
