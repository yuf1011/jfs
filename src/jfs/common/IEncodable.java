package jfs.common;

import java.io.IOException;

public interface IEncodable {
	public byte[] toBytes() throws IOException;
	public int size();
}
