/** README

Submitted by - Ayush Patel
Student ID - 801022369

*/

Description of LZW Algorithm:

Lempel–Ziv–Welch (LZW) is a universal lossless data compression algorithm created by Abraham Lempel, Jacob Ziv, and Terry Welch. It was published by Welch in 1984 as an improved implementation of the LZ78 algorithm published by Lempel and Ziv in 1978. The scenario described by Welch's 1984 paper[1] encodes sequences of 8-bit data as fixed-length 12-bit codes. The codes from 0 to 255 represent 1-character sequences consisting of the corresponding 8-bit character, and the codes 256 through 4095 are created in a dictionary for sequences encountered in the data as it is encoded. At each stage in compression, input bytes are gathered into a sequence until the next character would make a sequence for which there is no code yet in the dictionary. The code for the sequence (without that character) is added to the output, and a new code (for the sequence with that character) is added to the dictionary.

Two steps of LZW Algorithm
1. Compression (Encoding)
2. Decomression (Decoding)

Programming Language used: JAVA
Compiler Version: 1.8.0_151


Compile the program
Command to build java class files
-	javac filename.java


Run the Program
Command to run java program
-	java filename InputfileName Bitlength

Command Line Arguments - 
1) InputfileName - The name of the file to be encoded should be provided as input in the commmand line.
2) Bitlength - Bitlength specifies the maximum size of the table for the encoding and decoding.

Data Structure
HashMap data structure is used to implement the algorithm and it contains the ASCII characters as KEY along with its ASCII value as VALUE for encoding and vice versa in case of decoding.

 Syntax:
Map<Integer, String> dictionary = new HashMap<Integer, String>();

 Encoder.java
The Encoder class uses HashMap and follows Pseudocode of Encoding to encode the input file. In HashMap ASCII Character is the KEY and ASCII Value is the VALUE. The Encoder class contains two functions - compress and inttoBinaryString.
 Decompressing.java
The Decoder class uses HashMap in opposite manner, where ASCII Value is the KEY and ASCII Character is the VALUE. It follows the Pseudocode of Decoding to decode the encoded text. The Decoder class contains two functions - decompress and spliintoNchar.

Files associated with the class -
1) input.txt
2) input.lzw (contains encoded output)
3) input_decoded.txt (contains decoded string (same as the input.txt). 

Note - 
1. BufferedReader and BufferedWriter for reading and writing to files.
2. For encoding, Encoder.java file is used. It will generate compressed (lzw) file.
3. For decoding compressed file, Decoder.java is used. It will generate decoded text file, whose contents will be same as the initial input file.
4. The compression (lzw) file is created using charset UTF_16BE and stored in 16-bit format.


Condition for the program to work properly -
The input.txt file should be saved in the current working directory where the class files are stored otherwise it will throw FileNotFound Exception.


Implementation- 
The Compression program generates the compressed file in a 16bit (UTF-16BE) format. The Decompresser program converts the generated compressed file back to the original input file. It handles null files and we can observe that large file size after compression is reduced to tiny files.


1) Pseudocode for Encoding

MAX_TABLE_SIZE=2(bit_length) //bit_length is number of encoding bits
initialize TABLE[0 to 255] = code for individual characters
STRING = null
while there are still input symbols:
SYMBOL = get input symbol
if STRING + SYMBOL is in TABLE:
STRING = STRING + SYMBOL
else:
output the code for STRING
If TABLE.size < MAX_TABLE_SIZE: // if table is not full
add STRING + SYMBOL to TABLE // STRING + SYMBOL now has a code
STRING = SYMBOL
output the code for STRING

2) Pseudocode for Decoding

MAX_TABLE_SIZE=2(bit_length)
initialize TABLE[0 to 255] = code for individual characters
CODE = read next code from encoder
STRING = TABLE[CODE]
output STRING
while there are still codes to receive:
CODE = read next code from encoder
if TABLE[CODE] is not defined: // needed because sometimes the
NEW_STRING = STRING + STRING[0] // decoder may not yet have code!
else:
NEW_STRING = TABLE[CODE]
output NEW_STRING
if TABLE.size < MAX_TABLE_SIZE:
add STRING + NEW_STRING[0] to TABLE
STRING = NEW_STRING
