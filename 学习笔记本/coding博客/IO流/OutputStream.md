# 数据写入输出流

## 直接写入

```Java
OutputStream os = socket.getOutputStream();  
os.write("人生苦短".getBytes());
```

## 间接写入

### OutputStreamWriter

```Java
OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());  
outputStreamWriter.write("It's Demo4");
```

### BufferedWriter

```Java
BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));  
bufferedWriter.write("It's Demo4");  
//使用BufferedWriter写入输出流必须在后面加flush方法  
bufferedWriter.flush();
```

^159f7e

### PrintWriter

```Java
//第二个参数为true，否则不会写入至输出流  
PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);  
printWriter.println("hello");
```

## 各种写入方法的区别

### `BufferedWriter` 和 `PrintWriter`

`BufferedWriter` 和 `PrintWriter` 都是用于向输出流写入数据的类，二者的区别如下：

1.  输出类型不同

`BufferedWriter` 只能输出字符流，而`PrintWriter` 可以输出字符流和字节流。

2.  是否自动刷新

`BufferedWriter` 写入数据时不会主动刷新缓存区，需要手动调用 `flush()` 方法刷新缓存，否则有可能出现数据未被写入输出流中的情况。相比之下，`PrintWriter` 具有自动刷新缓冲区的功能，即在输出数据时自动将数据刷新到输出流中。 ^30c160

3.  是否抛出异常

`BufferedWriter` 的 `write()` 方法只抛出IOException异常，而 `PrintWriter` 的 `print()` 方法可抛出IOException和NullPointerException异常。因此，使用 `PrintWriter` 写入输出流时应该先判断数据是否为 `null`。

因此，如果你只是想向一个字符输出流（如 `FileWriter` 或 `StringWriter`）写入文本数据，则可以选择 `BufferedWriter` 或 `PrintWriter` 都可以；如果需要向二进制流中写入数据，则需要使用 `PrintWriter`。如果你希望向输出流写入的数据在写入时就能即时刷新，那么应该使用 `PrintWriter`。如果希望手动控制缓冲区的刷新时间，或者在只处理字符流的情况下保持兼容性，则应该使用 `BufferedWriter`。

### `OutputStreamWriter` 和 `BufferedWriter`

`OutputStreamWriter` 和 `BufferedWriter` 都通过将字符转换为字节的方式将文本写入输出流。

两者的区别如下：

1.  输出类型不同

-   `OutputStreamWriter` 可以输出字节流，也可以输出字符流。
-   `BufferedWriter` 只能输出字符流。

2.  I/O 操作方式不同

-   `OutputStreamWriter` 输出的数据在写入流之前不会缓存，因此在 I/O 操作时需要进行较多的 I/O 操作。使用 `OutputStreamWriter` 进行 I/O 操作通常需要通过 `BufferedWriter` 来实现一定程度上的缓冲。
-   `BufferedWriter` 在写入数据时会首先将数据存储在缓存区中，只有在缓存区满、关闭缓存器或显式刷新数据时才会将数据写入流中。这是相比于 `OutputStreamWriter` 一个显著的优势，可以减少 I/O 操作，从而提高程序的效率。

因此，如果需要输出字节流或字符流，可以使用 `OutputStreamWriter`，如果只需要输出字符流，或希望减少 I/O 操作，可以使用 `BufferedWriter`。此外，如果需要输出更高层次、更方便的 I/O 操作，建议使用 `PrintWriter` 或 `PrintStream` 等类。

### 总结

- 三种 Writer 中`OutputStreamWriter` 和`PrintWriter`可以输出字节流，因此这两个类型的示例对象都可以以`OutputStream`类型实例作为参数，由于`BufferedWriter`不能输出字节流，因此当需要使用缓存写入时，不可以使`BufferedWriter`实例直接写入`OutputStream`实例，应选择`OutputStreamWriter`实例作为`BufferedWriter`与`OutputStream`实例的中间转换器。
- `PrintWriter`和 `BufferedWriter`类型实例在写入数据时都需要及时[冲洗缓存](OutputStream#^30c160)，前者有构造参数自动冲洗，后者只能使用[`flush`方法](#^159f7e)冲洗，否者数据不会写入输出流。