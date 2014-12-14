package jfs.authentication.ntlm.message.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.TransferableData;

public class AvTimestamp extends TransferableData {

	public int Value;
	
	@Override
	public byte[] toBytes() throws IOException {
		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(Value);
		
		return data;
	}

	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		Value = buffer.getInt();
		return size();
	}

	@Override
	public int size() {
		return 4;
	}

}
