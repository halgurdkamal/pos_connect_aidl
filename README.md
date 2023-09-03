
# POS CONNECT AIDL

this package with to connect q6pro pos with AIDL this package for now only support images print




## Getting Started

First import 

```dart
import 'package:pos_connect_aidl/pos_connect_aidl.dart';
```





## Usage/Examples

```dart
import 'package:pos_connect_aidl/pos_connect_aidl.dart';

final _posConnectAidlPlugin = PosConnectAidl();

final ByteData bytes = await rootBundle.load('assets/Receipt.jpg');
final Uint8List listBytes = bytes.buffer.asUint8List();

await _posConnectAidlPlugin.printImage(pathImage: listBytes)
```

