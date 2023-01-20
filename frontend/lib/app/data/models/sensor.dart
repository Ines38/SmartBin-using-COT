class Sensor {
  final dynamic value;
  final String? id;
  final String? siteId;

  Sensor({required this.value, required this.id, required this.siteId});

  factory Sensor.fromJson(Map<String, dynamic> json) {
    return Sensor(
        value: json["value"] ?? 0,
        id: json["sensorId"] ?? "",
        siteId: json["siteId"] ?? "");
  }

  @override
  String toString() {
    return "the sensor $id is in $siteId and have the value $value";
  }
}
