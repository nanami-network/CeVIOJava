# CeVIOJava

## 仕組み
[CeVIO API](https://cevio.jp/guide/cevio_ai/interface/dotnet/) を使用してこのプロジェクトにある [CeVIOJava.dll]() から [Java Native Access](https://github.com/java-native-access/jna/) を使って呼び出します

### CeVIOJava.dllを経由する理由
[DllExport](https://github.com/3F/DllExport) を使用して [Java Native Access](https://github.com/java-native-access/jna/) から実行することが可能になりますが、再頒布することは禁止なので経由しています

利点: **CeVIOJava.dll** から実行することでCeVIO Creative Studioをインストールするだけで実行可能になります