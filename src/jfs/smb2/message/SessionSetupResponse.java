package jfs.smb2.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.IDecodable;

public class SessionSetupResponse implements IDecodable{
	public static final short FIXED_SIZE = 8;
	
	public short StructureSize;
	public short SessionFlags;
	public short SecurityBufferOffset;
	public short SecurityBufferLength;
	public byte Buffer[];
	
	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(data, offset, data.length - offset);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		StructureSize = buffer.getShort();
		SessionFlags = buffer.getShort();
		SecurityBufferOffset = buffer.getShort();
		assert SecurityBufferOffset == FIXED_SIZE;
		SecurityBufferLength = buffer.getShort();
		assert SecurityBufferLength > 0;	
		Buffer = new byte[SecurityBufferLength];
		buffer.get(Buffer);
		return size();
	}
	
	@Override
	public int size() {
		return FIXED_SIZE + SecurityBufferLength;
	}
	
	
}
