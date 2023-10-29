import 'package:flutter/material.dart';
import 'package:lab_eleven_flutter/second_widget.dart';

import 'the_list.dart';

class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomeState();
  }
}

class Book {
  final String name;
  final String description;

  Book(this.name, this.description);
}

class _HomeState extends State<Home> {
  int _currentIndex = 0;
  final List<Widget> _children = [
    MySecondWidget(),
    MyApp(
      items: [
        HeadingItem("Список книг"),
        MessageItem(
            'Тени сгущаются',
            'Наемникам наплевать — на чьей стороне сражаться. Согласно меткой пословице, за золото они готовы идти хоть на всадников Апокалипсиса. Однако для лихих парней из Черного Отряда эта пословица неожиданно становится реальностью… Их нанимают не короли, не графы — великие маги и чернокнижники. Они сражаются то на стороне Света, то на стороне Тьмы, и противники их — не только люди, но демоны, монстры ...',
            'https://readly.ru/public/media/covers/d/b/db4cd066e205f9deff7d33c28ffa2416_160x0.jpg'),
        MessageItem(
            'Дом на берегу',
            '«Дом на берегу» — возможно, лучший роман Дафны дю Морье, автора захватывающих романтических триллеров, каждый из которых гарантирует читателю бессонную ночь. Здесь сталкиваются две реальности, разделенные пропастью в шестьсот лет. С одной стороны — средневековый Корнуолл, прекрасная дама и ее возлюбленный. С другой — наш современник, тоже безнадежно влюбленный и потому способный на безрассудные...',
            'https://readly.ru/public/media/covers/6/4/6462bf111149858fa84ce9eeec0bfc86_160x0.jpg'),
        MessageItem(
            'Серебряный ключ',
            'Рэндольф Картер, предпочитающий уходить от реальности в царство своих грез и фантазий, получает во сне подсказку своего деда и обретает серебряный ключ – ключ ко времени и пространству. Только тот, кто хочет вернуться в страну своих снов, сможет им воспользоваться.',
            'https://readly.ru/public/media/covers/a/a/aaaae5fab5b75c5567d0b537cc4c215a_160x0.jpg'),
      ],
    )
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('My Flutter App'),
      ),
      body: _children[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        onTap: onTabTapped,
        currentIndex: _currentIndex,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Example 1'),
          BottomNavigationBarItem(icon: Icon(Icons.mail), label: 'Example 2'),
        ],
      ),
    );
  }

  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }
}
