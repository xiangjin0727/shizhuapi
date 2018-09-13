package com.hz.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 * This class converts byte array data from and to hex and also provides
 * a hexdump method.
 *
 * @author Albrecht Messner
 */
public final class HexUtil
{
   private static final int BYTE_UNSIGNED_MAX = 255;
   private static final int HALF_BYTE = 4;
   private static final byte LOW_HALF_MASK = (byte) 0x0F;
   private static final byte HIGH_HALF_MASK = (byte) 0xF0;
   private static final int BYTE_MASK = 0xFF;
   private static final int CHARS_PER_BYTE = 2;
   private static final int DECIMAL_OFFSET = 10;


   private static final char[] HEX_CHARS = {
      '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
   };

   private static final String[] BYTE_AS_HEX
         = new String[BYTE_UNSIGNED_MAX + 1];

   private static final int[] CHAR_TO_NIBBLE_LOW
         = new int[BYTE_UNSIGNED_MAX + 1];
   private static final int[] CHAR_TO_NIBBLE_HIGH
         = new int[BYTE_UNSIGNED_MAX + 1];

   private static final int DUMP_BYTES_PER_LINE = 16;
   private static final int DUMP_BYTES_PER_COLUMN = 8;
   private static final int DUMP_ADDR_LEN = 8;
   private static final int DUMP_BUFFER_SIZE = DUMP_ADDR_LEN // address
         + DUMP_BYTES_PER_LINE * CHARS_PER_BYTE // bytes
         + DUMP_BYTES_PER_LINE // whitespaces between bytes
         + 1 + 1; // extra whitespace after address and to separate columns

   static
   {
      // set up tables/arrays for byte to hex conversion
      final StringBuffer sb = new StringBuffer();
      for (int i = 0; i < BYTE_AS_HEX.length; i++)
      {
         sb.setLength(0);
         final int lowerFourBits = i & LOW_HALF_MASK;
         final int highFourBits = (i & HIGH_HALF_MASK) >> HALF_BYTE;
         sb.append(HEX_CHARS[highFourBits]);
         sb.append(HEX_CHARS[lowerFourBits]);
         BYTE_AS_HEX[i] = sb.toString();
      }

      // set up tables/arrays for hex to byte conversion
      Arrays.fill(CHAR_TO_NIBBLE_LOW, -1);
      Arrays.fill(CHAR_TO_NIBBLE_HIGH, -1);
      for (int i = '0'; i <= '9'; i++)
      {
         CHAR_TO_NIBBLE_LOW[i] = i - '0';
         CHAR_TO_NIBBLE_HIGH[i] = (i - '0') << HALF_BYTE;
      }
      for (int i = 'a'; i <= 'f'; i++)
      {
         CHAR_TO_NIBBLE_LOW[i] = i - 'a' + DECIMAL_OFFSET;
         CHAR_TO_NIBBLE_HIGH[i] = (i - 'a' + DECIMAL_OFFSET) << HALF_BYTE;
      }
      for (int i = 'A'; i <= 'F'; i++)
      {
         CHAR_TO_NIBBLE_LOW[i] = i - 'A' + DECIMAL_OFFSET;
         CHAR_TO_NIBBLE_HIGH[i] = (i - 'A' + DECIMAL_OFFSET) << HALF_BYTE;
      }
   }

   private HexUtil ()
   {
       // utility class that provides only static methods
   }

   /**
    * Converts a byte array into an upper-case hex string, starting at the
    * given offset and converting the given number of bytes.
    * @param data the byte data to convert to hex
    * @param offset the start offset in the byte array
    * @param length the number of bytes to convert
    * @return null if data was null, an empty string if data.length == 0,
    *       and the hex representation of the byte array otherwise
    * @throws IndexOutOfBoundsException if offset + length > data.lenght
    */
   public static String bytesToHex (
         final byte[] data, final int offset, final int length)
         throws IndexOutOfBoundsException
   {
      final String result;
      if (data == null)
      {
         result = null;
      }
      else
      {
         final StringBuffer sbuf = new StringBuffer();
         for (int i = offset; i < offset + length; i++)
         {
            sbuf.append(BYTE_AS_HEX[data[i] & BYTE_MASK]);
         }
         result = sbuf.toString();
      }
      return result;
   }

   /**
    * Converts a byte array into an upper-case hex string, starting at the
    * first byte and converting the whole array.
    * @param data the byte data to convert to hex
    * @return null if data was null, an empty string if data.length == 0,
    *       and the hex representation of the byte array otherwise
    */
   public static String bytesToHex (final byte[] data)
   {
      final String result;
      if (data != null)
      {
         result = bytesToHex(data, 0, data.length);
      }
      else
      {
         result = null;
      }
      return result;
   }

   /**
    * Convert the given hex string to a byte array.
    * @param s the string to convert
    * @return null if the string is null, an empty byte array if s.length == 0
    *       and a byte array representing the hex string otherwise
    * @throws IllegalArgumentException if the string is not a multiple of 2
    *       characters long, or if the string contains an invalid hex char
    */
   public static byte[] stringToBytes (final String s)
         throws IllegalArgumentException
   {
      final byte[] result;
      if (s == null)
      {
         result = null;
      }
      else if (s.length() == 0)
      {
         result = new byte[0];
      }
      else
      {
         // string must be a multiple of 2 chars
         if (s.length() % CHARS_PER_BYTE != 0)
         {
            throw new IllegalArgumentException(
                  "The length of a hex string must be a multiple of 2 (was "
                  + s.length() + ")");
         }
         int count = 0;
         result = new byte[s.length() / CHARS_PER_BYTE];
         try
         {
            for (int i = 0; i < s.length(); i++)
            {
               final char c1 = s.charAt(i);
               final char c2 = s.charAt(++i);
               final int b = CHAR_TO_NIBBLE_HIGH[c1] | CHAR_TO_NIBBLE_LOW[c2];
               if (b == -1)
               {
                  throw new IllegalArgumentException(
                        "'" + c1 + c2
                        + "' is not a valid hex representation of a byte");
               }
               result[count] = (byte) b;
               ++count;
            }
         }
         catch (IndexOutOfBoundsException ex)
         {
            final char c1 = s.charAt(count * CHARS_PER_BYTE);
            final char c2 = s.charAt(count * CHARS_PER_BYTE + 1);
            final IllegalArgumentException e = new IllegalArgumentException(
                  "'" + c1 + c2
                  + "' is not a valid hex representation of a byte");
            e.initCause(ex);
            throw e;
         }
      }
      return result;
   }


   private static void padBuffer (final StringBuffer sbuf)
   {
      while (sbuf.length() < DUMP_BUFFER_SIZE)
      {
         sbuf.append(' ');
      }
   }

   private static String offsetToHex (int offset)
   {
      String s = Integer.toHexString(offset);
      while (s.length() < DUMP_ADDR_LEN)
      {
         s = "0" + s;
      }
      return s;
   }
 /*  Java功能包-2进制，16进制，BCD，ascii转换 收藏 
   java二进制,字节数组,字符,十六进制,BCD编码转换2007-06-07 00:17/** *//**
       * 把16进制字符串转换成字节数组
       * @param hex
       * @return
       */
   public static byte[] hexStringToByte(String hex) {
       int len = (hex.length() / 2);
       byte[] result = new byte[len];
       char[] achar = hex.toCharArray();
       for (int i = 0; i < len; i++) {
        int pos = i * 2;
        result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
       }
       return result;
   }

   private static byte toByte(char c) {
       byte b = (byte) "0123456789ABCDEF".indexOf(c);
       return b;
   }

   /** *//**
       * 把字节数组转换成16进制字符串
       * @param bArray
       * @return
       */
   public static final String bytesToHexString(byte[] bArray) {
       StringBuffer sb = new StringBuffer(bArray.length);
       String sTemp;
       for (int i = 0; i < bArray.length; i++) {
        sTemp = Integer.toHexString(0xFF & bArray[i]);
        if (sTemp.length() < 2)
         sb.append(0);
        sb.append(sTemp.toUpperCase());
       }
       return sb.toString();
   }

   /** *//**
       * 把字节数组转换为对象
       * @param bytes
       * @return
       * @throws IOException
       * @throws ClassNotFoundException
       */
   public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
       ByteArrayInputStream in = new ByteArrayInputStream(bytes);
       ObjectInputStream oi = new ObjectInputStream(in);
       Object o = oi.readObject();
       oi.close();
       return o;
   }

   /** *//**
       * 把可序列化对象转换成字节数组
       * @param s
       * @return
       * @throws IOException
       */
   public static final byte[] objectToBytes(Serializable s) throws IOException {
       ByteArrayOutputStream out = new ByteArrayOutputStream();
       ObjectOutputStream ot = new ObjectOutputStream(out);
       ot.writeObject(s);
       ot.flush();
       ot.close();
       return out.toByteArray();
   }

   public static final String objectToHexString(Serializable s) throws IOException{
       return bytesToHexString(objectToBytes(s));
   }

   public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException{
       return bytesToObject(hexStringToByte(hex));
   }

   /** *//**
       * @函数功能: BCD码转为10进制串(阿拉伯数据)
       * @输入参数: BCD码
       * @输出结果: 10进制串
       */
   public static String bcd2Str(byte[] bytes){
       StringBuffer temp=new StringBuffer(bytes.length*2);

       for(int i=0;i<bytes.length;i++){
        temp.append((byte)((bytes[i]& 0xf0)>>>4));
        temp.append((byte)(bytes[i]& 0x0f));
       }
       return temp.toString().substring(0,1).equalsIgnoreCase("0")?temp.toString().substring(1):temp.toString();
   }

   /** *//**
       * @函数功能: 10进制串转为BCD码
       * @输入参数: 10进制串
       * @输出结果: BCD码
       */
   public static byte[] str2Bcd(String asc) {
       int len = asc.length();
       int mod = len % 2;

       if (mod != 0) {
        asc = "0" + asc;
        len = asc.length();
       }

       byte abt[] = new byte[len];
       if (len >= 2) {
        len = len / 2;
       }

       byte bbt[] = new byte[len];
       abt = asc.getBytes();
       int j, k;

       for (int p = 0; p < asc.length()/2; p++) {
        if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
         j = abt[2 * p] - '0';
        } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
         j = abt[2 * p] - 'a' + 0x0a;
        } else {
         j = abt[2 * p] - 'A' + 0x0a;
        }

        if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
         k = abt[2 * p + 1] - '0';
        } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
         k = abt[2 * p + 1] - 'a' + 0x0a;
        }else {
         k = abt[2 * p + 1] - 'A' + 0x0a;
        }

        int a = (j << 4) + k;
        byte b = (byte) a;
        bbt[p] = b;
       }
       return bbt;
   }
   /** *//**
       * @函数功能: BCD码转ASC码
       * @输入参数: BCD串
       * @输出结果: ASC码
       */
   public static String BCD2ASC(byte[] bytes) {
       StringBuffer temp = new StringBuffer(bytes.length * 2);

       for (int i = 0; i < bytes.length; i++) {
        int h = ((bytes[i] & 0xf0) >>> 4);
        int l = (bytes[i] & 0x0f);   
       // temp.append(BToA[h]).append( BToA[l]);
       }
       return temp.toString() ;
   }

   /** *//**
       * MD5加密字符串，返回加密后的16进制字符串
       * @param origin
       * @return
       */
   public static String MD5EncodeToHex(String origin) { 
          return bytesToHexString(MD5Encode(origin));
        }

   /** *//**
       * MD5加密字符串，返回加密后的字节数组
       * @param origin
       * @return
       */
   public static byte[] MD5Encode(String origin){
       return MD5Encode(origin.getBytes());
   }

   /** *//**
       * MD5加密字节数组，返回加密后的字节数组
       * @param bytes
       * @return
       */
   public static byte[] MD5Encode(byte[] bytes){
       MessageDigest md=null;
       try {
        md = MessageDigest.getInstance("MD5");
        return md.digest(bytes);
       } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return new byte[0];
       }
     
   }
}
