# React-Native-Sunmi-Printer
Sunmi devices have built in printers. This library extends the java code such that the functions can be used
in react native. More over, all sunmi devices(at least printers) run on android so this library is only
available for android.

## To implement after V1:
&check; Basic printer connection<br />
&check; Test printing<br />
&check; Get printer status and specs<br />
&check; Print text, set align and bold<br />
&check; Print images<br />
&cross; Set bold<br />
&cross; Print bar code<br />
&cross; Print tables<br />
&cross; Print QR codes<br />
&cross; Checking params for printing qrcode, tables and barcode<br />

## Installation
Using npm:
```
npm install --save @makgabri/react-native-sunmi-printer
```
or using yarn:
```
yarn add @makgabri/react-native-sunmi-printer
```

## Example Import
```js
import { SPrinter, Constants } from '@makgabri/react-native-sunmi-printer';

// ...
await SPrinter.connect();
await SPrinter.testPrint();
await SPrinter.disconnect();
```
Below is the api documentation to execute other functions in the library including printing 
- texts
- images
- setting align/bold
- ...etc

## Function returns
All functions run async as the printer may need time to respond. When successful or a json object is returned
such as the case for getting specs, the promise will resolve an object. Example:
```js
try {
    // Successful response
    await SPrinter.connect();
    // { successful: true }

    // Successfull spec response
    await SPrinter.getPrinterSpecs();
    //{DeviceModel: 'T2-GPIOINT\n', PrintPaper: '80mm', PrinterVersion: '1.05\n', SerialNo: 'XXXXXXXXXXXXXXXXXXXX'}
} catch(e) {
    console.log(e.code) // Code for error
    console.log(e.message) // Message for error
}
```

## Errors
| Error Code | Error Message |
|------------|---------------|
| -1         | Printer service empty, please connect printer first|
| -2         | Remote exception error(refer to message to get more details) |

<!-- Generated by documentation.js. Update this documentation by updating the source code. -->

### Table of Contents

*   [connect][1]
    *   [Examples][2]
*   [disconnect][3]
    *   [Examples][4]
*   [reset][5]
    *   [Examples][6]
*   [testPrint][7]
    *   [Examples][8]
*   [getPrinterSpecs][9]
    *   [Examples][10]
*   [getPrinterStatus][11]
    *   [Examples][12]
*   [cutPaper][13]
    *   [Examples][14]
*   [printText][15]
    *   [Parameters][16]
    *   [Examples][17]
*   [printTextCustom][18]
    *   [Parameters][19]
    *   [Examples][20]
*   [printEmptyLines][21]
    *   [Parameters][22]
    *   [Examples][23]
*   [setAlign][24]
    *   [Parameters][25]
    *   [Examples][26]
*   [setFontSize][27]
    *   [Parameters][28]
    *   [Examples][29]
*   [printBarCode][30]
    *   [Parameters][31]
    *   [Examples][32]
*   [printQRCode][33]
    *   [Parameters][34]
    *   [Examples][35]
*   [printTable][36]
    *   [Parameters][37]
    *   [Examples][38]
*   [printBase64Image][39]
    *   [Parameters][40]
    *   [Examples][41]
*   [ALIGN_CONSTANTS][42]
    *   [LEFT][43]
    *   [CENTER][44]
    *   [RIGHT][45]
*   [BARCODE_TYPE_CONSTANTS][46]
    *   [UPCA][47]
    *   [UPCE][48]
    *   [JAN13][49]
    *   [JAN8][50]
    *   [CODE39][51]
    *   [ITF][52]
    *   [CODABAR][53]
    *   [CODE93][54]
    *   [CODE128][55]
*   [BARCODE_TEXT_POSITION_CONSTANTS][56]
    *   [NO_PRINT][57]
    *   [ABOVE][58]
    *   [BELOW][59]
    *   [BOTH][60]
*   [QR_ERROR_LEVEL_CONSTANTS][61]
    *   [L][62]
    *   [M][63]
    *   [Q][64]
    *   [H][65]

## connect

This function connects to the printer

### Examples

```javascript
// returns { success: true }
await SPrinter.connect();
```

Returns **[Promise][66]\<any>** success message.

## disconnect

This function disconnects the printer

### Examples

```javascript
// returns { success: true }
await SPrinter.disconnect();
```

Returns **[Promise][66]\<any>** success message.

## reset

This function resets any printing adjustments sent(e.g align or font size)

### Examples

```javascript
// returns { success: true }
await SPrinter.reset();
```

Returns **[Promise][66]\<any>** success message.

## testPrint

This function runs the default test print

### Examples

```javascript
// returns { success: true }
await SPrinter.testPrint();
```

Returns **[Promise][66]\<any>** success message.

## getPrinterSpecs

This function returns the printer specs including model, paper, printer version and serial no

### Examples

```javascript
// returns {DeviceModel: 'T2-GPIOINT\n', PrintPaper: '80mm', PrinterVersion: '1.05\n', SerialNo: 'XXXXXXXXXXXXXXXXXXXX'}
await SPrinter.getPrinterSpecs();
```

Returns **[Promise][66]\<any>** Spec object.

## getPrinterStatus

This function returns the printer current status such as printing or running

### Examples

```javascript
// returns "Printer is running"
await SPrinter.getPrinterStatus();
```

Returns **[Promise][66]<[String][67]>** success message.

## cutPaper

This function cuts the paper

### Examples

```javascript
// returns { success: true }
await SPrinter.cutPaper();
```

Returns **[Promise][66]\<any>** success message.

## printText

This function prints a given text

### Parameters

*   `text` **[String][67]** text to print

### Examples

```javascript
// returns { success: true }
await SPrinter.printText("Example of printing");
```

Returns **[Promise][66]\<any>** success message.

## printTextCustom

This function prints a given text with styling of text

### Parameters

*   `text` **[String][67]** text to print
*   `size` **[Number][68]** size of text
*   `isBold` **[boolean][69]** whether text is bold
*   `isUnderline` **[boolean][69]** whether text is underelined
*   `typeface` **[String][67]** typeface of text

### Examples

```javascript
// returns { success: true }
await SPrinter.printTextCustom("Example of custom printing", "28", true, true, "gh");
```

Returns **[Promise][66]\<any>** success message.

## printEmptyLines

This function prints a number of empty lines

### Parameters

*   `num` **[Number][68]** num of empty lines

### Examples

```javascript
// returns { success: true }
await SPrinter.printEmptyLines(3);
```

Returns **[Promise][66]\<any>** success message.

## setAlign

This function sets the alignment of next print

### Parameters

*   `align` **ALIGN** align to set

### Examples

```javascript
// returns { success: true }
await SPrinter.setAlign(Constants.Align.CENTER);
```

Returns **[Promise][66]\<any>** success message.

## setFontSize

This function sets the font size of next print

### Parameters

*   `fontSize` **[Number][68]** font size to set

### Examples

```javascript
// returns { success: true }
await SPrinter.setFontSize(28);
```

Returns **[Promise][66]\<any>** success message.

## printBarCode

This function prints a barcode given data

### Parameters

*   `data` **[String][67]** data of barcode
*   `symbology` **BARCODE_TYPE** symbol of barcode type
*   `height` **[Number][68]** height of barcode
*   `width` **[Number][68]** width of barcode
*   `textPosition` **BARCODE_TEXT_POSITION** position of text

### Examples

```javascript
// returns { success: true }
await SPrinter.printBarCode("SecretABC", Constants.Barcode.CODE39, 90, 10, Constants.BarcodeText.ABOVE);
```

Returns **[Promise][66]\<any>** success message.

## printQRCode

This function prints a qr code given data

### Parameters

*   `data` **[String][67]** data of qr code
*   `modulesize` **[Number][68]** module size of qr code
*   `errorlevel` **QR_ERROR_LEVEL** error level of qr code

### Examples

```javascript
// returns { success: true }
await SPrinter.printQRCode("URL TO SOME SECRET", 10, 15);
```

Returns **[Promise][66]\<any>** success message.

## printTable

This function prints a table given data

### Parameters

*   `texts` **[Array][70]<[String][67]>** an array of texts to print
*   `width` **[Array][70]<[Number][68]>** an array defining width of corresponding text
*   `align` **[Array][70]<[Number][68]>** an array defining align of corresponding text

### Examples

```javascript
// returns { success: true }
await SPrinter.printTable(['a','b','c'], [3,3,5], [1,2,0]);
```

Returns **[Promise][66]\<any>** success message.

## printBase64Image

This function prints an image given the base 64 string

### Parameters

*   `base64` **[String][67]** base64 string of image

### Examples

```javascript
// returns { success: true }
await SPrinter.printBase64Image('some base 64 image string');
```

Returns **[Promise][66]\<any>** success message.

## ALIGN_CONSTANTS

Type: ALIGN

### LEFT

Type: [Number][68]

### CENTER

Type: [Number][68]

### RIGHT

Type: [Number][68]

## BARCODE_TYPE_CONSTANTS

Type: BARCODE_TYPE

### UPCA

Type: [Number][68]

### UPCE

Type: [Number][68]

### JAN13

Type: [Number][68]

### JAN8

Type: [Number][68]

### CODE39

Type: [Number][68]

### ITF

Type: [Number][68]

### CODABAR

Type: [Number][68]

### CODE93

Type: [Number][68]

### CODE128

Type: [Number][68]

## BARCODE_TEXT_POSITION_CONSTANTS

Type: BARCODE_TEXT_POSITION

### NO_PRINT

Type: [Number][68]

### ABOVE

Type: [Number][68]

### BELOW

Type: [Number][68]

### BOTH

Type: [Number][68]

## QR_ERROR_LEVEL_CONSTANTS

Type: QR_ERROR_LEVEL

### L

Type: [Number][68]

### M

Type: [Number][68]

### Q

Type: [Number][68]

### H

Type: [Number][68]

[1]: #connect

[2]: #examples

[3]: #disconnect

[4]: #examples-1

[5]: #reset

[6]: #examples-2

[7]: #testprint

[8]: #examples-3

[9]: #getprinterspecs

[10]: #examples-4

[11]: #getprinterstatus

[12]: #examples-5

[13]: #cutpaper

[14]: #examples-6

[15]: #printtext

[16]: #parameters

[17]: #examples-7

[18]: #printtextcustom

[19]: #parameters-1

[20]: #examples-8

[21]: #printemptylines

[22]: #parameters-2

[23]: #examples-9

[24]: #setalign

[25]: #parameters-3

[26]: #examples-10

[27]: #setfontsize

[28]: #parameters-4

[29]: #examples-11

[30]: #printbarcode

[31]: #parameters-5

[32]: #examples-12

[33]: #printqrcode

[34]: #parameters-6

[35]: #examples-13

[36]: #printtable

[37]: #parameters-7

[38]: #examples-14

[39]: #printbase64image

[40]: #parameters-8

[41]: #examples-15

[42]: #align_constants

[43]: #left

[44]: #center

[45]: #right

[46]: #barcode_type_constants

[47]: #upca

[48]: #upce

[49]: #jan13

[50]: #jan8

[51]: #code39

[52]: #itf

[53]: #codabar

[54]: #code93

[55]: #code128

[56]: #barcode_text_position_constants

[57]: #no_print

[58]: #above

[59]: #below

[60]: #both

[61]: #qr_error_level_constants

[62]: #l

[63]: #m

[64]: #q

[65]: #h

[66]: https://developer.mozilla.org/docs/Web/JavaScript/Reference/Global_Objects/Promise

[67]: https://developer.mozilla.org/docs/Web/JavaScript/Reference/Global_Objects/String

[68]: https://developer.mozilla.org/docs/Web/JavaScript/Reference/Global_Objects/Number

[69]: https://developer.mozilla.org/docs/Web/JavaScript/Reference/Global_Objects/Boolean

[70]: https://developer.mozilla.org/docs/Web/JavaScript/Reference/Global_Objects/Array

## License

MIT
