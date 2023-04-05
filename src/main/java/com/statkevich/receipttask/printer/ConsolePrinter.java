package com.statkevich.receipttask.printer;

import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.util.PrepareStringToPrintUtil;

import java.io.PrintWriter;

/**
 * Described class implement console output of {@link ReceiptDto}.
 */
public class ConsolePrinter implements Printer {
    private final PrintWriter writer;

    public ConsolePrinter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public void print(ReceiptDto receiptDto) {
        String receipt = PrepareStringToPrintUtil.prepareReceipt(receiptDto);
        writer.print(receipt);
        writer.flush();
    }
}
