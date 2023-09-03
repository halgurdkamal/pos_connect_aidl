import 'package:flutter/foundation.dart';

import 'pos_connect_aidl_platform_interface.dart';

class PosConnectAidl {
  Future<bool?> printImage({required Uint8List pathImage}) {
    return PosConnectAidlPlatform.instance.printImage(pathImage: pathImage);
  }
}
