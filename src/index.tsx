import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-sunmi-printer' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const SunmiPrinter = NativeModules.SunmiPrinter
  ? NativeModules.SunmiPrinter
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

/**
 * @enum {ALIGN}
 */
const ALIGN_CONSTANTS = {
  /** @type {Number} LEFT - left align */
  LEFT: 0,
  /** @type {Number} CENTER - center align */
  CENTER: 1,
  /** @type {Number} RIGHT - right align */
  RIGHT: 2
}
/**
 * @enum {BARCODE_TYPE}
 */
const BARCODE_TYPE_CONSTANTS = {
  /** @type {Number} UPCA */
  UPCA: 0,
  /** @type {Number} UPCE */
  UPCE: 1,
  /** @type {Number} JAN13 */
  JAN13: 2,
  /** @type {Number} JAN8 */
  JAN8: 3,
  /** @type {Number} CODE39 */
  CODE39: 4,
  /** @type {Number} ITF */
  ITF: 5,
  /** @type {Number} CODABAR */
  CODABAR: 6,
  /** @type {Number} CODE93 */
  CODE93: 7,
  /** @type {Number} CODE128 */
  CODE128: 8
}
/**
 * @enum {BARCODE_TEXT_POSITION}
 */
const BARCODE_TEXT_POSITION_CONSTANTS = {
  /** @type {Number} NO_PRINT - don't print text of barcode */
  NO_PRINT: 0,
  /** @type {Number} ABOVE - print text of barcode above barcode */
  ABOVE: 1,
  /** @type {Number} BELOW - don't print text of barcode below barcode */
  BELOW: 2,
  /** @type {Number} BOTH - don't print text of barcode above and below barcode */
  BOTH: 3
}
/**
 * @enum {QR_ERROR_LEVEL}
 */
const QR_ERROR_LEVEL_CONSTANTS = {
  /** @type {Number} L - QR Code error level of L ~ 7% */
  L: 0,
  /** @type {Number} M - QR Code error level of M ~ 15% */
  M: 1,
  /** @type {Number} Q - QR Code error level of Q ~ 25% */
  Q: 2,
  /** @type {Number} H - QR Code error level of H ~ 30% */
  H: 3
}

const SPrinter = {
  Constants: {
    Align: ALIGN_CONSTANTS,
    Barcode: BARCODE_TYPE_CONSTANTS,
    BarcodeText: BARCODE_TEXT_POSITION_CONSTANTS,
    QRError: QR_ERROR_LEVEL_CONSTANTS
  },

  /**
   * This function connects to the printer
   * @example
   * // returns { success: true }
   * await SPrinter.connect();
   * @returns {Promise<any>} success message.
   */
  connect: async function connect(): Promise<any> {
    return await SunmiPrinter.connect();
  },

  /**
   * This function disconnects the printer
   * @example
   * // returns { success: true }
   * await SPrinter.disconnect();
   * @returns {Promise<any>} success message.
   */
  disconnect: async function disconnect(): Promise<any> {
    return await SunmiPrinter.disconnect();
  },

  /**
   * This function resets any printing adjustments sent(e.g align or font size)
   * @example
   * // returns { success: true }
   * await SPrinter.reset();
   * @returns {Promise<any>} success message.
   */
  reset: async function reset(): Promise<any> {
    return await SunmiPrinter.reset();
  },

  /**
   * This function runs the default test print
   * @example
   * // returns { success: true }
   * await SPrinter.testPrint();
   * @returns {Promise<any>} success message.
   */
  testPrint: async function testPrint(): Promise<any> {
    return await SunmiPrinter.testPrint();
  },


  /**
   * This function returns the printer specs including model, paper, printer version and serial no
   * @example
   * // returns {DeviceModel: 'T2-GPIOINT\n', PrintPaper: '80mm', PrinterVersion: '1.05\n', SerialNo: 'XXXXXXXXXXXXXXXXXXXX'}
   * await SPrinter.getPrinterSpecs();
   * @returns {Promise<any>} Spec object.
   */
  getPrinterSpecs: async function getPrinterSpecs(): Promise<any> {
    return await SunmiPrinter.printerSpecs();
  },

  /**
   * This function returns the printer current status such as printing or running
   * @example
   * // returns "Printer is running"
   * await SPrinter.getPrinterStatus();
   * @returns {Promise<String>} success message.
   */
  getPrinterStatus: async function getPrinterStatus(): Promise<any> {
    return await SunmiPrinter.printerStatus();
  },

  /**
   * This function cuts the paper
   * @example
   * // returns { success: true }
   * await SPrinter.cutPaper();
   * @returns {Promise<any>} success message.
   */
  cutPaper: async function cutPaper(): Promise<any> {
    return await SunmiPrinter.cutPaper();
  },

  /**
   * This function prints a given text
   * @param {String} text - text to print
   * @example
   * // returns { success: true }
   * await SPrinter.printText("Example of printing");
   * @returns {Promise<any>} success message.
   */
  printText: async function printText(text: String): Promise<any> {
    return await SunmiPrinter.printText(text);
  },

  /**
   * This function prints a given text with styling of text
   * @param {String} text - text to print
   * @param {Number} size - size of text
   * @param {boolean} isBold - whether text is bold
   * @param {boolean} isUnderline - whether text is underelined
   * @param {String} typeface - typeface of text
   * @example
   * // returns { success: true }
   * await SPrinter.printTextCustom("Example of custom printing", "28", true, true, "gh");
   * @returns {Promise<any>} success message.
   */
  printTextCustom: async function printTextCustom(
    text: String,
    size: Number,
    isBold: boolean,
    isUnderline: boolean,
    typeface: String
  ): Promise<any> {
    return await SunmiPrinter.printTextCustom(
      text,
      size,
      isBold,
      isUnderline,
      typeface
    );
  },

  /**
   * This function prints a number of empty lines
   * @param {Number} num - num of empty lines
   * @example
   * // returns { success: true }
   * await SPrinter.printEmptyLines(3);
   * @returns {Promise<any>} success message.
   */
  printEmptyLines: async function printEmptyLines(num: Number): Promise<any> {
    return await SunmiPrinter.printEmptyLines(num);
  },

  /**
   * This function sets the alignment of next print
   * @param {ALIGN} align - align to set
   * @example
   * // returns { success: true }
   * await SPrinter.setAlign(SPrinter.Constants.Align.CENTER);
   * @returns {Promise<any>} success message.
   */
  setAlign: async function setAlign(align: Number): Promise<any> {
    return await SunmiPrinter.setAlign(align);
  },

  /**
   * This function sets the font size of next print
   * @param {Number} fontSize - font size to set
   * @example
   * // returns { success: true }
   * await SPrinter.setFontSize(28);
   * @returns {Promise<any>} success message.
   */
  setFontSize: async function setFontSize(fontSize: Number): Promise<any> {
    return await SunmiPrinter.setFontSize(fontSize);
  },

  /**
   * This function prints a barcode given data
   * @param {String} data - data of barcode
   * @param {BARCODE_TYPE} symbology - symbol of barcode type
   * @param {Number} height - height of barcode
   * @param {Number} width - width of barcode
   * @param {BARCODE_TEXT_POSITION} textPosition - position of text
   * @example
   * // returns { success: true }
   * await SPrinter.printBarCode("SecretABC", SPrinter.Constants.Barcode.CODE39, 90, 10, SPrinter.Constants.BarcodeText.ABOVE);
   * @returns {Promise<any>} success message.
   */
  printBarCode: async function printBarCode(
    data: String,
    symbology: Number,
    height: Number,
    width: Number,
    textPosition: Number
  ): Promise<any> {
    return await SunmiPrinter.printBarCode(
      data,
      symbology,
      height,
      width,
      textPosition
    );
  },

  /**
   * This function prints a qr code given data
   * @param {String} data - data of qr code
   * @param {Number} modulesize - module size of qr code
   * @param {QR_ERROR_LEVEL} errorlevel - error level of qr code
   * @example
   * // returns { success: true }
   * await SPrinter.printQRCode("URL TO SOME SECRET", 10, 15);
   * @returns {Promise<any>} success message.
   */
  printQRCode: async function printQRCode(
    data: String,
    modulesize: Number,
    errorlevel: Number
  ): Promise<any> {
    return await SunmiPrinter.printQRCode(data, modulesize, errorlevel);
  },

  /**
   * This function prints a table given data
   * @param {String[]} texts - an array of texts to print
   * @param {Number[]} width - an array defining width of corresponding text
   * @param {Number[]} align - an array defining align of corresponding text
   * @example
   * // returns { success: true }
   * await SPrinter.printTable(['a','b','c'], [3,3,5], [1,2,0]);
   * @returns {Promise<any>} success message.
   */
  printTable: async function printTable(
    texts: String[],
    width: Number[],
    align: Number[]
  ): Promise<any> {
    return await SunmiPrinter.printTable(texts, width, align);
  },

  /**
   * This function prints an image given the base 64 string
   * @param {String} base64 - base64 string of image
   * @example
   * // returns { success: true }
   * await SPrinter.printBase64Image('some base 64 image string');
   * @returns {Promise<any>} success message.
   */
  printBase64Image: async function printBase64Image(
    base64: String
  ): Promise<any> {
    return await SunmiPrinter.printBase64Image(base64);
  },
};

export { SPrinter };
