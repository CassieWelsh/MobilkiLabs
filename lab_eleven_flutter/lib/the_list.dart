import 'dart:math' as math;

import 'package:flutter/material.dart';

class MyApp extends StatefulWidget {
  final List<ListItem> items;

  const MyApp({required this.items});

  @override
  State<StatefulWidget> createState() {
    return _MyApper(items);
  }
}

class _MyApper extends State<MyApp> {
  late HeadingItem headingItem;
  late List<MessageItem> books;
  late List<Color> colors;

  _MyApper(List<ListItem> items) {
    books = [];
    for (var item in items) {
      if (item is HeadingItem) {
        headingItem = item;
      } else if (item is MessageItem) {
        books.add(item);
      }
    }
    colors = [];
    for (int i = 0; i < items.length; i++) {
      colors.add(Colors.white);
    }
  }

  @override
  Widget build(BuildContext context) {
    final title = 'Mixed List';
    return MaterialApp(
      title: title,
      home: Scaffold(
        appBar: AppBar(
          title: Text(title),
        ),
        body: ListView.builder(

          // Let the ListView know how many items it needs to build.
          itemCount: books.length,
          // Provide a builder function. This is where the magic happens. // Convert each item into a widget based on the type of item it is.
          itemBuilder: (context, index) {
              return ListTile(
                title: Text(books[index].sender),
                subtitle: Text(books[index].body),
                tileColor: colors[index],
                onTap: () {
                  setState(() {
                    colors[index] =
                        Color((math.Random().nextDouble() * 0xFFFFFF).toInt())
                            .withOpacity(1.0);
                  });
                },
                onLongPress: () {
                  setState(() {
                    books.removeAt(index);
                  });
                },
                leading: ConstrainedBox(
                    constraints: const BoxConstraints(
                      minWidth: 44,
                      minHeight: 128,
                      maxWidth: 64,
                      maxHeight: 256,
                    ),
                    child: Image(
                        image: NetworkImage(books[index].cover), fit: BoxFit.cover)),
              );
          },
        ),
      ),
    );
  }
}

// The base class for the different types of items the list can contain.
abstract class ListItem {}

// A ListItem that contains data to display a heading.
class HeadingItem implements ListItem {
  final String heading;

  HeadingItem(this.heading);
}

// A ListItem that contains data to display a message.
class MessageItem implements ListItem {
  final String sender;
  final String body;
  final String cover;

  MessageItem(this.sender, this.body, this.cover);
}
