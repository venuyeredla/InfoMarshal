package org.vgr.store.io;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.ds.BstTest;
/**
 * Used to write data in terms of blocks
 * @author vyeredla
 *
 */
public class DataWriter implements Closeable {
	private static final Logger LOG=LoggerFactory.getLogger(DataWriter.class);
 OutputStream os=null;
 String fileName;
 RamStorage ramStorage=null;
 boolean isRamStorage=false;
 private int bytesWritten=0;
 
 public DataWriter(String fileName) {
		try {
			this.fileName=fileName;
			this.os=new BufferedOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  }
  public DataWriter(OutputStream os) {
		this.os=new BufferedOutputStream(os);
  }
	public DataWriter(RamStorage ramStorage) {
		isRamStorage=true;
		this.ramStorage=ramStorage;
	}

	public void writeByte(int b) {
		try {
			if(isRamStorage) {
				ramStorage.write(b);
			}else {
				os.write(b);
			}
			bytesWritten++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeBlock(int offset,Block block) {
		try {
			Path path=FileSystems.getDefault().getPath(fileName);
			SeekableByteChannel sbc=Files.newByteChannel(path, StandardOpenOption.WRITE);
			sbc.position(offset);
			ByteBuffer byteBuffer=ByteBuffer.wrap(block.getBytes());
			sbc.write(byteBuffer);
            sbc.close();	
            LOG.info("No of bytes written : "+block.getBytes().length);
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	 }
	
	public void writeBytes(byte[] bytes) {
		try {
			bytesWritten=bytes.length;
			this.os.write(bytes);
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	}
	
	public int getFilePointer() {
		return bytesWritten;
	}
	
	public void update(int offset,byte[] bytes) {
		 try {
		        Path path=FileSystems.getDefault().getPath(fileName);
				SeekableByteChannel sbc=Files.newByteChannel(path, StandardOpenOption.WRITE);
				sbc.position(offset);
				ByteBuffer byteBuffer=ByteBuffer.wrap(bytes);
				sbc.write(byteBuffer);
                sbc.close();				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void flush() {
		try {
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void close(){
		try {
			if(!isRamStorage) {
				os.flush();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
