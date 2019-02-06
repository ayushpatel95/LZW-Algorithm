/**
 * Name - Ayush Patel
 * Student ID - 801022369
 */
package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Decoder {
    public static void main(String args[]) throws IOException {

        String decompressed = decompress(args[0], args[1]); //calls decompress function with arguments filename
        // and bit length and returns decoded string.
        System.out.println(decompressed);
    }

    /**
     * This function splits an input string into an an equal length of characters array. Here it is used to split the string into 16 bit arrays.
     */
    private static String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }

    /**
     * Decompress a list of output ks to a string.
     */
    public static String decompress(String file_name, String bit_length) throws IOException {
        // Build the dictionary.
        int dictSize = 256;


        File file = new File(file_name);
        List<Integer> integerList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-16BE"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            String clear_string = sb.toString().replaceAll("\\s+", "");
            //used to replace the tabspaces and whitespaces with "".

            String[] strings = splitToNChar(clear_string, 16); //strings contains the 16bit binary numbers in an array.
            for (int i = 0; i < strings.length; i++) {
                integerList.add(Integer.parseInt(strings[i], 2)); //integerList contains the integer values for the binary numbers.
            }

            if (integerList.size() == 0) {
                System.out.print("The file is empty\t");
            } else {
                Map<Integer, String> dictionary = new HashMap<Integer, String>();
                for (int i = 0; i < 256; i++)
                    dictionary.put(i, "" + (char) i); //initialized dictionary with ASCII characters.


                String code = "" + (char) (int) integerList.remove(0);
                StringBuffer result = new StringBuffer(code);
                for (int k : integerList) {
                    String entry = null;
                    if (dictionary.containsKey(k))
                        entry = dictionary.get(k);
                    else if (k == dictSize)
                        entry = code + code.charAt(0);
                    else
                        System.out.println("Max table size reached");

                    result.append(entry);

                    // Add code+entry[0] to the dictionary.
                    if (dictSize < Math.pow(2, Double.parseDouble(bit_length)))
                        dictionary.put(dictSize++, code + entry.charAt(0));
                    code = entry;
                }

                //Writing output string to the file
                String[] name = file_name.split(".lzw");
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(name[0] + "_decoded.txt")))) {
                    writer.write(result.toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result.toString();
            }




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }


        return null;
    }


}
