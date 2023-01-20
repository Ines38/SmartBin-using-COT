import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:frontend/app/data/models/login.dart';
import 'package:frontend/app/data/models/profile.dart';
import 'package:frontend/app/data/models/site.dart';
import 'package:frontend/app/data/models/sensor.dart';
import 'package:frontend/app/data/models/signup.dart';
import 'package:frontend/app/oauth/oauth_lib.dart';
import 'package:get_it/get_it.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class APIServices {
  Future<Login> login({required email, required password}) async {
    try {
      await GetIt.I<OAuthSettings>()
          .oauth
          .requestTokenAndSave(PasswordGrant(email: email, password: password));
      return Login(loggedIn: true, message: "loggedIn");
    } on DioError catch (e) {
      return Login.fromJson(e.response!.data);
    }
  }

  Future<SignUp> register(
      {required forename,
      required surname,
      required email,
      required password,
      required username,
      permission = 1}) async {
    FlutterSecureStorage storage = const FlutterSecureStorage();
    Dio dio = Dio();
    final request = RequestOptions(
        method: 'POST',
        path: '/',
        contentType: 'application/json',
        data: {
          "forename": forename,
          "surname": surname,
          "email": email,
          "password": password,
          "username": username,
          "permission": permission
        });
    try {
      var resp = await dio.request(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/user/signup",
          data: request.data,
          options: Options(
              contentType: request.contentType, method: request.method));
      await storage.write(key: "id", value: resp.data["id"]);
      return SignUp.fromJson(resp.data);
    } on DioError catch (e) {
      return SignUp.fromJson(e.response!.data);
    }
  }

  Future<Profile> profile() async {
    final request = RequestOptions(
      path: '/',
      contentType: 'application/json',
    );
    try {
      var resp = await GetIt.I<OAuthSettings>().authenticatedDio.get(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/user",
          options: Options(contentType: request.contentType));
      return Profile.fromJson(resp.data);
    } on DioError catch (e) {
      var data = {
        "ok": false,
      };
      return Profile.fromJson(data);
    }
  }

  Future<List<Profile>> listUsers({required page, required limit}) async {
    final request = RequestOptions(
      path: '/',
      contentType: 'application/json',
    );
    try {
      var resp = await GetIt.I<OAuthSettings>().authenticatedDio.get(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/user?page=$page&limit=$limit",
          options: Options(contentType: request.contentType));
      print(resp);

      return resp.data["message"].map<Profile>((e) {
        return Profile.fromJson({"ok": true, "message": e});
      }).toList();
    } on DioError catch (e) {
      var data = {
        "ok": false,
      };
      return [Profile.fromJson(data)];
    }
  }

  Future<Profile> getUserById({required id}) async {
    final request = RequestOptions(
      path: '/',
      contentType: 'application/json',
    );
    try {
      var resp = await GetIt.I<OAuthSettings>().authenticatedDio.get(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/user/$id",
          options: Options(contentType: request.contentType));

      return Profile.fromJson(resp.data);
    } on DioError catch (_) {
      var data = {
        "ok": false,
      };
      return Profile.fromJson(data);
    }
  }

  Future<void> updateProfile({required Map<String, dynamic> data}) async {
    final request = RequestOptions(
      path: '/',
      contentType: 'application/json',
    );
    try {
      FlutterSecureStorage storage = const FlutterSecureStorage();
      String? id = await storage.read(key: "id");
      await GetIt.I<OAuthSettings>().authenticatedDio.patch(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/user/$id",
          data: data,
          options: Options(contentType: request.contentType));
    } on DioError catch (e) {
      print(e.response);
    }
  }

  Future<void> updatePermessionById({required id, required permission}) async {
    final request = RequestOptions(
      path: '/',
      contentType: 'application/json',
    );
    try {
      await GetIt.I<OAuthSettings>().authenticatedDio.patch(
          "https://api.homeautomationcot.me/users/$id",
          data: {"permissions": permission},
          options: Options(contentType: request.contentType));
    } on DioError catch (e) {
      print(e.response);
    }
  }

  Future<void> deleteUserById({required id}) async {
    final request = RequestOptions(
      path: '/',
      contentType: 'application/json',
    );
    try {
      await GetIt.I<OAuthSettings>().authenticatedDio.delete(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/user/$id",
          options: Options(contentType: request.contentType));
    } on DioError catch (e) {
      print(e.response);
    }
  }

  Future<bool> isConnected() async {
    try {
      OAuthToken? token =
          await GetIt.I<OAuthSettings>().oauth.fetchOrRefreshAccessToken();
      return token != null ? true : false;
    } on DioError catch (e) {
      print(e.response);
      return false;
    } catch (e) {
      return false;
    }
  }

  Future<void> logout() async {
    FlutterSecureStorage storage = const FlutterSecureStorage();
    await storage.deleteAll();
  }

  Future<Sensor> addConnectedObject(
      {required String siteId,
      required String objectId,
      required int pin}) async {
    final request = RequestOptions(
      path: '/',
      data: {"siteId": siteId, "sensorId": objectId, "pin": pin},
      contentType: 'application/json',
    );
    try {
      var resp = await GetIt.I<OAuthSettings>().authenticatedDio.post(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/box",
          data: request.data,
          options: Options(contentType: request.contentType));
      return Sensor(value: null, id: objectId, siteId: siteId);
    } on DioError catch (e) {
      return Sensor(
          value: e.response!.data["ok"],
          id: e.response!.data.toString(),
          siteId: null);
    }
  }

  // Future<String> removeConnectedObject(
  //     {required String siteId, required String objectId}) async {
  //   final request = RequestOptions(
  //     path: '/',
  //     data: {"siteId": siteId, "sensorId": objectId},
  //     contentType: 'application/json',
  //   );
  //   try {
  //     var resp = await GetIt.I<OAuthSettings>().authenticatedDio.post(
  //         "http://localhost:8080/cot-1.0-SNAPSHOT/api/box/removeObject",
  //         data: request.data,
  //         options: Options(contentType: request.contentType));
  //     return resp.data["message"];
  //   } on DioError catch (e) {
  //     return e.response!.data["message"];
  //   }
  // }

  // Future<String> setStateOfConnectedObject({
  //   required String? siteId,
  //   required String? objectId,
  //   required bool state,
  // }) async {
  //   final request = RequestOptions(
  //     path: '/',
  //     data: {
  //       "roomId": siteId,
  //       "sensorId": objectId,
  //       "action": {'on': state}
  //     },
  //     contentType: 'application/json',
  //   );
  //   try {
  //     var resp = await GetIt.I<OAuthSettings>().authenticatedDio.post(
  //         "http://localhost:8080/cot-1.0-SNAPSHOT/api/setState",
  //         data: request.data,
  //         options: Options(contentType: request.contentType));
  //     return resp.data["message"];
  //   } on DioError catch (e) {
  //     return e.response!.data["message"];
  //   }
  // }

  // Future<Sensor> getsetStateOfConnectedObject(
  //     {required String siteId, required String objectId}) async {
  //   final request = RequestOptions(
  //     path: '/',
  //     method: 'GET',
  //     contentType: 'application/json',
  //   );
  //   final Dio dio = Dio();
  //   try {
  //     var resp = await dio.get(
  //         "http://localhost:8080/cot-1.0-SNAPSHOT/api/getState?roomId=$siteId&sensorId=$objectId");

  //     return Sensor(value: resp.data["message"], id: objectId, siteId: siteId);
  //   } on DioError catch (e) {
  //     return Sensor(value: e.response!.data["message"], id: null, siteId: null);
  //   }
  // }

  Future<List<Site>> listSites({required page, required limit}) async {
    final request = RequestOptions(
      path: '/',
      method: 'GET',
      contentType: 'application/json',
    );
    final Dio dio = Dio();
    try {
      var resp = await dio.request(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/listSites?page=$page&limit=$limit",
          options: Options(contentType: request.contentType));
      Map<String, List> x = Map<String, List>.from(resp.data["message"]);
      List<Site> rooms = [];
      x.forEach((key, value) {
        List<Sensor> sensors = value.map<Sensor>((e) {
          return Sensor(
              id: e.keys.elementAt(0),
              value: e.values.elementAt(0),
              siteId: key);
        }).toList();
        rooms.add(Site(id: key, sensors: sensors));
      });
      return rooms;
      // List<Room> fina = [];

      // for (MapEntry e in x.entries) {
      //   List<String> l = List.from(e.value);
      //   List<Sensor> s = [];
      //   for (String element in l) {
      //     Sensor con = await getsetStateOfConnectedObject(
      //         objectId: element, roomId: e.key);
      //     s.add(con);
      //   }

      //   fina.add(Room(sensors: s, id: e.key));
      // }

    } on DioError catch (e) {
      return [Site(sensors: null, id: null)];
    }
  }

  // Future<List<Sensor>> listSensorsBySite({required siteId}) async {
  //   final request = RequestOptions(
  //     path: '/',
  //     method: 'GET',
  //     contentType: 'application/json',
  //   );
  //   final Dio dio = Dio();
  //   try {
  //     var resp = await dio.request(
  //         "http://localhost:8080/cot-1.0-SNAPSHOT/api/listSensors/$siteId",
  //         options: Options(contentType: request.contentType));
  //     List<Sensor> sensors = resp.data["message"]
  //         .map<Sensor>((e) =>
  //             Sensor(value: e["value"], id: e["sensorId"], siteId: siteId))
  //         .toList();
  //     return sensors;
  //   } on DioError catch (e) {
  //     return [Sensor(value: null, id: null, siteId: null)];
  //   }
  // }

  Future<List<double>> getLocation() async {
    final request = RequestOptions(
      path: '/',
      method: 'GET',
      contentType: 'application/json',
    );
    final Dio dio = Dio();
    try {
      var res = await dio.request(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/site",
          options: Options(contentType: request.contentType));
      List<double> l = [res.data["message"]["latitude"], res.data["message"]["longitude"]];
      return l;
    } on DioError catch (e) {
      return [0, 0];
    }
  }

  Future<bool> setLocation({required LatLng location}) async {
    final request = RequestOptions(
      path: '/',
      method: 'POST',
      contentType: 'application/json',
    );
    final Dio dio = Dio();
    try {
      await await GetIt.I<OAuthSettings>().authenticatedDio.request(
          "http://localhost:8080/cot-1.0-SNAPSHOT/api/site?latitude=${location.latitude}&longitude=${location.longitude}",
          options: Options(
              contentType: request.contentType, method: request.method));
      return true;
    } on DioError catch (e) {
      print(e.response!.data);
      return false;
    }
  }
}
