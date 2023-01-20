

import 'package:frontend/app/data/models/sensor.dart';

class Site {
  final List<Sensor>? sensors;
  final String? id;

  Site({required this.sensors, required this.id});

  factory Site.fromJson(Map<String, dynamic> json) {
    return Site(id: json["_id"]["siteId"] ?? "", sensors: json["sensors"]);
  }
}
