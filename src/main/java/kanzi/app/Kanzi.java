/*
Copyright 2011-2017 Frederic Langlet
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
you may obtain a copy of the License at

                http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package kanzi.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Kanzi
{
   private static final String[] CMD_LINE_ARGS = new String[]
   {
      "-c", "-d", "-i", "-o", "-b", "-t", "-e", "-j", "-v", "-l", "-s", "-x", "-f", "-h"
   };

   //private static final int ARG_IDX_COMPRESS = 0;
   //private static final int ARG_IDX_DECOMPRESS = 1;
   private static final int ARG_IDX_INPUT = 2;
   private static final int ARG_IDX_OUTPUT = 3;
   private static final int ARG_IDX_BLOCK = 4;
   private static final int ARG_IDX_TRANSFORM = 5;
   private static final int ARG_IDX_ENTROPY = 6;
   private static final int ARG_IDX_JOBS = 7;
   private static final int ARG_IDX_VERBOSE = 8;
   private static final int ARG_IDX_LEVEL = 9;
   
   private static final String APP_HEADER = "Kanzi 1.4 (C) 2018,  Frederic Langlet";
   
  
   public static void main(String[] args)
   {
      Map<String, Object> map = new HashMap<>();
      processCommandLine(args, map);    
      char mode = (char) map.remove("mode");

      if (mode == 'c')
      {
         BlockCompressor bc = null;

         try
         {
            bc = new BlockCompressor(map);
         }
         catch (Exception e)
         {
            System.err.println("Could not create the compressor: "+e.getMessage());
            System.exit(kanzi.Error.ERR_CREATE_COMPRESSOR);
         }

         final int code = bc.call();

         if (code != 0)
            bc.dispose();

         System.exit(code);
      }

      if (mode == 'd')
      {
         BlockDecompressor bd = null;

         try
         {
            bd = new BlockDecompressor(map);
         }
         catch (Exception e)
         {
            System.err.println("Could not create the decompressor: "+e.getMessage());
            System.exit(kanzi.Error.ERR_CREATE_DECOMPRESSOR);
         }

         final int code = bd.call();

         if (code != 0)
            bd.dispose();

         System.exit(code);
      }

      System.out.println("Missing arguments: try --help or -h");
      System.exit(1);
   }


    private static void processCommandLine(String args[], Map<String, Object> map)
    {
        int blockSize = -1;
        int verbose = 1;
        boolean overwrite = false;
        boolean checksum = false;
        boolean skip = false;
        String inputName = null;
        String outputName = null;
        String codec = null;
        String transform = null;
        int tasks = 0;
        int ctx = -1;
        int level = -1;
        char mode = ' ';

        for (String arg : args)
        {
           arg = arg.trim();

           if (arg.equals("-o"))
           {
              ctx = ARG_IDX_OUTPUT;
              continue;
           }

           if (arg.equals("-v"))
           {
              ctx = ARG_IDX_VERBOSE;
              continue;
           }

           // Extract verbosity, output and mode first
           if (arg.equals("--compress") || (arg.equals("-c")))
           {
              if (mode == 'd')
              {
                  System.err.println("Both compression and decompression options were provided.");
                  System.exit(kanzi.Error.ERR_INVALID_PARAM);
              }

              mode = 'c';
              continue;
           }

           if (arg.equals("--decompress") || (arg.equals("-d")))
           {
              if (mode == 'c')
              {
                  System.err.println("Both compression and decompression options were provided.");
                  System.exit(kanzi.Error.ERR_INVALID_PARAM);
              }

              mode = 'd';
              continue;
           }

           if (arg.startsWith("--verbose=") || (ctx == ARG_IDX_VERBOSE))
           {
               String verboseLevel = arg.startsWith("--verbose=") ? arg.substring(10).trim() : arg;

               try
               {
                   verbose = Integer.parseInt(verboseLevel);

                   if ((verbose < 0) || (verbose > 5))
                      throw new NumberFormatException();
               }
               catch (NumberFormatException e)
               {
                  System.err.println("Invalid verbosity level provided on command line: "+arg);
                  System.exit(kanzi.Error.ERR_INVALID_PARAM);
               }
           }
           else if (arg.startsWith("--output=") || (ctx == ARG_IDX_OUTPUT))
           {
               outputName = arg.startsWith("--output=") ? arg.substring(9).trim() : arg;
           }

           ctx = -1;
        }

        // Overwrite verbosity if the output goes to stdout
        if ("STDOUT".equalsIgnoreCase(outputName))
           verbose = 0;

        if (verbose >= 1)
            printOut("\n" + APP_HEADER +"\n", true);

        ctx = -1;

        for (String arg : args)
        {
           arg = arg.trim();

           if (arg.equals("--help") || arg.equals("-h"))
           {
               printOut("", true);
               printOut("   -h, --help", true);
               printOut("        display this message\n", true);
               printOut("   -v, --verbose=<level>", true);
               printOut("        0=silent, 1=default, 2=display details, 3=display configuration,", true);
               printOut("        4=display block size and timings, 5=display extra information", true);
               printOut("        Verbosity is reduced to 1 when files are processed concurrently", true);
               printOut("        Verbosity is silently reduced to 0 when the output is 'stdout'", true);
               printOut("        (EG: The source is a directory and the number of jobs > 1).\n", true);
               printOut("   -f, --force", true);
               printOut("        overwrite the output file if it already exists\n", true);
               printOut("   -i, --input=<inputName>", true);
               printOut("        mandatory name of the input file or directory or 'stdin'", true);
               printOut("        When the source is a directory, all files in it will be processed.", true);
               printOut("        Provide " + File.separator + ". at the end of the directory name to avoid recursion", true);
               printOut("        (EG: myDir" + File.separator + ". => no recursion)\n", true);
               printOut("   -o, --output=<outputName>", true);
               
               if (mode == 'c')
               {
                  printOut("        optional name of the output file or directory (defaults to", true);
                  printOut("        <inputName.knz>) or 'none' or 'stdout'. 'stdout' is not valid", true);
                  printOut("        when the number of jobs is greater than 1.\n", true);
               }
               else if (mode == 'd')
               {
                  printOut("        optional name of the output file or directory (defaults to", true);
                  printOut("        <inputName.bak>) or 'none' or 'stdout'. 'stdout' is not valid", true);
                  printOut("        when the number of jobs is greater than 1.\n", true);
               }
               else
               {
                  printOut("        optional name of the output file or 'none' or 'stdout'.\n", true);
               }

               if (mode != 'd')
               {
                  printOut("   -b, --block=<size>", true);
                  printOut("        size of blocks, multiple of 16 (default 1 MB, max 1 GB, min 1 KB).\n", true);
                  printOut("   -l, --level=<compression>", true);
                  printOut("        set the compression level [0..6]", true);
                  printOut("        Providing this option forces entropy and transform.", true);
                  printOut("        0=None&None (store), 1=TEXT+LZ4&HUFFMAN, 2=TEXT+ROLZ", true);
                  printOut("        3=BWT+RANK+ZRLT&ANS0, 4=BWT+RANK+ZRLT&FPAQ, 5=BWT&CM", true);
                  printOut("        6=X86+RLT+TEXT&TPAQ, 7=X86+RLT+TEXT&TPAQX\n", true);
                  printOut("   -e, --entropy=<codec>", true);
                  printOut("        entropy codec [None|Huffman|ANS0|ANS1|Range|PAQ|FPAQ|TPAQ|TPAQX|CM]", true);
                  printOut("        (default is ANS0)\n", true);
                  printOut("   -t, --transform=<codec>", true);
                  printOut("        transform [None|BWT|BWTS|SNAPPY|LZ4|ROLZ|RLT|ZRLT|MTFT|RANK|TEXT|X86]", true);
                  printOut("        EG: BWT+RANK or BWTS+MTFT (default is BWT+RANK+ZRLT)\n", true);
                  printOut("   -x, --checksum", true);
                  printOut("        enable block checksum\n", true);
                  printOut("   -s, --skip", true);
                  printOut("        copy blocks with high entropy instead of compressing them.\n", true);
               }

               printOut("   -j, --jobs=<jobs>", true);
               printOut("        maximum number of jobs the program may start concurrently", true);
               printOut("        (default is 1, maximum is 64).\n", true);
               printOut("", true);

               if (mode != 'd')
               {
                  printOut("EG. java -cp kanzi.jar kanzi.app.Kanzi -c -i foo.txt -o none -b 4m -l 4 -v 3\n", true);
                  printOut("EG. java -cp kanzi.jar kanzi.app.Kanzi -c -i foo.txt -f ", true);
                  printOut("    -t BWT+MTFT+ZRLT -b 4m -e FPAQ -v 3 -j 4\n", true);
                  printOut("EG. java -cp kanzi.jar kanzi.app.Kanzi --compress --input=foo.txt --force ", true);
                  printOut("    --output=foo.knz --transform=BWT+MTFT+ZRLT --block=4m --entropy=FPAQ ", true);
                  printOut("    --verbose=3 --jobs=4\n", true);
               }

               if (mode != 'c')
               {
                  printOut("EG. java -cp kanzi.jar kanzi.app.Kanzi -d -i foo.knz -f -v 2 -j 2\n", true);
                  printOut("EG. java -cp kanzi.jar kanzi.app.Kanzi --decompress --input=foo.knz --force --verbose=2 --jobs=2\n", true);
               }

               System.exit(0);
           }

           if (arg.equals("--compress") || arg.equals("-c") || arg.equals("--decompress") || arg.equals("-d"))
           {
               if (ctx != -1)
                  printOut("Warning: ignoring option [" + CMD_LINE_ARGS[ctx] + "] with no value.", verbose>0);

               ctx = -1;
               continue;
           }

           if (arg.equals("--force") || arg.equals("-f"))
           {
               if (ctx != -1)
                  printOut("Warning: ignoring option [" + CMD_LINE_ARGS[ctx] + "] with no value.", verbose>0);

               overwrite = true;
               ctx = -1;
               continue;
           }

           if (arg.equals("--skip") || arg.equals("-s"))
           {
               if (ctx != -1)
                  printOut("Warning: ignoring option [" + CMD_LINE_ARGS[ctx] + "] with no value.", verbose>0);

               skip = true;
               ctx = -1;
               continue;
           }

           if (arg.equals("--checksum") || arg.equals("-x"))
           {
               if (ctx != -1)
                  printOut("Warning: ignoring option [" + CMD_LINE_ARGS[ctx] + "] with no value.", verbose>0);

               checksum = true;
               ctx = -1;
               continue;
           }

           if (ctx == -1)
           {
               int idx = -1;

               for (int i=0; i<CMD_LINE_ARGS.length; i++)
               {
                  if (CMD_LINE_ARGS[i].equals(arg))
                  {
                     idx = i;
                     break;
                  }
               }

               if (idx != -1)
               {
                  ctx = idx;
                  continue;
               }
           }

           if (arg.startsWith("--input=") || (ctx == ARG_IDX_INPUT))
           {
               inputName = arg.startsWith("--input=") ? arg.substring(8).trim() : arg;
               ctx = -1;
               continue;
           }

           if (arg.startsWith("--entropy=") || (ctx == ARG_IDX_ENTROPY))
           {
               codec = arg.startsWith("--entropy=") ? arg.substring(10).trim().toUpperCase() :
                 arg.toUpperCase();
               ctx = -1;
               continue;
           }

           if (arg.startsWith("--transform=") || (ctx == ARG_IDX_TRANSFORM))
           {
               transform = arg.startsWith("--transform=") ? arg.substring(12).trim().toUpperCase() :
                 arg.toUpperCase();
               ctx = -1;
               continue;
           }

           if (arg.startsWith("--level=") || (ctx == ARG_IDX_LEVEL))
           {
               String str = arg.startsWith("--level=") ? arg.substring(8).trim().toUpperCase() :
                 arg.toUpperCase();
              
               try
               {
                   level = Integer.parseInt(str);
               }
               catch (NumberFormatException e)
               {
                  System.err.println("Invalid compression level provided on command line: "+arg);
                  System.exit(kanzi.Error.ERR_INVALID_PARAM);
               }

               if ((level < 0) || (level > 7))
               {
                  System.err.println("Invalid compression level provided on command line: "+arg);
                  System.exit(kanzi.Error.ERR_INVALID_PARAM);                  
               }
               
               ctx = -1;
               continue;
           }

           if (arg.startsWith("--block=") || (ctx == ARG_IDX_BLOCK))
           {
               String str = arg.startsWith("--block=") ? arg.substring(8).toUpperCase().trim() :
                  arg.toUpperCase();
               char lastChar = (str.length() == 0) ? ' ' : str.charAt(str.length()-1);
               int scale = 1;

              try
              {
                 // Process K or M or G suffix
                 if ('K' == lastChar)
                 {
                     scale = 1024;
                     str = str.substring(0, str.length()-1);
                 }
                 else if ('M' == lastChar)
                 {
                     scale = 1024 * 1024;
                     str = str.substring(0, str.length()-1);
                 }
                 else if ('G' == lastChar)
                 {
                     scale = 1024 * 1024 * 1024;
                     str = str.substring(0, str.length()-1);
                 }

                 blockSize = scale * Integer.parseInt(str);
                 ctx = -1;
                 continue;
              }
              catch (NumberFormatException e)
              {
                 System.err.println("Invalid block size provided on command line: "+arg);
                 System.exit(kanzi.Error.ERR_INVALID_PARAM);
              }
           }

           if (arg.startsWith("--jobs=") || (ctx == ARG_IDX_JOBS))
           {
              arg = arg.startsWith("--jobs=") ? arg.substring(7).trim() : arg;

              try
              {
                  tasks = Integer.parseInt(arg);

                  if (tasks < 1)
                     throw new NumberFormatException();

                  ctx = -1;
                  continue;
              }
              catch (NumberFormatException e)
              {
                  System.err.println("Invalid number of jobs provided on command line: "+arg);
                  System.exit(kanzi.Error.ERR_INVALID_PARAM);
              }
           }

           if (!arg.startsWith("--verbose=") && (ctx == -1) && !arg.startsWith("--output="))
           {
               printOut("Warning: ignoring unknown option ["+ arg + "]", verbose>0);
           }

           ctx = -1;
        }

        if (inputName == null)
        {
           System.err.println("Missing input file name, exiting ...");
           System.exit(kanzi.Error.ERR_MISSING_PARAM);
        }

        if (ctx != -1)
        {
           printOut("Warning: ignoring option with missing value ["+ CMD_LINE_ARGS[ctx] + "]", verbose>0);
        }

        if (level >= 0)
        {
           if (codec != null)
              printOut("Warning: providing the 'level' option forces the entropy codec. Ignoring ["+ codec + "]", verbose>0);

           if (transform != null)
              printOut("Warning: providing the 'level' option forces the transform. Ignoring ["+ transform + "]", verbose>0);
        }
        
        if (blockSize != -1)
           map.put("block", blockSize);

        map.put("verbose", verbose);
        map.put("mode", mode);
        
        if (mode == 'c')
           map.put("level", level);

        if (overwrite == true)
           map.put("overwrite", overwrite);

        map.put("inputName", inputName);
        map.put("outputName", outputName);

        if (codec != null)
           map.put("entropy", codec);

        if (transform != null)
           map.put("transform", transform);

        if (checksum == true)
           map.put("checksum", checksum);

        if (skip == true)
           map.put("skipBlocks", skip);

        map.put("jobs", tasks);
    }


    private static void printOut(String msg, boolean print)
    {
       if ((print == true) && (msg != null))
          System.out.println(msg);
    }
    
    
    public static void createFileList(String target, List<Path> files) throws IOException
    {
       if (target == null)
          return;
       
       Path root = Paths.get(target);
       
       if (Files.exists(root) == false) 
          throw new IOException("Cannot access input file '"+root+"'");
       
       if ((Files.isRegularFile(root) == true) && (Files.isHidden(root) == true))
          throw new IOException("Cannot access input file '"+root+"'");
       
       if (Files.isRegularFile(root) == true)
       {
          if (target.charAt(0) != '.')
             files.add(root);
          
          return;
       }       
        
       // If not a regular file and not a directory (a link ?), fail 
       if (Files.isDirectory(root) == false)
          throw new IOException("Invalid file type '"+root+"'");
       
       String suffix = File.separator + ".";
       String strRoot = root.toString();
       boolean isRecursive = !strRoot.endsWith(suffix); 
       
       if (isRecursive == true)
       {
          if (strRoot.endsWith(File.separator) == false)
             root = Paths.get(strRoot+File.separator);
       }
       else
       {
          // Remove suffix
          root = Paths.get(strRoot.substring(0, strRoot.length()-1));
       }
       
       try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) 
       {
          for (Path entry: stream) 
          {
             if ((Files.exists(entry) == false) || (Files.isHidden(entry) == true))
                continue;

             if ((Files.isRegularFile(entry) == true) && (entry.getFileName().toString().startsWith(".") == false))
                files.add(entry);
             else if ((isRecursive == true) && (Files.isDirectory(entry) == true))
                createFileList(entry.toString(), files);
          }
       } 
       catch (DirectoryIteratorException e) 
       {
         throw e.getCause();
       }
    }
}    
