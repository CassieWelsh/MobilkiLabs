import 'package:flutter/material.dart';

class MyApp extends StatelessWidget {
  final List<ListItem> items;

  MyApp({Key? key, required this.items}) : super(key: key);

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
          itemCount: items.length,
          // Provide a builder function. This is where the magic happens. // Convert each item into a widget based on the type of item it is.
          itemBuilder: (context, index) {
            final item = items[index];
            if (item is HeadingItem) {
              return ListTile(
                title: Text(
                  item.heading,
                  style: Theme.of(context).textTheme.headlineSmall,
                ),
              );
            } else if (item is MessageItem) {
              return ListTile(
                title: Text(item.sender),
                subtitle: Text(item.body),
                leading: ConstrainedBox(
                  constraints: const BoxConstraints(
                    minWidth: 44,
                    minHeight: 128,
                    maxWidth: 64,
                    maxHeight: 256,
                  ),
                  child: Image(
                    image: NetworkImage(item.cover),
                    fit: BoxFit.cover
                  )
                ),
              );
            }
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
