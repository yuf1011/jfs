package jfs.authentication.ntlm.message.basic;

import java.io.IOException;
import java.util.Arrays;

import jfs.common.TransferableData;

public class AvUnicodeValue extends TransferableData{

	public String Value;
	
	@Override
	public byte[] toBytes() throws IOException {
		assert Value != null;
		return Value.getBytes("UTF-16LE");
	}

	@Override
	public int size() {
		return Value.length() * 2;
	}

	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		byte valueBytes[] = Arrays.copyOfRange(data, offset, offset + length);
		Value = new String(valueBytes);
		return size();
	}
}
