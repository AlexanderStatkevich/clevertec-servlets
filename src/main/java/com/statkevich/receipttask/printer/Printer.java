package com.statkevich.receipttask.printer;

import com.statkevich.receipttask.dto.ReceiptDto;

/**
 * Described class used to handle output of application.
 * Must be implemented by {@link com.statkevich.receipttask.printer.ConsolePrinter},
 * {@link com.statkevich.receipttask.printer.FilePrinter}
 */
public interface Printer {

    void print(ReceiptDto receiptDto);
}
