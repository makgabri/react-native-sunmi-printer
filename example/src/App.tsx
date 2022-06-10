/* eslint-disable react-native/no-inline-styles*/
import { useState } from 'react';
import * as React from 'react';
import { Modal } from 'react-native';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import { SPrinter, Constants } from 'react-native-sunmi-printer';
import { ScrollView } from 'react-native';
import { TextInput } from 'react-native';
import { Dimensions } from 'react-native';

export default function App() {
  const [modalVisible, setModalVisible] = useState(false);
  const [success, setSuccess] = useState(false);
  const [modalTitle, setModalTitle] = useState('');
  const [msg, setMsg] = useState('');
  const [printText, setPrintText] = useState('Example');
  const [printLines, setPrintLines] = useState(2);
  const [printAlign, setPrintAlign] = useState(Constants.Align.CENTER);
  const [printFontSize, setPrintFontSize] = useState(18);

  const showModal = (
    actionSuccess: boolean,
    title: string,
    actionMsg: string
  ) => {
    setModalTitle(title);
    setSuccess(actionSuccess);
    setMsg(actionMsg);
    setModalVisible(true);
  };

  const ActionList = [
    {
      title: 'Initialization: ',
      items: [
        {
          title: 'Connect',
          success: true,
          action: async () => {
            try {
              showModal(
                true,
                'Connect Printer',
                JSON.stringify(await SPrinter.connect())
              );
            } catch (e: any) {
              showModal(
                false,
                'Connect Printer',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Disconnect',
          success: true,
          action: async () => {
            try {
              showModal(
                true,
                'Disconnect Printer',
                JSON.stringify(await SPrinter.disconnect())
              );
            } catch (e: any) {
              showModal(
                false,
                'Disconnect Printer',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Reset Printer',
          success: true,
          action: async () => {
            try {
              showModal(
                true,
                'Reset Printer',
                JSON.stringify(await SPrinter.reset())
              );
            } catch (e: any) {
              showModal(
                false,
                'Reset Printer',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
      ],
    },
    {
      title: 'Status Related: ',
      items: [
        {
          title: 'Test Print',
          success: true,
          action: async () => {
            try {
              showModal(
                true,
                'Test Print',
                JSON.stringify(await SPrinter.testPrint())
              );
            } catch (e: any) {
              showModal(
                false,
                'Test Print',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Get Printer Specs',
          success: true,
          action: async () => {
            try {
              showModal(
                true,
                'Get Printer Specs',
                JSON.stringify(await SPrinter.getPrinterSpecs())
              );
            } catch (e: any) {
              showModal(
                false,
                'Get Printer Specs',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Show Printer Status',
          success: true,
          action: async () => {
            try {
              showModal(
                true,
                'Show Printer Status',
                JSON.stringify(await SPrinter.getPrinterStatus())
              );
            } catch (e: any) {
              showModal(
                false,
                'Show Printer Status',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
      ],
    },
    {
      title: 'Text Related: ',
      style: { flexDirection: 'column' },
      items: [
        {
          title: 'Print Text',
          success: true,
          input: (
            <View>
              <TextInput
                style={{
                  height: 40,
                  margin: 12,
                  borderWidth: 1,
                  padding: 10,
                  width: Dimensions.get('screen').width * 0.4,
                }}
                value={printText}
                onChangeText={setPrintText}
              />
            </View>
          ),
          action: async () => {
            try {
              await SPrinter.printText(printText);
              await SPrinter.printEmptyLines(2);
              await SPrinter.cutPaper();
              showModal(true, 'Print Text', 'Great Success');
            } catch (e: any) {
              showModal(
                false,
                'Print Text',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Print Empty Lines',
          success: true,
          input: (
            <View
              style={{
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
                height: 60,
                width: Dimensions.get('screen').width * 0.2,
              }}
            >
              <TouchableOpacity
                onPress={() => {
                  if (printLines > 1) setPrintLines(printLines - 1);
                }}
                style={styles.numInput}
              >
                <Text style={{ fontSize: 52 }}>-</Text>
              </TouchableOpacity>
              <View style={styles.numInput}>
                <Text style={{ fontSize: 36 }}>{printLines}</Text>
              </View>
              <TouchableOpacity
                onPress={() => {
                  setPrintLines(printLines + 1);
                }}
                style={styles.numInput}
              >
                <Text style={{ fontSize: 40 }}>+</Text>
              </TouchableOpacity>
            </View>
          ),
          action: async () => {
            try {
              showModal(
                true,
                'Print lines',
                JSON.stringify(await SPrinter.printEmptyLines(printLines))
              );
            } catch (e: any) {
              showModal(
                false,
                'Print lines',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Cut Paper',
          success: true,
          action: async () => {
            try {
              showModal(
                true,
                'Cut Paper',
                JSON.stringify(await SPrinter.cutPaper())
              );
            } catch (e: any) {
              showModal(
                false,
                'Cut Paper',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Set Align',
          success: true,
          input: (
            <View
              style={{
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
                height: 60,
                width: Dimensions.get('screen').width * 0.2,
              }}
            >
              <TouchableOpacity
                onPress={() => {
                  setPrintAlign(Constants.Align.LEFT);
                }}
                style={[
                  styles.numInput,
                  { width: 120 },
                  printAlign === Constants.Align.LEFT
                    ? {
                        borderColor: 'green',
                        backgroundColor: 'rgba(0, 255, 0, 0.3)',
                      }
                    : {},
                ]}
              >
                <Text style={{ fontSize: 22 }}>Left</Text>
              </TouchableOpacity>
              <TouchableOpacity
                onPress={() => {
                  setPrintAlign(Constants.Align.CENTER);
                }}
                style={[
                  styles.numInput,
                  { width: 120 },
                  printAlign === Constants.Align.CENTER
                    ? {
                        borderColor: 'green',
                        backgroundColor: 'rgba(0, 255, 0, 0.3)',
                      }
                    : {},
                ]}
              >
                <Text style={{ fontSize: 22 }}>Center</Text>
              </TouchableOpacity>
              <TouchableOpacity
                onPress={() => {
                  setPrintAlign(Constants.Align.RIGHT);
                }}
                style={[
                  styles.numInput,
                  { width: 120 },
                  printAlign === Constants.Align.RIGHT
                    ? {
                        borderColor: 'green',
                        backgroundColor: 'rgba(0, 255, 0, 0.3)',
                      }
                    : {},
                ]}
              >
                <Text style={{ fontSize: 22 }}>Right</Text>
              </TouchableOpacity>
            </View>
          ),
          action: async () => {
            try {
              showModal(
                true,
                'Set Align',
                JSON.stringify(await SPrinter.setAlign(printAlign))
              );
            } catch (e: any) {
              showModal(
                false,
                'Set Align',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
        {
          title: 'Set Font Size',
          success: true,
          input: (
            <View
              style={{
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
                height: 60,
                width: Dimensions.get('screen').width * 0.2,
              }}
            >
              <TouchableOpacity
                onPress={() => {
                  if (printFontSize > 1) setPrintFontSize(printFontSize - 1);
                }}
                style={styles.numInput}
              >
                <Text style={{ fontSize: 52 }}>-</Text>
              </TouchableOpacity>
              <View style={styles.numInput}>
                <Text style={{ fontSize: 36 }}>{printFontSize}</Text>
              </View>
              <TouchableOpacity
                onPress={() => {
                  setPrintFontSize(printFontSize + 1);
                }}
                style={styles.numInput}
              >
                <Text style={{ fontSize: 40 }}>+</Text>
              </TouchableOpacity>
            </View>
          ),
          action: async () => {
            try {
              showModal(
                true,
                'Set Font Size',
                JSON.stringify(await SPrinter.setFontSize(printFontSize))
              );
            } catch (e: any) {
              showModal(
                false,
                'Set Font Size',
                `Code: ${e.code}\n Message: ${e.message}`
              );
            }
          },
        },
      ],
    },
    {
      title: 'Other Printing: ',
      items: [
        {
          title: 'Print Bar Code',
          success: false,
        },
        {
          title: 'Print QR Code',
          success: false,
        },
        {
          title: 'Print Table',
          success: false,
        },
        {
          title: 'Print base64',
          success: true,
          action: async () => {
            try {
              await SPrinter.printBase64Image(
                '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'
              );
              await SPrinter.printEmptyLines(3);
              await SPrinter.cutPaper();
            } catch (e) {
              console.warn(e);
            }
          },
        },
      ],
    },
  ];

  return (
    <View style={styles.root}>
      <ScrollView style={{ flex: 1 }}>
        <View style={{ height: 50 }} />
        {ActionList.map((group: any, idx) => {
          return (
            <View
              key={`group_${idx}`}
              style={{
                flex: 1,
                flexDirection: 'column',
                justifyContent: 'flex-start',
                alignItems: 'center',
              }}
            >
              <Text style={styles.title}>{group.title}</Text>
              <View style={[styles.container, group.style ? group.style : {}]}>
                {group.items.map((item: any, idx2: Number) => {
                  return (
                    <View
                      style={{ alignItems: 'center', justifyContent: 'center' }}
                      key={`item_${idx}_${idx2}`}
                    >
                      {item.input}
                      <TouchableOpacity
                        onPress={
                          item.action
                            ? item.action
                            : () => {
                                console.log('TODO');
                              }
                        }
                        style={[
                          styles.button,
                          group.style ? { marginVertical: 20 } : {},
                        ]}
                      >
                        <Text style={{ color: item.success ? 'green' : 'red' }}>
                          {item.title}
                        </Text>
                      </TouchableOpacity>
                    </View>
                  );
                })}
              </View>
            </View>
          );
        })}
        <View style={{ height: 50 }} />
      </ScrollView>

      <Modal
        animationType="fade"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(false);
          setModalTitle('');
          setMsg('');
        }}
      >
        <View style={styles.container}>
          <View style={styles.modalView}>
            <Text
              style={[styles.modalTitle, { color: success ? 'green' : 'red' }]}
            >
              {modalTitle}
            </Text>
            <Text style={{ marginBottom: 50 }}>{msg}</Text>
            <TouchableOpacity
              onPress={() => {
                setModalVisible(false);
                setModalTitle('');
                setMsg('');
              }}
              style={[
                styles.button,
                { borderColor: 'blue', borderWidth: 5, marginBottom: 15 },
              ]}
            >
              <Text>Close</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  modalTitle: {
    fontSize: 48,
    fontWeight: 'bold',
    paddingVertical: 15,
  },
  container: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginVertical: 20,
  },
  bottomLine: {
    borderBottomColor: '#9f9f9f',
    borderBottomWidth: 3,
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
  },
  numInput: {
    height: 60,
    width: 60,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 3,
    borderColor: '#9f9f9f',
  },
  button: {
    width: 200,
    height: 60,
    marginHorizontal: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: 'black',
    justifyContent: 'center',
    alignItems: 'center',
  },
});
