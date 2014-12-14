package jfs.authentication.ntlm.message.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.TransferableData;

public class VersionStructure extends TransferableData{
	public static final int FIXED_SIZE = 8;
	
	public byte ProductMajorVersion;
	public byte ProductMinorVersion;
	public short ProductBuild;
	public byte Reserved1;
	public byte Reserved2;
	public byte Reserved3;
	public byte NTLMRevisionCurrent;
	
	@Override
	public byte[] toBytes() throws IOException {
		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(ProductMajorVersion);
		buffer.put(ProductMinorVersion);
		buffer.putShort(ProductBuild);
		buffer.put(Reserved1);
		buffer.put(Reserved2);
		buffer.put(Reserved3);
		buffer.put(NTLMRevisionCurrent);
		return data;
	}

	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		ProductMajorVersion = buffer.get();
		ProductMinorVersion = buffer.get();
		ProductBuild = buffer.getShort();
		Reserved1 = buffer.get();
		Reserved2 = buffer.get();
		Reserved3 = buffer.get();
		NTLMRevisionCurrent = buffer.get();
		
		return size();
	}

	@Override
	public int size() {
		return FIXED_SIZE;
	}

}
