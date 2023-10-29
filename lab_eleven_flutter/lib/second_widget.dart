import 'package:flutter/material.dart';

class MySecondWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyState();
  }
}

class MyState extends State<MySecondWidget> {
  String url = "https://source.unsplash.com/random/800x600";

  void changeURL() {
    setState(() {
      url = "https://source.unsplash.com/random/800x600/?"
          "q=${new DateTime.now().millisecondsSinceEpoch}";
    });
  }

  // A random image from Unsplash
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              ElevatedButton(
                child: Text("Press Me"),
                onPressed: changeURL,
              ),
              Image.network(url),
            ],
          ),
        ),
      ),
    );
  }
}
