import 'package:frontend/app/modules/auth/auth.dart';
import 'package:frontend/app/modules/list_users/bindings/list_users_binding.dart';
import 'package:frontend/app/modules/list_users/views/list_users_view.dart';
import 'package:get/get.dart';

import 'package:frontend/app/modules/city/bindings/city_binding.dart';
import 'package:frontend/app/modules/city/views/city_view.dart';
import 'package:frontend/app/modules/splash_screen/bindings/splash_screen_binding.dart';
import 'package:frontend/app/modules/splash_screen/views/splash_screen_view.dart';

part 'app_routes.dart';

class AppPages {
  static const INITIAL = Routes.SPLASH_SCREEN;

  static final routes = [
    GetPage(
      name: _Paths.CITY,
      page: () => CityView(),
      binding: CityBinding(),
    ),
    GetPage(
      name: _Paths.SPLASH_SCREEN,
      page: () => SplashScreenView(),
      binding: SplashScreenBinding(),
    ),
    GetPage(
      name: _Paths.CONNECTED_DEVICE,
      page: () => ListUsersView(),
      binding: ListUsersBinding(),
    ),
    GetPage(
      name: _Paths.SIGNIN,
      page: () => AuthScreen(),
    ),
    // GetPage(
    //   name: _Paths.SIGNUP,
    //   page: () => SignUpScreen(),
    // ),
  ];
}
