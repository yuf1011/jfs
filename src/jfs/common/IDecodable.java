package jfs.common;

public interface IDecodable {
	public int fromBytes(byte data[], int offset, int length);
	public int size();
}
