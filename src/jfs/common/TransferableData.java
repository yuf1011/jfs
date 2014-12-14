package jfs.common;

import java.io.IOException;

public abstract class TransferableData implements IDecodable, IEncodable {

	@Override
	public abstract byte[] toBytes() throws IOException;

	@Override
	public abstract int fromBytes(byte[] data, int offset, int length);

	@Override
	public abstract int size();

}
