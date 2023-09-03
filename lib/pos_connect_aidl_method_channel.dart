import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'pos_connect_aidl_platform_interface.dart';

/// An implementation of [PosConnectAidlPlatform] that uses method channels.
class MethodChannelPosConnectAidl extends PosConnectAidlPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('pos_connect_aidl');

  @override
  Future<bool?> printImage({required Uint8List pathImage}) async {
    return await methodChannel.invokeMethod<bool>('printImage', {
      'pathImage': pathImage,
    });
  }
}
