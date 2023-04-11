package com.statkevich.receipttask.printer;

/**
 * Described class used to instantiate implementations of Printer interface.
 * Must be implemented by {@link com.statkevich.receipttask.printer.ConsolePrinterFactory},
 * {@link com.statkevich.receipttask.printer.FilePrinterFactory}
 */
public interface PrinterFactory {
    Printer createPrinter();
}
