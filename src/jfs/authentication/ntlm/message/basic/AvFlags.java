package jfs.authentication.ntlm.message.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.TransferableData;

public class AvFlags extends TransferableData {

	public static final short FIXED_SIZE = 4;

	public int Value;

	public static class AvFlagValues {
		public static final int ACCOUNT_AUTHENTICATION_CONSTRAINED = 0x00000001;
		public static final int MESSAGE_INTEGRITY_PROVIDED = 0x00000002;
		public static final int TARGET_SPN_FROM_UNTRUSTED_SOURCE = 0x00000004;
	}

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
