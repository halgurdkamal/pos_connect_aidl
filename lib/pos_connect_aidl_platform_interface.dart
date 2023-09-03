import 'package:flutter/foundation.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'pos_connect_aidl_method_channel.dart';

abstract class PosConnectAidlPlatform extends PlatformInterface {
  /// Constructs a PosConnectAidlPlatform.
  PosConnectAidlPlatform() : super(token: _token);

  static final Object _token = Object();

  static PosConnectAidlPlatform _instance = MethodChannelPosConnectAidl();

  /// The default instance of [PosConnectAidlPlatform] to use.
  ///
  /// Defaults to [MethodChannelPosConnectAidl].
  static PosConnectAidlPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [PosConnectAidlPlatform] when
  /// they register themselves.
  static set instance(PosConnectAidlPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

   Future<bool?> printImage({required Uint8List pathImage}) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
