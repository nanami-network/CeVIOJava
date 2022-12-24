# CeVIOJava

## 仕組み
[CeVIO API](https://cevio.jp/guide/cevio_ai/interface/dotnet/) を使用してこのプロジェクトにある [CeVIOJava.dll](src/main/resources/CeVIOJava.dll) から [Java Native Access](https://github.com/java-native-access/jna/) を使って呼び出します

### CeVIOJava.dllを経由する理由
[DllExport](https://github.com/3F/DllExport) を使用して [Java Native Access](https://github.com/java-native-access/jna/) から実行することが可能になりますが、再頒布することは禁止なので経由しています

利点: **CeVIOJava.dll** から実行することでCeVIO Creative Studioをインストールするだけで実行可能になります

## サンプルコード

#### "こんにちは！ [キャスト名] です！" と喋らせるコードとランダムに感情を設定するコード
```
    CeVIOJava cevio = new CeVIOBuilder()
            .setAutoStart(true)
            .create();

    List<String> availableCasts = cevio.getAvailableCastsList();
    if (availableCasts.isEmpty()) {
        System.err.println("Non AvailableCasts");
    } else {
        CastSettings settings = cevio.getCastSettings(availableCasts.get(0));
        for (TalkerComponent component : settings.getComponents().getAsCollection()) {
            final int random = new Random().nextInt(100);

            System.out.printf("name=%s id=%s value=%s newValue=%s%n", component.getName(), component.getId(), component.getValue(), random);
            component.setValue(random);
        }
        for (TalkerComponent component : settings.getComponents().getAsCollection()) {
            System.out.printf("[Log] name=%s id=%s value=%s%n", component.getName(), component.getId(), component.getValue());
        }
        settings.speak("こんにちは！" + settings.getCast() + "です！", true);
    }
    cevio.stop();
```
#### 出力:
```
name=普通 id=A-02 value=0 newValue=70
name=怒り id=A-03 value=0 newValue=31
name=哀しみ id=A-04 value=0 newValue=94
name=元気 id=A-01 value=100 newValue=38
[Log] name=普通 id=A-02 value=70
[Log] name=怒り id=A-03 value=31
[Log] name=哀しみ id=A-04 value=94
[Log] name=元気 id=A-01 value=38
```