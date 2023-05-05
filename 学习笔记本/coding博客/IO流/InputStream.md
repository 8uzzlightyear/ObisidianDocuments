
# 读取方法
<a name="my-anchor1"></a>
## 输入流read()方法

`read()` 方法有以下几种不同的形式:

1.  `public abstract int read() throws IOException`

该方法读取一个字节的数据并返回其作为无符号字节（即0到255）的值。如果已经到达流的末尾，则返回值为 -1。此方法阻塞直到输入数据为止，或者检测到输入流已经关闭。


```java
InputStream inputStream = new FileInputStream("example.txt");
int byteRead = inputStream.read();
while (byteRead != -1) {
    // 处理已读取的数据
    byteRead = inputStream.read();
}
```

2.  `public int read(byte[] b) throws IOException`

该方法从输入流中读取多个字节，并将其存储到缓冲区数组 `b` 中。返回读取的字节数，如果已经到达流的末尾则返回值为 -1。


```java
InputStream inputStream = new FileInputStream("example.txt");
byte[] buffer = new byte[1024]; // 声明一个缓冲区
int bytesRead = 0;
while ((bytesRead = inputStream.read(buffer)) != -1) {
    // 处理已读取的数据
}
```

3.  `public int read(byte[] b, int off, int len) throws IOException`

该方法从输入流中读取最多 `len` 个字节，并将其存储到一个数组 `b` 中，该数组从偏移量 `off` 处开始存储。返回值为实际读取的字节数。


```java
InputStream inputStream = new FileInputStream("example.txt");
byte[] buffer = new byte[1024]; // 声明一个缓冲区
int bytesRead = 0;
// 读取最多 1024 个字节
bytesRead = inputStream.read(buffer, 0, 1024);
if (bytesRead != -1) {
    // 处理已读取的数据
}
```

4.  `public long skip(long n) throws IOException`

该方法跳过并丢弃输入流中的 `n` 个字节数据，返回值为实际跳过的字节数。如果输入流已经到达结尾，则返回值为 0。


```java
InputStream inputStream = new FileInputStream("example.txt");
inputStream.skip(1024); // 跳过输入流中的前 1024 个字节
```

总之，不论采用哪种形式的 `read()` 方法，都应该始终在读取之前检查返回值是否为 -1，这表示输入流已经结束。 此外，如果未将 `InputStream` 封装在 `BufferedInputStream` 中进行缓冲，则可能需要使用 `mark()` 和 `reset()` 方法以支持多次读取输入流的相同数据。

## Reader方法

### `BufferedReader` 和 `InputStreamReader`

`BufferedReader` 和 `InputStreamReader` 都是 Java 中用于读取字符流的类，但它们的使用方式和功能特点有所不同。

-   数据来源不同

`InputStreamReader` 是从字节流中读取数据，将其解码为字符之后才能被程序读取。因此，它适用于读取以字节为单位的输入流，例如文件流和网络流等。

`BufferedReader` 则用于缓存读取字符流数据，它可以包装任何输入字符流，包括 `FileReader`、`StringReader` 等等。

-   读取方式不同

`InputStreamReader` 是按字节、字节数组或出现的字符集读取输入流，并将其转换为字符。字符集指的是将输入流字节解码为字符时使用的字符编码方式，例如 UTF-8、GBK 等。

`BufferedReader` 通过缓存来提高读取效率。当从底层读取器读取数据时，它会自动一次性读取指定的字符数（比如8192个字符），存储到内部缓存数组中。程序先从缓存中读取数据，当缓存中的数据被读完后，再向底层的流读取新数据填充到缓存中。

-   开销不同

`InputStreamReader` 是一个比较简单的解码器，它的内部结构简单，开销较小。通过 `InputStreamReader` 读取数据时，需要不停地进行编解码操作，读取的数据还需要缓存到内存中，所以其资源开销较大。

`BufferedReader` 通过缓存可以减少 I/O 操作的次数，有利于性能的提升。由于缓存的存在，每次请求数据时都会先判断缓存中是否存在所需数据，如果存在则直接返回缓存，如果不存在再从底层读取器中读取数据，并缓存到缓存区中。

综上所述，如果需要读取以字节为单位的输入流，或需要在读取字符流时指定字符编码方式，可以使用 `InputStreamReader`；如果需要缓存读取字符流数据以提高效率，可以使用 `BufferedReader`。