![TimoMenu](https://github.com/samlss/TimoMenu/blob/master/screenshots/menu.png)

 [![Download](https://api.bintray.com/packages/samlss/maven/timomenu/images/download.svg)](https://bintray.com/samlss/maven/timomenu/_latestVersion)   [![Api reqeust](https://img.shields.io/badge/API-14+-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14#l14)    [![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/samlss/TimoMenu/blob/master/LICENSE)  [![Apk](https://img.shields.io/badge/apk-download-orange.svg)](https://github.com/samlss/TimoMenu/blob/master/apks/TimoMenu-V1.0.0.apk?raw=true)  


[中文](https://github.com/samlss/TimoMenu/wiki/%E4%B8%BB%E9%A1%B5)

### Features

- Multiple row items
- The menu appearance and disappear animation
- Header & Footer
- Custom item open animation & click effect


### Screenshots

#### Bottom
![Lighter](https://github.com/samlss/TimoMenu/blob/master/screenshots/screenshot1.gif)

<br>

#### Center
![Lighter](https://github.com/samlss/TimoMenu/blob/master/screenshots/screenshot2.gif)

<br>

#### Top
![Lighter](https://github.com/samlss/TimoMenu/blob/master/screenshots/screenshot3.gif)

<br>

#### Header & Footer
![Lighter](https://github.com/samlss/TimoMenu/blob/master/screenshots/screenshot5.gif)

<br>

#### Support 8 kinds of opening animations
![Lighter](https://github.com/samlss/TimoMenu/blob/master/screenshots/screenshot4.gif)

------
### Dependency

#### Gradle
Add it in your module build.gradle at the end of repositories:
  ```java
  dependencies {
      implementation 'me.samlss:timomenu:1.0.0'
  }
  ```

#### Maven
```java
<dependency>
  <groupId>me.samlss</groupId>
  <artifactId>timomenu</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

### Sample Usage

```java
//create menu
TimoMenu timoMenu = new TimoMenu.Builder(this)
                .setGravity(Gravity.BOTTOM)
                .setMenuMargin(new Rect(20, 20, 20, 20))
                .setMenuPadding(new Rect(0, 10, 0, 10))
                .addRow('ItemAnimation', ' List<TimoItemViewParameter>')
                .build();

//Show menu
timoMenu.show(); 

//Dismiss menu.
timoMenu.dismiss();

```



Please read [wiki](https://github.com/samlss/TimoMenu/wiki) for more descriptions.

### License

```
Copyright 2018 samlss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
