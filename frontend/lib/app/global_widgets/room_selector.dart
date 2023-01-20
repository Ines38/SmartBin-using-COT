import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:frontend/app/theme/text_theme.dart';

class SiteSelector extends StatelessWidget {
  final String? siteName;
  final String? siteImageURL;
  final bool? isSelected;

  const SiteSelector({
    Key? key,
    required this.siteName,
    required this.siteImageURL,
    required this.isSelected,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: Get.height * 0.11,
      height: Get.height * 0.2,
      child: Column(
        children: [
          Container(
            height: Get.height * 0.083,
            width: Get.height * 0.083,
            padding: EdgeInsets.all(14),
            decoration: BoxDecoration(
              color: isSelected!
                  ? Theme.of(context).primaryColor
                  : Theme.of(context).scaffoldBackgroundColor,
              border: Border.all(
                color: Theme.of(context).primaryColor,
                width: 1,
              ),
              borderRadius: BorderRadius.circular(15),
            ),
            child: SvgPicture.asset(siteImageURL!,
                color: isSelected!
                    ? Theme.of(context).scaffoldBackgroundColor
                    : Theme.of(context).primaryColor,
                placeholderBuilder: (context) => SvgPicture.asset(
                      'assets/icons/room.svg',
                      color: isSelected!
                          ? Theme.of(context).scaffoldBackgroundColor
                          : Theme.of(context).primaryColor,
                    )),
          ),
          SizedBox(height: Get.height * 0.01),
          Text(
            siteName!,
            style: HomeFiTextTheme.kSub2HeadTextStyle.copyWith(
                color: Theme.of(context).primaryColorDark, fontSize: 10),
          ),
        ],
      ),
    );
  }
}
