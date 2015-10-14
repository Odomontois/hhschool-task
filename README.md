# Задание для школы программистов HeadHunter

## Минимальное расстояние

Реализация алгоритма "разделяй и властвуй" находит минимальное расстояние в худшем случае за время `O(n log(n))` для `n` точек

Построена на базе [этой статьи][1], 
с улучшением взятым из [этого описания алгоритма][2].

Алгоритм реализован в классe [`ru.hh.school.midist.MinDistSearcher`][MinDistSearcher] в методе `find`.
Сначала определяется какое количество точек рассматривается, точки сорируются по координате `x` и вызывается соответствующая реализация метода `search`.

* Для случая из одной точки находится трививальное решение (мин. расстояние - бесконечность, вторая точка неизвестна)
* Для случая большего количества список делится пополам. Т.к. точки отсортированы по `x` точки с меньшей координатой находятся в левом подсписке.
  Для каждого из подсписков находится оптимальное решение, затем решения объединяются.

### Объединение решений

Сравниваются минимальные расстояния в каждом из подсписков. Из них берётся меньшее `d_min`и для него запускается шаг сравнения на границе.
Для этого мы берём максимальную координату по `x_split` из левого подсписка, 
отбираем из него точки, находящиеся не левее `x_split - d_min`, 
а из правого - не правее `x_split + d_min`, в полученном списке координаты сортируем по `y` и обходим, 
для каждой точки c `y_current` из левого списка рассматривая только точки в диапазоне 
`[y_current - d_min, y_current + d_min]`, как уточняется в [статье][1], таких точек найдётся не более шести
 
Т.к. для корректного прохода нам необходимо отсортировать точки по `y`, как указано в [описании алгоритма][2] 
нам необходимо вместе с минимальным расстоянием для каждого промежуточного результата также определять 
список всех точек в решении, заранее отсортированных по `y`, тогда при объединении решений мы можем использовать
алгоритм, аналогичный [сортировке слиянием][3], реализованный с помощью класса [`ru.hh.school.utils.SortedList`][SortedList].
В этом случае каждое слияние будет проходить гарантировано за `O(m)`, где `m` - размер рассматриваемого подсписка 
и общая сложность алгоритма не привысит `O(n∙log(n))`

***прим.*** *Т.к. в Java для сортировки используется [timsort][4], который учитывает частичную-сортированность коллекции,
 обычная сортировка объединённых списков также заняла бы `O(m)`, 
 но т.к. это не гарантировано в API, мы имплементируем слияние вручную* 
 
### Запуск исполняемой программы

Главный исполняемый класс: [`ru.hh.school.runnable.MinimalDistance`][MinimalDistance]

Входной файл может иметь любое число строк по два или три элемента (см.ниже). 
Последние два элемента должны быть целыми числами или  дробными числами с десятичной точкой.
В случае трёх элементов первый элемент может содержать буквы, цифры и знак подчёркивания.

Первым аргументом идёт имя входного файла, дополнительные аргументы:

* `-l`, `--label` - каждая строчка содержит три, а не два элемента, первым элементом идёт метка
* `-v`, `--verbose` - вывести не только минимальное расстояние, но и информацию о ближайших точках

## Дроби

Для поиска переодической записи дроби реализован класс [`ru.hh.school.fraction.Fraction`][Fraction]. 
В методе `toDigitalString`  с помощью класса [`ru.hh.school.utils.Unfold`][Unfold] 
генерируется бесконечная последовательность остатков от деления, получающихся, если делить
числитель на знаменатель "в столбик" с заданным основанием системы счисления.

Среди этой последовательности ищется цикл с помощью класса [`ru.hh.school.utils.CyclicSequence`][CyclicSequence], 
реализующий два метода поиска:

####`CONSTANT_MEMORY` 
Поиск за O(1) памяти. Мы генерируем используем два итератора 

* медленный - перебирающий элемент за элементом `a[i]`
* быстрый - перепрыгивающий нечётные элементы `a[2i]`
      
Когда два значения, полученные в этих двух подпоследовательностях сойдутся, 
т.е. мы найдём такое `k`, что `a[k] == a[2k]`, это `k` будет является `НОК(p,c)`
где `p`- длина минимального ациклического префикса последовательности, `c` - период циклического суффикса последовательности, 
`НОК(a,b)` - наименьшее общее кратное чисел `a` и `b`

Зная это мы сдвигаем последовательности, на `k` элементов вперёд, находим такой минимальный `p`, что `a[p+1] == a[p+1+k]`,
После чего находим цикл, ища такой `с`, что `a[p+1]==a[p+1+c]`

Если каждый итератор использует в каждый момент `O(1)` памяти, данный алгоритм,
 используя заранее известное число итераторов, также будет использовать `O(1)` памяти
 
#### `MEMOIZE`
Этот использует один итератор и запоминает все пройденные элементы, сохраняя порядок, 
используя свойства коллекции [`java.util.LinkedHashSet`][LinkedHashSet], 
когда очередной элемент найдётся среди пройденных, 
алгоритм пройдёт запомненные элементы и определит `p` и `c` из предыдущего пункта.

Таким образом, для работы этому методу необходимо минимальное число итераций для поиска периода.

### Запись числа 
Найдя необходимые `p` и `c`  и принимая `i` - целая часть числа,
мы можем построить строковое представление числа в одном из видов:

1. `i,p(c)` 
2. `i,p`, если `|c| = 0`
3. `i`, если `|c| = 0`

Для всех оснований системы счисления от 2 до 36 будут использоваться цифры из ряда

***0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ***

Для оснований, больших 36, для каждой цифры будет использоваться запись`[d]`, 
где `d` - запись цифры в десятичной форме.

### Полиморфизм

Решение может работать для любых чисел, для которых определён экземпляр 
класса [`ru.hh.school.typeclasses.Whole`][Whole] определяющий операции над целыми числами.
В данный момент реализованы имплементации для `java.lang.Long` и `java.math.BigInteger`

### Запуск исполняемой программы

Главный исполняемый файл [`ru.hh.school.runnable.Fractions`][Fractions]

Входной файл может иметь любое число строк. Каждая должна содержать ровно три элемента в таком порядке

1. числитель
2. знаменатель
3. основание СИ

Записанных в десятичной системе счисления.
Попытка использовать основание < 2 приведёт к ошибке во время исполнения

Первым аргументом должно быть имя входного файла, дополнительные аргументы:

* `-b`, `--bigint` - использовать реализацию для `java.math.BigInteger` (по умолчанию)
* `-l`, `--long` - использовать реализцию для `java.lang.Long`
* `-cm`, `--O1memory` - использовать алгоритм `CONSTANT_MEMORY` c *быстрым* и *медленным* обходом (по умолчанию)
* `-s`, `--memoize` - использовать алгоритм `MEMOIZE` c сохранением в `java.util.LinkedHashSet`



[1]:https://en.wikipedia.org/wiki/Closest_pair_of_points_problem
[2]:http://e-maxx.ru/algo/nearest_points
[3]:https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D1%80%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0_%D1%81%D0%BB%D0%B8%D1%8F%D0%BD%D0%B8%D0%B5%D0%BC
[4]:https://en.wikipedia.org/wiki/Timsort
[MinDistSearcher]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/mindist/MinDistSearcher.java
[Sortedlist]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/utils/SortedList.java
[MinimalDistance]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/runnable/MinimalDistance.java
[Fraction]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/fraction/Fraction.java
[Unfold]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/utils/Unfold.java
[CyclicSequence]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/utils/CyclicSequence.java
[LinkedHashSet]:http://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html
[Whole]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/typeclasses/Whole.java
[Fractions]:https://github.com/Odomontois/hhschool-task/blob/master/src/main/java/ru/hh/school/runnable/Fractions.java