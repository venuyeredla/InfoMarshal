package org.vgr.store.rdbms;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.io.Bytes;

public class Table {
	private static final Logger LOG = LoggerFactory.getLogger(Table.class);
	private String name;
	private int num;
	private String primary;
	private List<TableColumn> columns;
	private short rowByteSize;
	
	public Table(String name) {
		this.name = name;
		columns=new ArrayList<>();
	}

	public Table(FileStore fileStore,int blockNum) {
		 Bytes b=fileStore.readBlock(blockNum);
		 this.name=b.readString();
		 this.num=b.readInt();
		 this.primary=b.readString();
		 short columnSize=b.readShort();
		 columns=new ArrayList<>();
		 for(int i=0;i<columnSize;i++) {
			 columns.add(new TableColumn(b.readString(), b.readByte(), Types.getType(b.readByte())));
		 }
		 this.rowByteSize=b.readShort();	
     }
	
	/**
	 * Returns the table info as byte block;
	 * @return
	 */
	public boolean write(FileStore fileStore,int blockNum) {
		Bytes block=new Bytes();
		block.write(this.name);
		block.write(this.num);
		block.write(this.primary);
		block.write((short)columns.size());
		this.rowByteSize=rowByteSize();
		LOG.info("Writeing table :"+this);
		columns.forEach(column->{
			block.write(column.getName());
			block.writeByte(column.getPos());
			block.writeByte(column.getType().typeNum());
		});
		block.write(this.rowByteSize);
        fileStore.writeBlock(blockNum, block);		
		return true;
	 }
	
	public short rowByteSize() { 
		int rowByteSize=columns.stream().mapToInt(col->col.getType().byteSize()).sum();
		return (short)rowByteSize;
	}
	
	public boolean addColumn(TableColumn col) {
		return this.columns.add(col);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	public List<TableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<TableColumn> columns) {
		this.columns = columns;
	}

	public short getRowByteSize() {
		return rowByteSize;
	}

	public void setRowByteSize(short rowByteSize) {
		this.rowByteSize = rowByteSize;
	}

	@Override
	public String toString() {
		return "Table [name=" + name + ", num=" + num + ", primary=" + primary + ", columns=" + columns
				+ ", rowByteSize=" + rowByteSize + "]";
	}
	
}
