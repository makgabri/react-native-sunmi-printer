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

