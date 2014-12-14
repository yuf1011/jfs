package jfs.smb2.message;

import java.io.IOException;
import java.nio.ByteBuffer;

import jfs.common.IEncodable;

public class RequestPacket implements IEncodable{
	public IEncodable Header;
	public IEncodable Payload;

	public int size() {
		int s = 0;
		s += Header.size();
		s += Payload.size();
		return s;
	}

	@Override
	public byte[] toBytes() throws IOException {
		int rawSize = size();
		byte[] data = new byte[rawSize + 4];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.putInt(rawSize);
		buffer.put(Header.toBytes());
		buffer.put(Payload.toBytes());
		return data;
	}
}
