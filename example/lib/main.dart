import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pos_connect_aidl/pos_connect_aidl.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _posConnectAidlPlugin = PosConnectAidl();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    // try {
    //   final ByteData bytes = await rootBundle.load('assets/Receipt.jpg');
    //   final Uint8List listBytes = bytes.buffer.asUint8List();
    //   platformVersion =
    //       (await _posConnectAidlPlugin.printImage(pathImage: listBytes))
    //           .toString();
    // } on PlatformException {
    //   platformVersion = 'Failed to get platform version.';
    // }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      // _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text('Running on: $_platformVersion\n'),
              ElevatedButton(
                  onPressed: () async {
                    try {
                      final ByteData bytes =
                          await rootBundle.load('assets/Receipt.jpg');
                      final Uint8List listBytes = bytes.buffer.asUint8List();
                      (await _posConnectAidlPlugin.printImage(
                              pathImage: listBytes))
                          .toString();
                    } on PlatformException {}
                  },
                  child: Text('Print Image'))
            ],
          ),
        ),
      ),
    );
  }
}
