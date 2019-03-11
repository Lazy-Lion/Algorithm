## 字符串匹配算法
 > 字符串匹配两个步骤
 * 预处理 (preprocessing)
 * 匹配 (matching)
 <br />
 
 > 定义：假设在字符串A中查找字符串B
 * 主串: A
 * 模式串: B
 * n = A.length(), m = B.length() => n >= m
 
 
 
## java中的 Character
##### [Unicode Character Representations](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html)
　The char data type (and therefore the value that a Character object encapsulates) are based on the original <br/>
Unicode specification, which defined characters as fixed-width 16-bit entities. The Unicode Standard has since <br/> 
been changed to allow for characters whose representation requires more than 16 bits. The range of legal code <br/>
points is now U+0000 to U+10FFFF, known as Unicode scalar value. (Refer to the definition of the U+n notation <br/>
in the Unicode Standard.)<br/>
　The set of characters from U+0000 to U+FFFF is sometimes referred to as the Basic Multilingual Plane (BMP). <br/>
Characters whose code points are greater than U+FFFF are called supplementary characters. The Java platform <br/> 
uses the UTF-16 representation in char arrays and in the String and StringBuffer classes. In this representation,<br/> 
supplementary characters are represented as a pair of char values, the first from the high-surrogates range, <br/>
(\uD800-\uDBFF), the second from the low-surrogates range (\uDC00-\uDFFF).<br/>
　A char value, therefore, represents Basic Multilingual Plane (BMP) code points, including the surrogate <br/> 
code points, or code units of the UTF-16 encoding. An int value represents all Unicode code points, including <br/>
supplementary code points. The lower (least significant) 21 bits of int are used to represent Unicode code points <br/>
and the upper (most significant) 11 bits must be zero. Unless otherwise specified, the behavior with respect <br/>
to supplementary characters and surrogate char values is as follows: <br/>
* The methods that only accept a char value cannot support supplementary characters. They treat char values from <br/>
the surrogate ranges as undefined characters. For example, Character.isLetter('\uD840') returns false, even <br/>
though this specific value if followed by any low-surrogate value in a string would represent a letter. 
* The methods that accept an int value support all Unicode characters, including supplementary characters. <br/>
For example, Character.isLetter(0x2F81A) returns true because the code point value represents a letter <br/>
(a CJK ideograph). 

　In the Java SE API documentation, Unicode code point is used for character values in the range between U+0000 and 
U+10FFFF, and Unicode code unit is used for 16-bit char values that are code units of the UTF-16 encoding. For 
more information on Unicode terminology, refer to the [Unicode Glossary](http://www.unicode.org/glossary/).
 <br />
##### [Unicode表](https://unicode-table.com/cn/)
 
 