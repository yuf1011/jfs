package jfs.authentication.ntlm.message.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.TransferableData;

public class AvSingleHost extends TransferableData {

	public static final short FIXED_SIZE = 48;
	
	public int Size;
	public int Z4;
	public long CustomData;
	public byte MachineID[];
	
	@Override
	public byte[] toBytes() throws IOException {
		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(Size);
		buffer.putInt(Z4);
		buffer.putLong(CustomData);
		buffer.put(MachineID);

		return data;
	}

	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		Size = buffer.getInt();
		Z4 = buffer.getInt();
		CustomData = buffer.getLong();
		MachineID = new byte[32];
		buffer.get(MachineID);
		return size();
	}

	@Override
	public int size() {
		return FIXED_SIZE;
	}

}
